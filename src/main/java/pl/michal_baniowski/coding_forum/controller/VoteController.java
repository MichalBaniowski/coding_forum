package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.exception.CreationFailException;
import pl.michal_baniowski.coding_forum.model.Exercise;
import pl.michal_baniowski.coding_forum.model.Solution;
import pl.michal_baniowski.coding_forum.model.Vote;
import pl.michal_baniowski.coding_forum.model.VoteType;
import pl.michal_baniowski.coding_forum.services.ExerciseService;
import pl.michal_baniowski.coding_forum.services.SolutionService;
import pl.michal_baniowski.coding_forum.services.VoteService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@WebServlet("/user/vote")
public class VoteController extends HttpServlet {

    private VoteService voteService;
    private ExerciseService exerciseService;
    private SolutionService solutionService;

    @Override
    public void init() throws ServletException {
        this.voteService = VoteService.getInstance();
        this.solutionService = SolutionService.getInstance();
        this.exerciseService = ExerciseService.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal == null) {
            response.sendRedirect("/login");
        }
        try {
            String voteType = request.getParameter("voteType");
            Long solutionId = Long.parseLong(request.getParameter("solutionId"));
            Vote vote = new Vote(solutionId, userPrincipal.getName(), VoteType.valueOf(voteType));
            voteService.addVote(vote);
            Long exerciseId = Long.parseLong(request.getParameter("exerciseId"));
            Exercise exerciseById = exerciseService.getExerciseById(exerciseId);
            List<Solution> solutionsByExercise = solutionService.getSolutionByExercise(exerciseId);
            request.setAttribute("exercise", exerciseById);
            request.setAttribute("solutions", solutionsByExercise);
            request.getRequestDispatcher("/WEB-INF/views/exercise.jsp").forward(request, response);
        }catch (CreationFailException e){
            DefaultErrorController.sendError(request, response, e.getMessage());
        }catch (Exception e) {
            DefaultErrorController.sendError(request, response);
        }

    }
}
