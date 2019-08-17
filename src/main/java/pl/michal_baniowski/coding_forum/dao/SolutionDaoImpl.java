package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.CommentDao;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.ExerciseDao;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.SolutionDao;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.VoteDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.*;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionDaoImpl implements SolutionDao {

    private static SolutionDaoImpl solutionDao;
    private ExerciseDao exerciseDao;
    private VoteDao voteDao;
    private CommentDao commentDao;

    public synchronized static SolutionDaoImpl getInstance(){
        if(solutionDao == null){
            solutionDao= new SolutionDaoImpl();
        }
        return solutionDao;
    }

    private SolutionDaoImpl(){
        this.exerciseDao = ExerciseDaoImpl.getInstance();
        this.voteDao = VoteDaoImpl.getInstance();
        this.commentDao = CommentDaoImpl.getInstance();
    }

    private static final String CREATE_SOLUTION_QUERY =
            "INSERT INTO solutions (exercise_id, author, created, updated, description) " +
            "VALUES (?, ?, ?, ?, ?);";
    private static final String READ_SOLUTION_QUERY =
            "SELECT solution_id, exercise_id, author, created, updated, description " +
                    "FROM solutions " +
                    "WHERE solution_id = ?;";
    private static final String READ_ALL_SOLUTIONS_BY_USERNAME_QUERY =
            "SELECT solution_id, exercise_id, author, created, updated, description " +
                    "FROM solutions " +
                    "WHERE author = ?;";
    private static final String READ_ALL_SOLUTIONS_BY_EXERCISE_QUERY =
            "SELECT solution_id, exercise_id, author, created, updated, description " +
                    "FROM solutions " +
                    "WHERE exercise_id = ?;";
    private static final String READ_ALL_SOLUTIONS_QUERY =
            "SELECT solution_id, exercise_id, author, created, updated, description " +
                    "FROM solutions; ";
    private static final String UPDATE_SOLUTION_QUERY = "UPDATE solutions " +
            "SET updated = ?, description = ? WHERE solution_id = ?;";
    private static final String DELETE_SOLUTION_QUERY = "DELETE FROM solutions WHERE solution_id = ?";

    @Override
    public List<Solution> getAllByUsername(String username) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SOLUTIONS_BY_USERNAME_QUERY)){
            preparedStatement.setString(1, username);
            return getAllSolutionsFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Solution> getAllByExercise(Long exerciseId) {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_SOLUTIONS_BY_EXERCISE_QUERY)){
            preparedStatement.setLong(1, exerciseId);
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
            throw new UpdateFailException("creation failure");
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
        solution.setExercise(ExerciseDaoImpl.getInstance().read(resultSet.getLong("exercise_id")));
        solution.setAuthor(resultSet.getString("author"));
        solution.setCreated(resultSet.getTimestamp("created"));
        solution.setUpdated(resultSet.getTimestamp("updated"));
        solution.setDescription(resultSet.getString("description"));
        solution.setVotes(getSolutionVotes(solution.getId()));
        solution.setComments(getSolutionComments(solution.getId()));
        return solution;
    }

    private Set<Vote> getSolutionVotes(Long solutionId) {
        return voteDao.getSolutionVotes(solutionId).stream().collect(Collectors.toSet());
    }

    private List<Comment> getSolutionComments(Long solutionId) {
        return commentDao.getCommentsBySolution(solutionId);
    }

    public void setSolutionCreateColumn(Solution solution, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, solution.getExercise().getId());
        preparedStatement.setString(2, solution.getAuthor());
        preparedStatement.setTimestamp(3, solution.getCreated());
        preparedStatement.setTimestamp(4, solution.getUpdated());
        preparedStatement.setString(5, solution.getDescription());
    }

    public void setSolutionUpdateColumn(Solution solution, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setTimestamp(1, solution.getUpdated());
        preparedStatement.setString(2, solution.getDescription());
        preparedStatement.setLong(3, solution.getId());
    }
}
