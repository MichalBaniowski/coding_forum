package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.services.ExerciseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@WebServlet("/user/add-exercise")
public class AddExerciseController extends HttpServlet {

    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        exerciseService = ExerciseService.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        boolean addExerciseResult = exerciseService.createExercise(new Exercise(title, content, userPrincipal.getName()));
        String prompt = addExerciseResult ? "Dodano zadanie" : "Nie udało się dodać zadania";
        request.setAttribute("operationResult", prompt);
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("/WEB-INF/views/exerciseForm.jsp").forward(request, response);
    }
}
