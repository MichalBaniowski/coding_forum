package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.services.UserService;
import pl.michal_baniowski.coding_forum.services.message.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean result = UserService.getInstance().addUser(request);

        request.setAttribute("operationResult",
                result ? "Konto zostało utworzone. Na podany adres został wysłany link aktywacyjny" : "Resjestracja nieudana");
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/registrationForm.jsp").forward(request, response);
    }
}
