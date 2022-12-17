package com.example.ridesharing.PendingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class PendingRequestController {
    private final PendingRequestService pendingRequestService;

    @Autowired
    public PendingRequestController(PendingRequestService pendingRequestService) {
        this.pendingRequestService = pendingRequestService;
    }
}
