package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.services.CommentService;
import pl.michal_baniowski.coding_forum.services.ExerciseService;
import pl.michal_baniowski.coding_forum.services.SolutionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/exercise")
public class GetExerciseController extends HttpServlet {

    private ExerciseService exerciseService;
    private SolutionService solutionService;

    @Override
    public void init() throws ServletException {
        exerciseService = ExerciseService.getInstance();
        solutionService = SolutionService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            Long exerciseId = Long.parseLong(id);
            Exercise exerciseById = exerciseService.getExerciseById(exerciseId);
            List<Solution> solutionsByExercise = solutionService.getSolutionByExercise(exerciseId);
            request.setAttribute("exercise", exerciseById);
            request.setAttribute("solutions", solutionsByExercise);
            request.getRequestDispatcher("/WEB-INF/views/exercise.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }




}
