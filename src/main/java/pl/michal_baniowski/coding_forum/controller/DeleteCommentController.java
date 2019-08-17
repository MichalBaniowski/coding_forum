package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.services.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/user/delete-comment")
public class DeleteCommentController extends HttpServlet {

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
            Long commentId = Long.parseLong(request.getParameter("commentId"));
            boolean result = commentService.deleteComment(commentId);
            String prompt = result ? "Komentarz usunięty" : "Nie udało się usunąć komentarza";
            request.setAttribute("operationResult", prompt);
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
