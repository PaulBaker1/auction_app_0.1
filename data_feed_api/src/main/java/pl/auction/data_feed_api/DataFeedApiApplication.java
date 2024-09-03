package pl.auction.data_feed_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DataFeedApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataFeedApiApplication.class, args);
    }

}
