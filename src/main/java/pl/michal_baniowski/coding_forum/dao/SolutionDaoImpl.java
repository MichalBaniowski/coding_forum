package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.SolutionDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.model.User;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolutionDaoImpl implements SolutionDao {

    private static SolutionDaoImpl solutionDao;

    public synchronized static SolutionDaoImpl getInstance(){
        if(solutionDao == null){
            solutionDao= new SolutionDaoImpl();
        }
        return solutionDao;
    }

    private SolutionDaoImpl(){}

    private static final String CREATE_SOLUTION_QUERY = "INSERT INTO solutions (exercise_id, username, description, up_vote, down_vote) " +
            "VALUES (?, ?, ?, ?, ?);";
    private static final String READ_SOLUTION_QUERY =
            "SELECT solution_id, solutions.exercise_id, username, created, updated, solutions.description AS solution_description, up_vote, down_vote, " +
                    "title, exercises.description AS exercise_description " +
                    "FROM solutions, exercises " +
                    "WHERE solutions.exercise_id = exercises.exercise_id " +
                    "AND solution_id = ?;";
    private static final String READ_ALL_SOLUTIONS_BY_USERNAME_QUERY =
            "SELECT solution_id, solutions.exercise_id, username, created, updated, solutions.description AS solution_description, up_vote, down_vote, " +
                    "title, exercises.description AS exercise_description " +
                    "FROM solutions, exercises " +
                    "WHERE solutions.exercise_id = exercises.exercise_id " +
                    "AND username = ?;";
    private static final String READ_ALL_SOLUTIONS_BY_EXERCISE_QUERY =
            "SELECT solution_id, solutions.exercise_id, username, created, updated, solutions.description AS solution_description, up_vote, down_vote, " +
                    "title, exercises.description AS exercise_description " +
                    "FROM solutions, exercises " +
                    "WHERE solutions.exercise_id = exercises.exercise_id " +
                    "AND solutions.exercise_id = ?;";
    private static final String READ_ALL_SOLUTIONS_QUERY =
            "SELECT solution_id, solutions.exercise_id, username, created, updated, solutions.description AS solution_description, up_vote, down_vote, " +
                    "title, exercises.description AS exercise_description " +
                    "FROM solutions, exercises " +
                    "WHERE solutions.exercise_id = exercises.exercise_id;";
    private static final String UPDATE_SOLUTION_QUERY = "UPDATE solutions SET updated = NOW(), description = ?, up_vote = ?, down_vote = ? WHERE solution_id = ?;";
    private static final String DELETE_SOLUTION_QUERY = "DELETE FROM solutions WHERE solution_id = ?";
    @Override
    public List<Solution> getAllByUser(User user) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SOLUTIONS_BY_USERNAME_QUERY)){
            preparedStatement.setString(1, user.getUsername());
            return getAllSolutionsFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Solution> getAllByExercise(Exercise exercise) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SOLUTIONS_BY_EXERCISE_QUERY)){
            preparedStatement.setLong(1, exercise.getId());
            return getAllSolutionsFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Solution create(Solution newObject) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SOLUTION_QUERY, Statement.RETURN_GENERATED_KEYS)){
            setSolutionCreateColumn(newObject, preparedStatement);
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
    public Solution read(Long primaryKey) throws NotFoundException{
        try (Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_SOLUTION_QUERY)){
            preparedStatement.setLong(1, primaryKey);
            return getSolutionFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new NotFoundException("solution not found");
    }

    @Override
    public boolean update(Solution updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SOLUTION_QUERY)){
            setSolutionUpdateColumn(updateObject, preparedStatement);
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SOLUTION_QUERY)){
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<Solution> getAll() {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SOLUTIONS_QUERY)){
            return getAllSolutionsFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Solution getSolutionFromPreparedStatement(PreparedStatement preparedStatement)throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return getSolutionFromResultSet(resultSet);
            }
        }
        throw new SQLException("solution not found");

    }

    public List<Solution> getAllSolutionsFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        List<Solution> solutions = new ArrayList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                solutions.add(getSolutionFromResultSet(resultSet));
            }
        }
        return solutions;
    }

    public Solution getSolutionFromResultSet(ResultSet resultSet) throws SQLException {
        Solution solution = new Solution();
        solution.setId(resultSet.getLong("solution_id"));
        solution.setExercise(ExerciseDaoImpl.getInstance().getExerciseFromResultSet(resultSet));
        solution.setUsername(resultSet.getString("username"));
        solution.setCreated(resultSet.getTimestamp("created"));
        solution.setUpdated(resultSet.getTimestamp("updated"));
        solution.setDescription(resultSet.getString("solution_description"));
        solution.setUpVote(resultSet.getInt("up_vote"));
        solution.setDownVote(resultSet.getInt("down_vote"));
        return solution;
    }

    public void setSolutionCreateColumn(Solution solution, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, solution.getExercise().getId());
        preparedStatement.setString(2, solution.getUsername());
        preparedStatement.setString(3, solution.getDescription());
        preparedStatement.setInt(4, solution.getUpVote());
        preparedStatement.setInt(5, solution.getDownVote());
    }

    public void setSolutionUpdateColumn(Solution solution, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, solution.getDescription());
        preparedStatement.setInt(2, solution.getUpVote());
        preparedStatement.setInt(3, solution.getDownVote());
        preparedStatement.setLong(4, solution.getId());
    }
}
