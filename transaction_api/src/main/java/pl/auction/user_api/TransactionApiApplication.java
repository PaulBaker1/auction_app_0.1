package pl.auction.user_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApiApplication.class, args);
    }

}
