package meetuphub.controller;

import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import meetuphub.model.User;
import meetuphub.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;

import static meetuphub.exception.ErrorHandler.handleError;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.getRequestDispatcher("views/auth/register.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String passwordHash = request.getParameter("passwordHash");
        if (passwordHash == null || passwordHash.isEmpty()) {
            request.setAttribute("error", "Пароль не может быть пустым");
            try {
                request.getRequestDispatcher("views/auth/register.jsp").forward(request, response);
            } catch (IOException | ServletException e) {
                handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
            }
            return;
        }
        String hashedPassword = BCrypt.hashpw(passwordHash, BCrypt.gensalt());
        User user = new User(0, username, email, hashedPassword, LocalDateTime.now());
        UserRepository.saveUserData(user);

        try {
            response.sendRedirect(request.getContextPath() + "/event");
        } catch (IOException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while redirecting to login.", e);
        }
    }
}