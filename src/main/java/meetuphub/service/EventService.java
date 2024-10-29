package meetuphub.service;

import meetuphub.models.Category;
import meetuphub.models.Event;
import meetuphub.models.Tag;
import meetuphub.models.User;
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

    public void addTagToEvent(int tagId, int eventId) {
        List<Event> events = EventRepository.getEventData(selectEvent, eventId);
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден");
        }

        List<Tag> tags = TagRepository.getTagData("SELECT * FROM tag WHERE id = ?", tagId);
        if (tags.isEmpty()) {
            throw new IllegalArgumentException("Тег не найден");
        }
        EventTagRepository.addTagToEvent(tagId, eventId);
    }

    public List<Event> getAllEvents() {
        return EventRepository.getAllEvents();
    }

    public List<Category> getCategoriesByEvent(int eventId) {
        return EventCategoryRepository.getCategoriesByEventId(eventId);
    }

    public List<Tag> getTagsByEvent(int eventId) {
        return EventTagRepository.getTagsByEventId(eventId);
    }


}



