package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.ActivationCodeDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.model.ActivationCode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivationCodeDaoImpl implements ActivationCodeDao {

    private static ActivationCodeDaoImpl activationCodeDao;

    public synchronized static ActivationCodeDaoImpl getInstance(){
        if(activationCodeDao == null) {
            activationCodeDao = new ActivationCodeDaoImpl();
        }
        return activationCodeDao;
    }

    private static final String CREATE_ACTIVATION_CODE = "INSERT INTO activation_codes (code, user_id) VALUES(?, ?);";
    private static final String READ_ACTIVATION_CODE = "SELECT code, user_id FROM activation_codes WHERE user_id=?;";
    private static final String READ_ALL_ACTIVATION_CODES = "SELECT code, user_id FROM activation_codes";
    private static final String UPDATE_ACTIVATION_CODE = "UPDATE activation_codes SET code=? WHERE user_id=?;";
    private static final String DELETE = "DELETE FROM activation_codes WHERE user_id=?;";

    @Override
    public ActivationCode create(ActivationCode newObject) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ACTIVATION_CODE)) {
            setActivationCodeColumns(preparedStatement, newObject);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return newObject;
    }

    @Override
    public ActivationCode read(Long primaryKey) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ACTIVATION_CODE)) {
            preparedStatement.setLong(1, primaryKey);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return getActivationCodeFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NotFoundException("activation code not found");
    }

    @Override
    public boolean update(ActivationCode updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACTIVATION_CODE)){
            setActivationCodeColumns(preparedStatement, updateObject);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean delete(Long key) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<ActivationCode> getAll() {
        List<ActivationCode> resultList = new ArrayList<>();
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_ACTIVATION_CODES)) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    resultList.add(getActivationCodeFromResultSet(resultSet));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultList;
    }

    private void setActivationCodeColumns(PreparedStatement preparedStatement, ActivationCode activationCode) throws SQLException {
        preparedStatement.setString(1, activationCode.getCode());
        preparedStatement.setLong(2, activationCode.getId());
    }

    private ActivationCode getActivationCodeFromResultSet(ResultSet resultSet) throws SQLException{
        ActivationCode activationCode = new ActivationCode();
        activationCode.setId(resultSet.getLong("user_id"));
        activationCode.setCode(resultSet.getString("code"));
        return activationCode;
    }
}
