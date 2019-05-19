package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.UserGroup;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserGroupDaoImpl implements pl.michal_baniowski.coding_forum.dao.interfacesDao.UserGroupDao {

    private static UserGroupDaoImpl userGroupDao;

    private static final String CREATE_USER_GROUP_QUERY = "INSERT INTO user_groups (name, description) " +
            "VALUES (?,?);";

    private static final String DELETE_USER_QUERY = "DELETE FROM user_groups WHERE user_group_id = ?";

    private static final String READ_ALL_USER_GROUPS_QUERY = "SELECT user_group_id, name, description FROM user_groups";

    private static final String READ_USER_GROUP_BY_ID = "SELECT user_group_id, name, description FROM user_groups WHERE user_group_id = ?";

    private static final String UPDATE_USER_QUERY = "UPDATE user_groups SET name = ?, description = ? WHERE user_group_id = ?";

    public synchronized static UserGroupDaoImpl getInstance(){
        if(userGroupDao == null){
            userGroupDao = new UserGroupDaoImpl();
        }
        return userGroupDao;
    }

    private UserGroupDaoImpl(){}

    @Override
    public UserGroup create(UserGroup newObject) throws UpdateFailException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_GROUP_QUERY, Statement.RETURN_GENERATED_KEYS)){
            setGroupServiceColumn(newObject, preparedStatement);
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    newObject.setId(resultSet.getLong(1));
                }else {
                    throw new UpdateFailException("creation failure");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return newObject;
    }

    @Override
    public UserGroup read(Long primaryKey) throws NotFoundException{
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_USER_GROUP_BY_ID)){
            preparedStatement.setLong(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return getUserGroupFromResultSet(resultSet);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new NotFoundException("user group not found");
    }

    @Override
    public boolean update(UserGroup updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)){
            setGroupServiceColumn(updateObject, preparedStatement);
            preparedStatement.setLong(3, updateObject.getId());
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean delete(Long key) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)){
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<UserGroup> getAll() {
        List<UserGroup> userGroups = new ArrayList<>();
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_USER_GROUPS_QUERY)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    userGroups.add(getUserGroupFromResultSet(resultSet));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userGroups;
    }

    public void setGroupServiceColumn(UserGroup userGroup, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, userGroup.getName());
        preparedStatement.setString(2, userGroup.getDescription());
    }

    public UserGroup getUserGroupFromResultSet(ResultSet resultSet) throws SQLException{
        UserGroup userGroup = new UserGroup();
        userGroup.setId(resultSet.getLong("user_group_id"));
        userGroup.setName(resultSet.getString("name"));
        userGroup.setDescription(resultSet.getString("description"));
        return userGroup;
    }
}
