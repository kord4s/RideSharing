package com.example.ridesharing.JourneyPickUp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class JourneyPickUpController {
    private final JourneyPickUpService journeyPickUpService;

    @Autowired
    public JourneyPickUpController(JourneyPickUpService journeyPickUpService) {
        this.journeyPickUpService = journeyPickUpService;
    }
}
