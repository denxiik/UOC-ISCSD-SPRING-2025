package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalItemRequest;
import edu.uoc.epcsd.user.domain.Alert;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.service.DigitalItemService;
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
@RequestMapping("/digitalItem")
public class DigitalItemRESTController {

    private final DigitalItemService digitalItemService;

    @GetMapping("/allItems")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalItem> getAllDigitalItem() {
        log.trace("getAllDigitalItem");

        return digitalItemService.findAllDigitalItem();
    }

    // IMPLEMENTED all bellows
    @GetMapping("/{digitalItemId}")
    public ResponseEntity<DigitalItem> getDigitalItemById(@PathVariable @NotNull Long digitalItemId) {
        log.trace("getDigitalItemById");
        Optional<DigitalItem> digitalItem = digitalItemService.getDigitalItemById(digitalItemId);
        return digitalItem.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/digitalItemBySession")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalItem> findDigitalItemBySession(@RequestParam @NotNull Long digitalSessionId) {
        log.trace("findDigitalItemBySession");
        return digitalItemService.findDigitalItemBySession(digitalSessionId);
    }
    

    @PostMapping("/addItem")
    public ResponseEntity<Long> addDigitalItem(@RequestBody @Valid CreateDigitalItemRequest createDigitalItemRequest) {
        log.trace("addDigitalItem");
        DigitalItem digitalItem = DigitalItem.builder()
                .description(createDigitalItemRequest.getDescription())
                .link(createDigitalItemRequest.getLink())
                .lat(createDigitalItemRequest.getLat())
                .lon(createDigitalItemRequest.getLon())
                .digitalSessionId(createDigitalItemRequest.getDigitalSessionId())
                .build();
        Long digitalItemId = digitalItemService.addDigitalItem(digitalItem);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(digitalItemId)
                .toUri();
        return ResponseEntity.created(uri).body(digitalItemId);
    }

    @PutMapping("/updateItem/{digitalItemId}")
    public ResponseEntity<Long> updateDigitalItem(@PathVariable @NotNull Long digitalItemId, @RequestBody @Valid CreateDigitalItemRequest updateDigitalItemRequest) {
        log.trace("updateDigitalItem");
        Long updatedId = digitalItemService.updateDigitalItem(
                digitalItemId,
                updateDigitalItemRequest.getDescription(),
                updateDigitalItemRequest.getLink(),
                updateDigitalItemRequest.getLat(),
                updateDigitalItemRequest.getLon()
        );
        if (updatedId != null) {
            return ResponseEntity.ok(updatedId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reviewDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> setDigitalItemForReview(@PathVariable @NotNull Long digitalItemId) {
        log.trace("setDigitalItemForReview");
        digitalItemService.setDigitalItemForReview(digitalItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/approveDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> approvePendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("approvePendingDigitalItem");
        digitalItemService.approvePendingDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rejectDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> rejectPendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("rejectPendingDigitalItem");
        digitalItemService.rejectPendingDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/dropItem/{digitalItemId}")
    public ResponseEntity<Void> dropDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("dropDigitalItem");
        digitalItemService.dropDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }
  
}
