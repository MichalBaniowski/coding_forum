package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.VoteDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;
import pl.michal_baniowski.coding_forum.model.Vote;
import pl.michal_baniowski.coding_forum.model.VoteType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteDaoImpl implements VoteDao {

    private static final String CREATE_VOTE = "INSERT INTO vote (solution_id, username, type) VALUES(?, ?, ?);";
    private static final String READ_VOTE = "SELECT * FROM vote WHERE vote_id = ?;";
    private static final String READ_ALL_VOTES = "SELECT * FROM vote;";
    private static final String READ_VOTE_BY_USER_ID_SOLUTION_ID = "SELECT * FROM vote WHERE username = ? AND solution_id = ?";
    private static final String UPDATE_VOTE = "UPDATE vote SET type = ? WHERE vote_id = ?;";
    private static final String DELETE_VOTE = "DELETE FROM vote WHERE vote_id = ?;";

    private static VoteDaoImpl voteDao;

    public synchronized static VoteDaoImpl getInstance(){
        if(voteDao == null){
            voteDao = new VoteDaoImpl();
        }
        return voteDao;
    }

    private VoteDaoImpl(){}

    @Override
    public Vote getVoteByUserIdSolutionId(String username, long solutionId) throws NotFoundException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_VOTE_BY_USER_ID_SOLUTION_ID)){
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, solutionId);
            return getVoteFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new NotFoundException("vote not found");
    }

    @Override
    public Vote create(Vote newObject) throws UpdateFailException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VOTE, Statement.RETURN_GENERATED_KEYS)){
            setVoteCreateColumn(newObject, preparedStatement);
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
    public Vote read(Long primaryKey) throws UpdateFailException {
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_VOTE)){
            preparedStatement.setLong(1, primaryKey);
            return getVoteFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new UpdateFailException("creation failure");
    }

    @Override
    public boolean update(Vote updateObject) {
        int result = 0;
        try(Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VOTE)){
            setVoteUpdateColumn(updateObject, preparedStatement);
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
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VOTE)){
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<Vote> getAll() {
        try (Connection connection = DbUtil.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_VOTES)){
            return getAllVoteFromPreparedStatement(preparedStatement);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Vote getVoteFromPreparedStatement(PreparedStatement preparedStatement)throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return getVoteFromResultSet(resultSet);
            }
        }
        throw new SQLException("comment not found");

    }

    public List<Vote> getAllVoteFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        List<Vote> votes = new ArrayList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                votes.add(getVoteFromResultSet(resultSet));
            }
        }
        return votes;
    }

    public Vote getVoteFromResultSet(ResultSet resultSet) throws SQLException {
        Vote vote = new Vote();
        vote.setId(resultSet.getLong("vote_id"));
        vote.setSolutionId(resultSet.getLong("solution_id"));
        vote.setUsername(resultSet.getString("username"));
        vote.setVoteType(VoteType.valueOf(resultSet.getString("type")));
        return vote;
    }

    public void setVoteCreateColumn(Vote vote, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, vote.getSolutionId());
        preparedStatement.setString(2, vote.getUsername());
        preparedStatement.setString(3, vote.getVoteType().toString());
    }

    public void setVoteUpdateColumn(Vote vote, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, vote.getVoteType().toString());
        preparedStatement.setLong(2, vote.getId());
    }
}
