package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.UserDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.User;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDao;

    private static final String CREATE_USER_QUERY = "INSERT INTO users (username, email, password, is_active) " +
            "VALUES (?,?,?,?);";

    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_id = ?";


    private static final String READ_ALL_USERS_QUERY = "SELECT user_id, username, email, is_active,  password " +
            "FROM users " +
            "ORDER BY user_id;";

    private static final String READ_ALL_USERS_BY_GROUP_QUERY = "SELECT users.user_id, username, email, is_active,  password " +
            "FROM users, users_user_groups,  " +
            "WHERE users.user_id = users_user_groups.user_id " +
            "AND users_user_groups.user_group_id = ? ORDER BY user_id;";

    private static final String READ_USER_BY_EMAIL_QUERY = "SELECT user_id, username, email, is_active, password " +
            "FROM users " +
            "WHERE email = ?;";

    private static final String READ_USER_BY_USERNAME_QUERY = "SELECT user_id, username, email, is_active, password " +
            "FROM users " +
            "WHERE username = ?;";

    private static final String READ_USER_BY_ID_QUERY = "SELECT user_id, username, email, is_active, password " +
            "FROM users " +
            "WHERE user_id = ?;";

    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ?, is_active = ? " +
            "WHERE user_id = ?";

    private static final String USER_ROLE_QUERY = "INSERT INTO user_role (username, role_name) VALUES (?, ?);";
    private static final String DEFAULT_USER_ROLE_QUERY = "INSERT INTO user_role (username) VALUES (?);";

    public synchronized static  UserDaoImpl getInstance(){
        if(userDao == null){
            userDao = new UserDaoImpl();
        }
        return userDao;
    }

    private  UserDaoImpl(){}

    //TODO RoleDao

    @Override
    public User getUserByUserName(String username) throws NotFoundException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_BY_USERNAME_QUERY)){
            preparedStatement.setString(1, username);
            return getUserFromPreperedStatement(preparedStatement);
        }catch (SQLException e){
            throw new NotFoundException("user not found");
        }
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException{
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_BY_EMAIL_QUERY)){
            preparedStatement.setString(1, email);
            return getUserFromPreperedStatement(preparedStatement);
        }catch (SQLException e){
            throw new NotFoundException("user not found");
        }
    }

    @Override
    public User read(Long primaryKey) throws NotFoundException{
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_BY_ID_QUERY)){
            preparedStatement.setLong(1, primaryKey);
            return getUserFromPreperedStatement(preparedStatement);
        }catch (SQLException e){
            throw new NotFoundException("user not found");
        }
    }

    @Override
    public User create(User newObject) throws UpdateFailException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS)){
            setUserColumn(newObject, preparedStatement);
            preparedStatement.executeUpdate();
            try(ResultSet resultId = preparedStatement.getGeneratedKeys()){
                if(resultId.next()){
                    newObject.setId(resultId.getInt(1));
                    setPrivilige(newObject);
                    return newObject;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw  new UpdateFailException("failed to create user");
    }

    @Override
    public boolean update(User updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)){
            setUserColumn(updateObject, preparedStatement);
            preparedStatement.setLong(5, updateObject.getId());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean delete(Long key) {
        int result = 0;
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)){
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<User> getAll() {

        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_USERS_QUERY)){
             return getAllUsersFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getAllByGroup(Long group_id) {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_USERS_BY_GROUP_QUERY)) {
            preparedStatement.setLong(1, group_id);
            return getAllUsersFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private User getUserFromPreperedStatement(PreparedStatement preparedStatement)throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        }
        throw new SQLException("user not found");

    }

    private List<User> getAllUsersFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                users.add(getUserFromResultSet(resultSet));
            }
        }
        return users;
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("user_id");
        String email = resultSet.getString("email");
        String userName = resultSet.getString("username");
        String password = resultSet.getString("password");
        int active = resultSet.getInt("is_active");

        return new User(id, userName, email, password, active);
    }

    private void setUserColumn(User user, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setInt(4, user.isActive() ? 1 : 0);
    }

    private void setPrivilige(User user){
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DEFAULT_USER_ROLE_QUERY)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
