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
import java.util.List;

@WebServlet("/user/exercises")
public class GetUserExercisesController extends HttpServlet {

    private ExerciseService exerciseService;

    @Override
    public void init() throws ServletException {
        exerciseService = ExerciseService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        if(principal == null) {
            response.sendRedirect("/login");
        }
        try{
            List<Exercise> exercises = exerciseService.getAllByUsername(principal.getName());
            request.setAttribute("exercises", exercises);
            request.getRequestDispatcher("/WEB-INF/views/userExercises.jsp").forward(request, response);
        }catch (Error e) {
            DefaultErrorController.sendError(request, response);
        }
    }
}
