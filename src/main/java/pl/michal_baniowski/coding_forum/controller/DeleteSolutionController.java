package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.services.SolutionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/delete-solution")
public class DeleteSolutionController extends HttpServlet {

    private SolutionService solutionService;

    @Override
    public void init() throws ServletException {
        this.solutionService = SolutionService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long solutionId = Long.parseLong(request.getParameter("solutionId"));
            boolean result = solutionService.deleteSolution(solutionId);
            String prompt = result ? "Rozwiązanie usunięte" : "Nie udało się usunąć rozwiązania";
            request.setAttribute("operationResult", prompt);
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
        } catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }


    }
}
