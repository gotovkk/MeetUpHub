package meetuphub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import meetuphub.model.User;
import meetuphub.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static meetuphub.exception.ErrorHandler.handleError;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = UserRepository.getUserData("SELECT * FROM \"user\"");
        request.setAttribute("users", users);
        try {
            request.getRequestDispatcher("views/user/userList.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
    try {
        if ("create".equals(action)) {
            User user = new User(
                    0,
                    request.getParameter("username"),
                    request.getParameter("email"),
                    request.getParameter("passwordHash"),
                    LocalDateTime.now()
            );
            UserRepository.saveUserData(user);
        } else if ("update".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            String newName = request.getParameter("name");
            UserRepository.updateUserData(userId, newName);
        } else if ("delete".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            UserRepository.deleteUserData(userId);
        }
    } catch (NumberFormatException e) {
        handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event data format.", e);
    }
    try {
        response.sendRedirect(request.getContextPath() + "/user");
    } catch (IOException e) {
        handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
    }
    }
}
