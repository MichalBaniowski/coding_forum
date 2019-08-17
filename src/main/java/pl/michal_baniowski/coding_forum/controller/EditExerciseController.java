package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.exception.UpdateFailException;
import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.services.ExerciseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/user/edit-exercise")
public class EditExerciseController extends HttpServlet {

    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        this.exerciseService = ExerciseService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            Exercise exercise = exerciseService.getExerciseById(exerciseId);
            exercise.setTitle(title);
            exercise.setDescription(content);
            boolean updateResult = exerciseService.updateExercise(exercise);
            String prompt = updateResult ? "Uaktualniono zadanie" : "Nie udało się aktualizować zadania";
            request.setAttribute("operationResult", prompt);
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
        } catch (UpdateFailException e) {
            DefaultErrorController.sendError(request, response, e.getMessage());
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        try {
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            Exercise exercise = exerciseService.getExerciseById(exerciseId);
            request.setAttribute("exerciseToEdit", exercise);
            request.getRequestDispatcher("/WEB-INF/views/exerciseForm.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
