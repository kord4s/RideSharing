package com.example.ridesharing.PendingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
//@CrossOrigin(origins = "") <--- adres frontu
public class PendingRequestController {
    private final PendingRequestService pendingRequestService;

    @Autowired
    public PendingRequestController(PendingRequestService pendingRequestService) {
        this.pendingRequestService = pendingRequestService;
    }
    @PostMapping("/add/new")
    public ResponseEntity<PendingRequest> addNewRequestWithNewPickUpPoint(@RequestParam Long userID, @RequestParam Long journeyID, @RequestParam Double XMap, @RequestParam Double YMap){
        return new ResponseEntity<>(pendingRequestService.addNewPendingRequestWithNewPickUpPoint(userID, journeyID, XMap, YMap), HttpStatus.OK);
    }

    @PostMapping("/add/exist")
    public ResponseEntity<PendingRequest> addNewRequestWithExistingPickUpPoint(@RequestParam Long userID, @RequestParam Long journeyID, @RequestParam Long startID){
        return new ResponseEntity<>(pendingRequestService.addNewPendingRequestWithExistingPickUpPoint(userID, journeyID, startID), HttpStatus.OK);
    }


    @PutMapping("/{id}/accept")
    public ResponseEntity<PendingRequest> acceptRequest(@PathVariable("id") Long id){
        return new ResponseEntity<>(pendingRequestService.acceptRequest(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<PendingRequest> rejectRequest(@PathVariable("id") Long id){
        return new ResponseEntity<>(pendingRequestService.rejectRequest(id), HttpStatus.OK);
    }

}
