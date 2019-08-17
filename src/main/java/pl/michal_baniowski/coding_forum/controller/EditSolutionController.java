package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.exception.UpdateFailException;
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

@WebServlet("/user/edit-solution")
public class EditSolutionController extends HttpServlet {

    private SolutionService solutionService;
    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        this.solutionService = SolutionService.getInstance();
        this.exerciseService = ExerciseService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long solutionId = Long.parseLong(request.getParameter("solutionId"));
            String description = request.getParameter("description");
            Solution solution = solutionService.getSolutionById(solutionId);
            solution.setDescription(description);
            boolean updateResult = solutionService.updateSolution(solution);
            String prompt = updateResult ? "Uaktualniono rozwiązanie" : "Nie udało się aktualizować rozwiązania";
            request.setAttribute("operationResult", prompt);
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
        }catch (UpdateFailException e){
            DefaultErrorController.sendError(request, response, e.getMessage());
        }catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        try {
            Long solutionId = Long.parseLong(request.getParameter("solutionId"));
            Solution solution = solutionService.getSolutionById(solutionId);
            request.setAttribute("solution", solution);
            request.getRequestDispatcher("/WEB-INF/views/solutionForm.jsp").forward(request,response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
