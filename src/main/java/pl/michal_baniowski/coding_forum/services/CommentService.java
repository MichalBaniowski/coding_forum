package pl.michal_baniowski.coding_forum.services;

public class CommentService {
    private static CommentService commentService;
    public synchronized static CommentService getInstance(){
        if(commentService == null){
            commentService = new CommentService();
        }
        return commentService;
    }

    private CommentService(){}

}
