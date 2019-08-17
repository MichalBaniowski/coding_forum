package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.exception.CreationFailException;
import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.services.ExerciseService;
import pl.michal_baniowski.coding_forum.services.SolutionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/user/add-solution")
public class AddSolutionController extends HttpServlet {

    private SolutionService solutionService;
    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        this.solutionService = SolutionService.getInstance();
        this.exerciseService = ExerciseService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        try {
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            Exercise exercise = exerciseService.getExerciseById(exerciseId);
            String description = request.getParameter("description");
            String author = userPrincipal.getName();
            Solution solution = new Solution(exercise, author, description);
            boolean createResult = solutionService.createSolution(solution);
            if(!createResult) {
                throw new CreationFailException("nie udało się dodać rozwiązania");
            }else {
                request.setAttribute("operationResult", "dodano rozwiązanie");
                request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
            }
        }catch (CreationFailException e){
            DefaultErrorController.sendError(request, response, e.getMessage());
        }catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            request.setAttribute("exerciseId", exerciseId);
            request.getRequestDispatcher("/WEB-INF/views/solutionForm.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
