package meetuphub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import meetuphub.exception.DatabaseException;
import meetuphub.model.Category;
import meetuphub.model.Event;
import meetuphub.model.User;
import meetuphub.repository.CategoryRepository;
import meetuphub.repository.EventCategoryRepository;
import meetuphub.repository.EventRepository;
import meetuphub.repository.EventUserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static meetuphub.exception.ErrorHandler.handleError;

@WebServlet(name = "EventServlet", urlPatterns = {"/event", "/event/create", "/event/register", "/event/update", "/event/delete"})
public class EventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            redirectToLogin(request, response);
            return;
        }

        try {
            switch (path) {
                case "/event":
                    handleEventList(request, response, user);
                    break;
                case "/event/update":
                    request.getRequestDispatcher("/views/event/updateEvent.jsp").forward(request, response);
                    break;
                case "/event/delete":
                    request.getRequestDispatcher("/views/event/deleteEvent.jsp").forward(request, response);
                    break;
                case "/event/create":
                    List<Category> categories = CategoryRepository.getCategoryData("SELECT * FROM category");
                    request.setAttribute("categories", categories);
                    request.getRequestDispatcher("/views/event/createEvent.jsp").forward(request, response);
                    break;
                default:
                    throw new ServletException("Unknown path: " + path);
            }
        } catch (DatabaseException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching data.", e);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
        }
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while redirecting to login.", e);
        }
    }

    private void handleEventList(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        try {
            List<Event> events = EventRepository.getEventData("SELECT * FROM event");
            request.setAttribute("events", events);

            for (Event event : events) {
                boolean isRegistered = EventUserRepository.isUserRegisteredForEvent(user.getId(), event.getId());
                List<Category> categories = EventCategoryRepository.getCategoriesByEventId(event.getId());

                request.setAttribute("isRegistered_" + event.getId(), isRegistered);
                request.setAttribute("categories_" + event.getId(), categories);
            }
            request.setAttribute("events", events);
            request.getRequestDispatcher("/views/event/eventList.jsp").forward(request, response);
        } catch (DatabaseException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error connecting to the database.", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            handleRedirectToLogin(request, response);
            return;
        }

        try {
            switch (path) {
                case "/event/create":
                    handleEventCreate(request, response);
                    break;
                case "/event/register":
                    handleEventRegister(request, response, user);
                    break;
                case "/event/update":
                    handleEventUpdate(request, response);
                    break;
                case "/event/delete":
                    handleEventDelete(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Path not found.");
                    break;
            }

            response.sendRedirect(request.getContextPath() + "/event");

        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid format for a numeric parameter.", e);
        } catch (IOException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.", e);
        }
    }

    private void handleRedirectToLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (IOException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while redirecting to login.", e);
        }
    }

    private void handleEventCreate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            int organizerId = Integer.parseInt(request.getParameter("organizerId"));
            LocalDateTime startTime = LocalDateTime.parse(request.getParameter("startTime"));
            LocalDateTime endTime = LocalDateTime.parse(request.getParameter("endTime"));

            // Создаем событие
            Event event = new Event(
                    0,
                    request.getParameter("name"),
                    request.getParameter("description"),
                    request.getParameter("status"),
                    startTime,
                    endTime,
                    LocalDateTime.now(),
                    locationId,
                    organizerId
            );
            EventRepository.saveEventData(event);

            int eventId = event.getId();

            String[] categoryIds = request.getParameterValues("categoryIds");
            if (categoryIds != null) {
                for (String categoryIdStr : categoryIds) {
                    int categoryId = Integer.parseInt(categoryIdStr);
                    EventCategoryRepository.addEventToCategory(eventId, categoryId);
                }
            }

        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event data format.", e);
        } catch (DatabaseException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.", e);
        }
    }


    private void handleEventRegister(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            EventUserRepository.addUserToEvent(eventId, user.getId());
        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID.", e);
        }
    }

    private void handleEventUpdate(HttpServletRequest request, HttpServletResponse response) {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            int organizerId = Integer.parseInt(request.getParameter("organizerId"));
            LocalDateTime startTime = LocalDateTime.parse(request.getParameter("startTime"));
            LocalDateTime endTime = LocalDateTime.parse(request.getParameter("endTime"));

            Event event = new Event(
                    eventId,
                    request.getParameter("name"),
                    request.getParameter("description"),
                    request.getParameter("status"),
                    startTime,
                    endTime,
                    LocalDateTime.now(),
                    locationId,
                    organizerId
            );
            EventRepository.updateEventData(event);
        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event data format.", e);
        }
    }

    private void handleEventDelete(HttpServletRequest request, HttpServletResponse response) {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            EventRepository.deleteEventData(eventId);
        } catch (NumberFormatException e) {
            handleError(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid event ID.", e);
        }
    }

}
