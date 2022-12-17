package com.example.ridesharing.Journey.AdditionalData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class AdditionalDataController {
    private final AdditionalDataService additionalDataService;

    @Autowired
    public AdditionalDataController(AdditionalDataService additionalDataService) {
        this.additionalDataService = additionalDataService;
    }
}
