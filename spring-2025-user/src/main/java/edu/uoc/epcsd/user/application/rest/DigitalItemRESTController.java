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
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{digitalItemId}"
    // and create the method getDigitalItemById(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding getDigitalItemById method 
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/digitalItemBySession"
    // and create the method findDigitalItemBySession(@RequestParam @NotNull Long digitalSessionId)
    // which call the corresponding findDigitalItemBySession method 
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/addItem"
    // and create the method addDigitalItem(@RequestBody @Valid CreateDigitalItemRequest createDigitalItemRequest)
    // which call the corresponding addDigitalItemm method 

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/updateItem/{digitalItemId}"
    // and create the method updateDigitalItem(@PathVariable @NotNull Long digitalItemId, @RequestBody @Valid CreateDigitalItemRequest updateDigitalItemRequest)
    // which call the corresponding updateDigitalItem method 

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/reviewDigitalItem/{digitalItemId}"
    // and create the method setDigitalItemForReview(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding setDigitalItemForReview method     
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/approveDigitalItem/{digitalItemId}"
    // and create the method approvePendingDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding approvePendingDigitalItem method     
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/rejectDigitalItem/{digitalItemId}"
    // and create the method rejectPendingDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding rejectPendingDigitalItem method    
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/dropItem/{digitalItemId}"
    // and create the method dropDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding dropDigitalItem method 
  
}
