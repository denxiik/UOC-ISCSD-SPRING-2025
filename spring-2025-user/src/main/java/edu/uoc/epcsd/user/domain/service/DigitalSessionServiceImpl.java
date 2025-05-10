package edu.uoc.epcsd.user.domain.service;

import edu.uoc.epcsd.user.application.rest.response.GetUserResponseTest;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.repository.DigitalSessionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DigitalSessionServiceImpl implements DigitalSessionService {   
    
    private final DigitalSessionRepository digitalSessionRepository;
    private final RestTemplate restTemplate;
    
    @Value("${userService.getUserById.url}")
    private String getUserById;   
    
    public List<DigitalSession> findAllDigitalSession() {
        return digitalSessionRepository.findAllDigitalSession();
    }

    @Override
	public List<DigitalSession> findDigitalSessionByUser(Long userId) {    	
        
    	try {
			ResponseEntity<GetUserResponseTest> response = restTemplate.
                    getForEntity(getUserById, GetUserResponseTest.class, userId);
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Could not fetch user with email " + userId);
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new UserNotFoundException(userId);
            }
            throw new RuntimeException("Could not fetch user with email " +userId);
        }   	
    	return digitalSessionRepository.findDigitalSessionByUser(userId);
	}
    
    @Override
	public Optional<DigitalSession> getDigitalSessionById(Long id) {
        return digitalSessionRepository.getDigitalSessionById(id);
	}

	@Override
	public Long createDigitalSession(DigitalSession digitalSession) {
        return digitalSessionRepository.createDigitalSession(digitalSession);
		
	}

	@Override
	public Long updateDigitalSession(Long digitalSessionId, String description, String link, String location, Long userId) {
       
        DigitalSession digitalSession = digitalSessionRepository.getDigitalSessionById(digitalSessionId).orElseThrow(IllegalArgumentException::new);
 
        digitalSession.setDescription(description);
        digitalSession.setLink(link);
        digitalSession.setLocation(location);
        digitalSession.setUserId(userId);
		
		return digitalSessionRepository.updateDigitalSession(digitalSession);
	}

	@Override
	public void removeDigitalSession(Long id) {
		DigitalSession digitalSession = digitalSessionRepository.getDigitalSessionById(id).orElseThrow(IllegalArgumentException::new);
				
        digitalSessionRepository.removeDigitalSession(digitalSession);		
	}
	
}
