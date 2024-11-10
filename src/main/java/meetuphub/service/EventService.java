package meetuphub.service;

import meetuphub.model.Category;
import meetuphub.model.Event;
import meetuphub.model.User;
import meetuphub.repository.*;

import java.util.List;

public class EventService {
    // после добавить проверки и перенести часть методов с запросами в репозиторий
    String selectEvent = "SELECT * FROM event WHERE id = ? ";

    public void addUserToEvent(int eventId, int userId) {

        List<Event> events = EventRepository.getEventData(selectEvent, eventId);
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден.");
        }

        List<User> users = UserRepository.getUserData("SELECT * FROM \"user\" WHERE id = ? ", userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        EventUserRepository.addUserToEvent(eventId, userId);
    }

    public void addEventToCategory(int eventId, int categoryId) {

        List<Event> events = EventRepository.getEventData(selectEvent, eventId);
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден.");
        }

        List<Category> categories = CategoryRepository.getCategoryData("SELECT * FROM category WHERE id = ? ", categoryId);
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("Категория не найдена.");
        }
        EventCategoryRepository.addEventToCategory(eventId, categoryId);
    }

    public List<Event> getAllEvents() {
        return EventRepository.getAllEvents();
    }

    public List<Category> getCategoriesByEvent(int eventId) {
        return EventCategoryRepository.getCategoriesByEventId(eventId);
    }


}



