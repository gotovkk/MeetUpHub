package meetuphub.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import meetuphub.exception.DatabaseException;
import meetuphub.model.Category;
import meetuphub.repository.CategoryRepository;

import java.io.IOException;
import java.util.List;

import static meetuphub.exception.ErrorHandler.handleError;

@WebServlet(name = "CategoryServlet", urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = CategoryRepository.getCategoryData("SELECT * FROM category");
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/views/category/category.jsp").forward(request, response);
        } catch (DatabaseException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading categories.", e);
        } catch (IOException | ServletException e) {
            handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal server error occurred.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("newCategoryName");
        String description = request.getParameter("newCategoryDescription");

        if (name != null && !name.isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);

            try {
                CategoryRepository.saveCategoryData(category);
                response.sendRedirect(request.getContextPath() + "/category"); // Redirect to refresh the category list
            } catch (DatabaseException e) {
                handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving category.",e );
            } catch (IOException e) {
                handleError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while redirecting to login.", e);
            }
        } else {
            request.setAttribute("error", "Category name is required.");
        }
    }
}