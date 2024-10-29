package meetuphub;

import meetuphub.models.Event;
import meetuphub.models.Role;
import meetuphub.models.User;
import meetuphub.repository.*;
import meetuphub.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

public class App  {
    public static void main(String[] args) {

        Role adminRole = new Role();
        adminRole.setName("admin");
        RoleRepository.saveRoleData(adminRole);
        UserRoleRepository.addRoleToUser(7, 1);
        EventService eventService = new EventService();


        User test = new User();
        test.setName("test11");
        test.setEmail("tes121131t@test.com");
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
