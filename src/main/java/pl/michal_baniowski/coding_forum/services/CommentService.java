package pl.michal_baniowski.coding_forum.services;

import pl.michal_baniowski.coding_forum.dao.factoryDao.DaoFactory;
import pl.michal_baniowski.coding_forum.dao.interfacesDao.CommentDao;
import pl.michal_baniowski.coding_forum.model.Comment;

import java.util.List;

public class CommentService {
    private static CommentService commentService;
    private CommentDao commentDao;
    public synchronized static CommentService getInstance(){
        if(commentService == null){
            commentService = new CommentService();
        }
        return commentService;
    }

    private CommentService(){
        this.commentDao = DaoFactory.getDaoFactory("MY_SQL").getCommentDao();
    }

    public List<Comment> getCommentsByUsername(String username) {
        return commentDao.getCommentsByUser(username);
    }

    public List<Comment> getCommentsBySolution(Long solutionId) {
        return commentDao.getCommentsBySolution(solutionId);
    }

    public List<Comment> getAllComments() {
        return commentDao.getAll();
    }

    public Comment getCommentById(Long commentId) {
        return commentDao.read(commentId);
    }

    public boolean createComment(Comment comment) {
        return commentDao.create(comment).getId() != null;
    }

    public boolean updateComment(Comment comment) {
        return commentDao.update(comment);
    }

    public boolean deleteComment(Long key) {
        return commentDao.delete(key);
    }
}
