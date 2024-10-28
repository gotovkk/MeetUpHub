package meetuphub;

import meetuphub.models.User;
import meetuphub.repository.UserRepository;

import java.util.List;

public class App implements UserRepository {
    public static void main(String[] args) {
        List<User> users = UserRepository.getUserData("SELECT * FROM \"user\"");
        System.out.println(users);

//        meetuphub.models.User user = new meetuphub.models.User();
//        user.setName("abc");
//        user.setEmail("abc@gmail.com");
//        user.setPasswordHash("123123");
//        meetuphub.repository.UserRepository.saveUserData(user);

        System.out.println(UserRepository.getUserData("SELECT * FROM \"user\""));

//        meetuphub.repository.UserRepository.updateUserData(2, null);
//        meetuphub.repository.UserRepository.deleteUserData(1);
    }
}
