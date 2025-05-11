package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalSessionRequest;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.service.DigitalSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/digital")
public class DigitalSessionRESTController {

    private final DigitalSessionService digitalSessionService;

    @GetMapping("/allDigital")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalSession> getAllDigitalSession() {
        log.trace("getAllDigitalSession");

        return digitalSessionService.findAllDigitalSession();
    }
    
    // IMPLEMENTED all bellows
    @GetMapping("/{digitalSessionId}")
    public ResponseEntity<DigitalSession> getDigitalSessionById(@PathVariable @NotNull Long digitalSessionId) {
        log.trace("getDigitalSessionById");
        Optional<DigitalSession> digitalSession = digitalSessionService.getDigitalSessionById(digitalSessionId);
        return digitalSession.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/digitalByUser")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalSession> findDigitalSessionByUser(@RequestParam @NotNull Long userId) {
        log.trace("findDigitalSessionByUser");
        return digitalSessionService.findDigitalSessionByUser(userId);
    }

    @PostMapping("/createDigital")
    public ResponseEntity<Long> createDigitalSession(@RequestBody @Valid CreateDigitalSessionRequest createDigitalSessionRequest) {
        log.trace("createDigitalSession");
        DigitalSession digitalSession = DigitalSession.builder()
                .description(createDigitalSessionRequest.getDescription())
                .link(createDigitalSessionRequest.getLink())
                .location(createDigitalSessionRequest.getLocation())
                .userId(createDigitalSessionRequest.getUserId())
                .build();
        Long digitalSessionId = digitalSessionService.createDigitalSession(digitalSession);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(digitalSessionId)
                .toUri();
        return ResponseEntity.created(uri).body(digitalSessionId);
    }

    @PutMapping("/updateDigital/{digitalSessionId}")
    public ResponseEntity<Long> updateDigitalSession(@PathVariable @NotNull Long digitalSessionId, @RequestBody @Valid CreateDigitalSessionRequest updateDigitalSessionRequest) {
        log.trace("updateDigitalSession");
        Long updatedId = digitalSessionService.updateDigitalSession(
                digitalSessionId,
                updateDigitalSessionRequest.getDescription(),
                updateDigitalSessionRequest.getLink(),
                updateDigitalSessionRequest.getLocation(),
                updateDigitalSessionRequest.getUserId()
        );
        if (updatedId != null) {
            return ResponseEntity.ok(updatedId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/removeDigital/{digitalSessionId}")
    public ResponseEntity<Void> removeDigitalSession(@PathVariable @NotNull Long digitalSessionId) {
        log.trace("removeDigitalSession with id: {}", digitalSessionId);
        digitalSessionService.removeDigitalSession(digitalSessionId);
        return ResponseEntity.noContent().build();
    }

}
