package pl.michal_baniowski.coding_forum.dao.interfacesDao;

import pl.michal_baniowski.coding_forum.model.Comment;

import java.util.List;

public interface CommentDao extends GenericDao<Comment, Long> {
    List<Comment> getCommentsByUser(String username);
    List<Comment> getCommentsBySolution(Long solutionId);
}
