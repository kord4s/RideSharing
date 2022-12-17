package com.example.ridesharing.JourneyPickUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyPickUpService {
    private final JourneyPickUpRepository journeyPickUpRepository;

    @Autowired
    public JourneyPickUpService(JourneyPickUpRepository journeyPickUpRepository) {
        this.journeyPickUpRepository = journeyPickUpRepository;
    }
}
