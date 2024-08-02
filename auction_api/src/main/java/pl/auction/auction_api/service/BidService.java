package pl.auction.auction_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.auction.auction_api.model.Bid;
import pl.auction.auction_api.model.BidRepository;
import pl.auction.auction_api.service.dto.User;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = Logger.getLogger(BidService.class.getName());

    public Bid createBid(Bid bid) {
        logger.info("Creating bid: " + bid);

        // Log the initial userEmail and paymentMethod
        logger.info("Initial userEmail: " + bid.getUserEmail());
        logger.info("Payment Method: " + bid.getPaymentMethod());

        if (bid.getUserEmail() == null || bid.getUserEmail().isEmpty()) {
            logger.severe("User email is missing");
            throw new RuntimeException("User email is missing");
        }

        User user;
        try {
            user = Optional.ofNullable(restTemplate.getForObject("http://localhost:8081/api/users/email/" + bid.getUserEmail(), User.class))
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + bid.getUserEmail()));
        } catch (Exception e) {
            logger.severe("Error retrieving user: " + e.getMessage());
            throw new RuntimeException("Error retrieving user: " + e.getMessage());
        }

        // Log the user details retrieved
        logger.info("Retrieved user: " + user);

        bid.setUserEmail(user.getEmail()); // Ensure user email is set correctly

        // Log the userEmail before saving the bid
        logger.info("userEmail before saving bid: " + bid.getUserEmail());

        try {
            return bidRepository.save(bid);
        } catch (Exception e) {
            logger.severe("Error saving bid: " + e.getMessage());
            throw new RuntimeException("Error saving bid: " + e.getMessage());
        }
    }
}
