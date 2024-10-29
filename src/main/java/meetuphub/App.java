package meetuphub;

import meetuphub.models.*;
import meetuphub.repository.*;
import meetuphub.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());


    public static void main(String[] args) {
            Role adminRole1 = new Role();
            adminRole1.setName("admin1");
            RoleRepository.saveRoleData(adminRole1);

            EventService eventService = new EventService();

            User test = new User();
            test.setName("test");
            test.setEmail("test1@test.com");
            test.setPasswordHash("123123");
            UserRepository.saveUserData(test);
            logger.info("ID пользователя после сохранения: " + test.getId());
            UserRoleRepository.addRoleToUser(test.getId(), adminRole1.getId());


            Event event = new Event(1, "Test Event", "Event description", "active",
                    LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                    LocalDateTime.now(), 1, test.getId());
            EventRepository.saveEventData(event);
            EventUserRepository.addUserToEvent(event.getId(), test.getId());
            logger.info("ID события после сохранения: " + event.getId());

            Category category = new Category();
            category.setName("WebTest");
            CategoryRepository.saveCategoryData(category);

            eventService.addEventToCategory(event.getId(), category.getId());
            logger.info("Категория добавлена к событию.");

            Tag tag = new Tag();
            tag.setName("Tech");
            TagRepository.saveTagData(tag);

            eventService.addTagToEvent(tag.getId(), event.getId());
            logger.info("Тег добавлен к событию.");

            List<Event> eventList = eventService.getAllEvents();
            logger.info("Список всех событий:");
            for (Event e : eventList) {
                logger.info(e.toString());
            }

            List<Category> eventCategories = eventService.getCategoriesByEvent(event.getId());
            logger.info("Категории, связанные с событием:");
            for (Category c : eventCategories) {
                logger.info(c.getName());
            }

            List<Tag> eventTags = eventService.getTagsByEvent(event.getId());
            logger.info("Теги, связанные с событием:");
            for (Tag t : eventTags) {
                logger.info(t.getName());
            }


    }
}

