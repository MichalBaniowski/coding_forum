package pl.michal_baniowski.coding_forum.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultErrorController {

    public synchronized static void sendError(HttpServletRequest request, HttpServletResponse response, String prompt) throws ServletException, IOException {
        request.setAttribute("operationResult", prompt);
        request.getRequestDispatcher("/WEB-INF/views/operationResultPrompt.jsp").forward(request, response);
    }

    public synchronized static void sendError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sendError(request, response, "coś poszło nie tak");
    }
}
