package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.exception.CreationFailException;
import pl.michal_baniowski.coding_forum.model.Comment;
import pl.michal_baniowski.coding_forum.services.CommentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/user/add-comment")
public class AddCommentController extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        this.commentService = CommentService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        try {
            Long solutionId = Long.parseLong(request.getParameter("solutionId"));
            String description = request.getParameter("description");
            boolean saveResult = commentService.createComment(new Comment(solutionId, userPrincipal.getName(), description));
            if(!saveResult) {
                throw new CreationFailException("nie udało się dodać komentarza");
            }else {
                request.setAttribute("operationResult", "dodano komentarz");
                request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
            }
        }catch (CreationFailException e){
            DefaultErrorController.sendError(request, response, e.getMessage());
        }catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
