package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.services.ExerciseService;
import pl.michal_baniowski.coding_forum.services.SolutionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/exercise")
public class GetExerciseFromCommentController extends HttpServlet {

    private ExerciseService exerciseService;
    private SolutionService solutionService;

    @Override
    public void init() throws ServletException {
        this.exerciseService = ExerciseService.getInstance();
        this.solutionService = SolutionService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long solutionId = Long.parseLong(request.getParameter("id"));
            Solution solution = solutionService.getSolutionById(solutionId);
            List<Solution> solutionsByExercise = solutionService.getSolutionByExercise(solution.getExercise().getId());
            request.setAttribute("exercise", solution.getExercise());
            request.setAttribute("solutions", solutionsByExercise);
            request.getRequestDispatcher("/WEB-INF/views/exercise.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
