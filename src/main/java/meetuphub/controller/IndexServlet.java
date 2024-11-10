package meetuphub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static meetuphub.exception.ErrorHandler.handleError;

@WebServlet(name = "index", value = "/")
public class IndexServlet extends HttpServlet {
    private String message;

    @Override
    public void init() {
        message = "Event Manager";
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        try {
            request.getRequestDispatcher("/views/index.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
        }
    }

}
