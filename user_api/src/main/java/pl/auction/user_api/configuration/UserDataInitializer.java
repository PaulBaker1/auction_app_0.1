package pl.auction.user_api.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.auction.user_api.model.User;
import pl.auction.user_api.model.UserRepository;

@Configuration
public class UserDataInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initData() {
        userRepository.save(User.builder().username("John Doe").email("john@example.com").build());
        userRepository.save(User.builder().username("Jane Smith").email("jane@example.com").build());
        userRepository.save(User.builder().username("Alice Johnson").email("alice@example.com").build());
    }
}

