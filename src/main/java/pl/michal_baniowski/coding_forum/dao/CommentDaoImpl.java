package pl.michal_baniowski.coding_forum.dao;

import pl.michal_baniowski.coding_forum.dao.interfacesDao.CommentDao;
import pl.michal_baniowski.coding_forum.dbUtil.DbUtil;
import pl.michal_baniowski.coding_forum.model.Comment;
import pl.michal_baniowski.coding_forum.exception.NotFoundException;
import pl.michal_baniowski.coding_forum.exception.UpdateFailException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    private static CommentDaoImpl commentDao;

    public synchronized static CommentDaoImpl getInstance(){
        if(commentDao == null){
            commentDao = new CommentDaoImpl();
        }
        return commentDao;
    }

    private CommentDaoImpl(){}

    private static final String CREATE_COMMENT = "INSERT INTO comments (solution_id, username, description, date) " +
            "VALUES(?, ?, ?, ?);";
    private static final String READ_COMMENT = "SELECT * FROM comments WHERE comment_id = ?;";
    private static final String READ_ALL_COMMENTS = "SELECT * FROM comments;";
    private static final String READ_ALL_COMMENTS_BY_USERNAME = "SELECT * FROM comments WHERE username = ?;";
    private static final String READ_ALL_COMMENTS_BY_SOLUTION = "SELECT * FROM comments WHERE solution_id = ?;";
    private static final String UPDATE_COMMENT = "UPDATE comments SET description = ?, date = NOW() WHERE comment_id = ?";
    private static final String DELETE_COMMENT = "DELETE FROM comments WHERE comment_id = ?;";

    @Override
    public List<Comment> getCommentsByUser(String username) {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_COMMENTS_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            return getAllCommentFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Comment> getCommentsBySolution(Long solutionId) {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_COMMENTS_BY_SOLUTION)) {
            preparedStatement.setLong(1, solutionId);
            return getAllCommentFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Comment create(Comment newObject) throws UpdateFailException {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COMMENT, Statement.RETURN_GENERATED_KEYS)) {
            setCommentCreateColumn(newObject, preparedStatement);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    newObject.setId(resultSet.getLong(1));
                } else {
                    throw new UpdateFailException("creation failure");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UpdateFailException("creation failure");
        }
        return newObject;
    }

    @Override
    public Comment read(Long primaryKey) {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_COMMENT)) {
            preparedStatement.setLong(1, primaryKey);
            return getCommentFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NotFoundException("comment not found");
    }

    @Override
    public boolean update(Comment updateObject) {
        int result = 0;
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMMENT)) {
            setCommentUpdateColumn(updateObject, preparedStatement);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public boolean delete(Long key) {
        int result = 0;
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT)) {
            preparedStatement.setLong(1, key);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    @Override
    public List<Comment> getAll() {
        try (Connection connection = DbUtil.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_COMMENTS)) {
            return getAllCommentFromPreparedStatement(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Comment getCommentFromPreparedStatement(PreparedStatement preparedStatement) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return getCommentFromResultSet(resultSet);
            }
        }catch (SQLException e) {}
        throw new NotFoundException("comment not found");
    }

    public List<Comment> getAllCommentFromPreparedStatement(PreparedStatement preparedStatement) throws SQLException{
        List<Comment> comments = new ArrayList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                comments.add(getCommentFromResultSet(resultSet));
            }
        }
        return comments;
    }

    public Comment getCommentFromResultSet(ResultSet resultSet) throws SQLException {
        Comment comment = new Comment();
        comment.setId(resultSet.getLong("comment_id"));
        comment.setSolutionId(resultSet.getLong("solution_id"));
        comment.setAuthor(resultSet.getString("username"));
        comment.setDescription(resultSet.getString("description"));
        comment.setCreated(resultSet.getTimestamp("date"));
        return comment;
    }

    public void setCommentCreateColumn(Comment comment, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setLong(1, comment.getSolutionId());
        preparedStatement.setString(2, comment.getAuthor());
        preparedStatement.setString(3, comment.getDescription());
        preparedStatement.setTimestamp(4, comment.getCreated());
    }

    public void setCommentUpdateColumn(Comment comment, PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.setString(1, comment.getDescription());
        preparedStatement.setLong(2, comment.getId());
    }
}
