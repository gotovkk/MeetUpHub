package meetuphub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import meetuphub.model.User;
import meetuphub.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static meetuphub.exception.ErrorHandler.handleError;


@WebServlet("/login")
public class LoginServlet extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher("views/auth/login.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Forward login error.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = UserService.findUserByName(name);

        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user);
            try {
                response.sendRedirect(request.getContextPath() + "/event");
            }  catch (IOException e) {
                handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internar server.", e);
            }
        } else {
            request.setAttribute("error", "Invalid username or password");
            try {
                request.getRequestDispatcher("views/auth/login.jsp").forward(request, response);
            } catch (IOException | ServletException e) {
                handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
            }
        }
    }

}