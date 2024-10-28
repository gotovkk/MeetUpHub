package meetuphub;

import meetuphub.models.Event;
import meetuphub.models.User;
import meetuphub.repository.EventRepository;
import meetuphub.repository.EventUserRepository;
import meetuphub.repository.UserRepository;
import meetuphub.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

public class App {
    public static void main(String[] args) {
        EventService eventService = new EventService();

        User test = new User();
        test.setName("test11");
        test.setEmail("tes1231t@test.com");
        test.setPasswordHash("testtest");
        UserRepository.saveUserData(test);

        System.out.println(test.getId());

        Event event = new Event(1, "Test Event", "Event description", "active",
                LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                LocalDateTime.now(), 1, test.getId());

        EventRepository.saveEventData(event);

        EventUserRepository.addUserToEvent(event.getId(), test.getId());
        System.out.println("ID события после сохранения: " + event.getId());

        List<Event> eventList = eventService.getAllEvents();

        System.out.println("Список всех событий:");
        for (Event e : eventList) {
            System.out.println(e);
        }

    }
}
