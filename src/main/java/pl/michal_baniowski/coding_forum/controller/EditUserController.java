package pl.michal_baniowski.coding_forum.controller;

import pl.michal_baniowski.coding_forum.model.User;
import pl.michal_baniowski.coding_forum.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit-user")
public class EditUserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String delete = request.getParameter("deleteUser");
        if (delete != null) {
            deleteUser(user.getId(), request, response);
            return;
        }

        String password = request.getParameter("password");

        if (!checkPassword(password, user.getPassword())) {
            request.setAttribute("operationResult", "Niepoprawne hasło, edycja zakończona niepowodzeniem");
            request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
            return;
        } else {
            User newUser = new User(user);
            editUser(newUser, request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/editUserForm.jsp").forward(request, response);
    }

    private void deleteUser(long id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (UserService.getInstance().deleteUser(id)) {
            request.getSession().invalidate();
            request.setAttribute("operationResult", "Twoje konto zostało usunięte");
        } else {
            request.setAttribute("operationResult", "Niestety coś poszło nie tak");
        }
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
    }

    private void editUser(User updatedUser, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updatedUser.setUsername(request.getParameter("username"));
        updatedUser.setEmail(request.getParameter("email"));
        String newPassword = request.getParameter("candidate");
        if (newPassword != null && !newPassword.isEmpty()) {
            updatedUser.setPassword(setPassword(newPassword));
        }
        if (UserService.getInstance().editUser(updatedUser)) {
            request.getSession().setAttribute("user", updatedUser);
            request.setAttribute("operationResult", "Dane zostały uaktualnione");
        }
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
    }

    private String setPassword(String pass) {
        return pass;
    }

    private boolean checkPassword(String password, String candidate) {
        return password.equals(candidate);
    }
}
