package com.example.ridesharing.PendingRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PendingRequestService {
    private final PendingRequestRepository pendingRequestRepository;

    @Autowired
    public PendingRequestService(PendingRequestRepository pendingRequestRepository) {
        this.pendingRequestRepository = pendingRequestRepository;
    }
}
