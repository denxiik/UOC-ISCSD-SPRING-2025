package edu.uoc.epcsd.notification.domain.service;

import edu.uoc.epcsd.notification.application.kafka.ProductMessage;
import edu.uoc.epcsd.notification.application.rest.dtos.GetProductResponse;
import edu.uoc.epcsd.notification.application.rest.dtos.GetUserResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${userService.getUsersToAlert.url}")
    private String userServiceUrl;

    @Value("${productService.getProductDetails.url}")
    private String productServiceUrl;

    @Override
    public void notifyProductAvailable(ProductMessage productMessage) {
        log.info("notifyProductAvailable");

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Call the User microservice to get users with alerts for the product and date
            String getUsersUrl = userServiceUrl + "?productId=" + productMessage.getProductId() + "&availableOnDate=" + LocalDate.now();
            log.info("Calling User Service URL: {}", getUsersUrl);

            ResponseEntity<GetUserResponse[]> response = restTemplate.getForEntity(getUsersUrl, GetUserResponse[].class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<GetUserResponse> usersToAlert = Arrays.asList(response.getBody());
                log.info("Retrieved {} users to alert for product {}", usersToAlert.size(), productMessage.getProductId());

                // Simulate email notification for each alerted user
                usersToAlert.forEach(user -> {
                    log.info("Sending an email to user {}", user.getFullName());
                });
            } else {
                log.warn("Failed to retrieve users to alert from User Service. Status: {}", response.getStatusCode());
            }

        } catch (Exception e) {
            log.error("Error while querying User Service for alerted users: {}", e.getMessage());
        }
    }
}
