package edu.uoc.epcsd.user.domain.service;

import edu.uoc.epcsd.user.application.rest.response.GetProductResponse;
import edu.uoc.epcsd.user.domain.Alert;
import edu.uoc.epcsd.user.domain.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    @Value("${productService.getProductDetails.url}")
    private String productServiceUrl;


    public List<Alert> findAllAlerts() {
        return alertRepository.findAllAlerts();
    }

    public Optional<Alert> findAlertById(Long id) {
        return alertRepository.findAlertById(id);
    }

    @Override
    public List<Alert> findAlertsByProductAndDate(Long productId, LocalDate availableOnDate) {
        return alertRepository.findAlertsByProductAndDate(productId, availableOnDate);
    }

    @Override
    public List<Alert> findAlertsByUserAndInterval(Long userId, LocalDate fromDate, LocalDate toDate) {
        return alertRepository.findAlertsByUserAndInterval(userId, fromDate, toDate);
    }

    public Long createAlert(Alert alert) {

        // check that the product exists
        new RestTemplate().getForEntity(productServiceUrl, GetProductResponse.class, alert.getProductId()).getBody();

        return alertRepository.createAlert(alert);
    }
}
