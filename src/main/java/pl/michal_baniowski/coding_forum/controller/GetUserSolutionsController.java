package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.services.SolutionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@WebServlet("/user/solutions")
public class GetUserSolutionsController extends HttpServlet {

    private SolutionService solutionService;

    @Override
    public void init() throws ServletException {
        this.solutionService = SolutionService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        if(principal == null) {
            response.sendRedirect("/login");
        }
        try{
            List<Solution> solutions = solutionService.getSolutionsByUsername(principal.getName());
            request.setAttribute("solutions", solutions);
            request.getRequestDispatcher("/WEB-INF/views/userSolutions.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }


    }
}
