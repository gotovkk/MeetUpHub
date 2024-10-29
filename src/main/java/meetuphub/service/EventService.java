package meetuphub.service;

import meetuphub.models.Event;
import meetuphub.models.User;
import meetuphub.repository.EventRepository;
import meetuphub.repository.EventUserRepository;
import meetuphub.repository.UserRepository;

import java.util.List;

public class EventService {

    public void addUserToEvent(int eventId, int userId) {

        List<Event> events = EventRepository.getEventData("SELECT * FROM event WHERE id = ? ", eventId);
        if (events.isEmpty()) {
            throw new IllegalArgumentException("Ивент не найден.");
        }

        List<User> users = UserRepository.getUserData("SELECT * FROM \"user\" WHERE id = ? ", userId);
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        EventUserRepository.addUserToEvent(eventId, userId);
    }

    public List<Event> getAllEvents() {
        return EventRepository.getAllEvents();
    }




}



