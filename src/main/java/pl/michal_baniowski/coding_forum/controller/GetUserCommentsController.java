package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.Comment;
import pl.michal_baniowski.coding_forum.services.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@WebServlet("/user/comments")
public class GetUserCommentsController extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        this.commentService = CommentService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        if(principal == null) {
            response.sendRedirect("/login");
        }
        try{
            List<Comment> comments = commentService.getCommentsByUsername(principal.getName());
            request.setAttribute("comments", comments);
            request.getRequestDispatcher("/WEB-INF/views/userComments.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
