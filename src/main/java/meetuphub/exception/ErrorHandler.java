package meetuphub.exception;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ErrorHandler extends RuntimeException {

    public static void handleError(HttpServletResponse response, int statusCode, String message, Exception e) {
        e.printStackTrace();
        try {
            response.sendError(statusCode, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}