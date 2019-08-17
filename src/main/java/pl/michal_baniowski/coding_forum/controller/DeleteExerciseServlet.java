package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.services.ExerciseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/delete-exercise")
public class DeleteExerciseServlet extends HttpServlet {

    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        this.exerciseService = ExerciseService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            boolean result = exerciseService.deleteExercise(exerciseId);
            String prompt = result ? "Udało się usunąć ćwiczenie" : "Nie udało się usunąć ćwiczenia";
            request.setAttribute("operationResult", prompt);
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
        }catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
