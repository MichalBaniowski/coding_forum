package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account-activation")
public class ActivationController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String code = request.getParameter("code");
        request.setAttribute("operationResult", UserService.getInstance().activateUser(id, code));
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
    }
}
