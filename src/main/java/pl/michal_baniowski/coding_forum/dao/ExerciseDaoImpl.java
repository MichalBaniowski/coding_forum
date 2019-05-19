package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.ExerciseDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDaoImpl implements ExerciseDao {

    private static ExerciseDaoImpl exerciseDao;

    private static final String CREATE_EXERCISE = "INSERT INTO exercises (title, description) " +
            "VALUES (?,?);";
    private static final String READ_EXERCISE = "SELECT exercise_id, title, description AS exercise_description FROM exercises WHERE exercise_id = ?;";
    private static final String READ_ALL_EXERCISES = "SELECT exercise_id, title, description AS exercise_description FROM exercises;";
    private static final String UPDATE_EXERCISE = "UPDATE exercises SET title = ?, description = ? " +
            "WHERE exercise_id = ?;";
    private static final String DELETE_EXERCISE = "DELETE FROM exercises WHERE exercise_id = ?;";

    public synchronized static ExerciseDaoImpl getInstance(){
        if(exerciseDao == null){
            exerciseDao = new ExerciseDaoImpl();
        }
        return exerciseDao;
    }

    private ExerciseDaoImpl(){}

    @Override
    public Exercise create(Exercise newObject) throws UpdateFailException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_EXERCISE, Statement.RETURN_GENERATED_KEYS)){
            setExerciseColumn(newObject, preparedStatement);
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if (resultSet.next()){
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
    public Exercise read(Long primaryKey) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_EXERCISE)){
            preparedStatement.setLong(1, primaryKey);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return getExerciseFromResultSet(resultSet);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new NotFoundException("exercise not found");
    }

    @Override
    public boolean update(Exercise updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EXERCISE)){
            setExerciseColumn(updateObject, preparedStatement);
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EXERCISE)){
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<Exercise> getAll() {
        List<Exercise> exercises = new ArrayList<>();
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_EXERCISES)){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    exercises.add(getExerciseFromResultSet(resultSet));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return exercises;
    }

    public void setExerciseColumn(Exercise exercise, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, exercise.getTitle());
        preparedStatement.setString(2, exercise.getDescription());
    }

    public Exercise getExerciseFromResultSet(ResultSet resultSet) throws SQLException{
        Exercise exercise = new Exercise();
        exercise.setId(resultSet.getLong("exercise_id"));
        exercise.setTitle(resultSet.getString("title"));
        exercise.setDescription(resultSet.getString("exercise_description"));
        return exercise;
    }
}
