package com.example.ridesharing.Journey.AdditionalData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class AdditionalDataService {
    private final AdditionalDataRepository additionalDataRepository;

    @Autowired
    public AdditionalDataService(AdditionalDataRepository additionalDataRepository) {
        this.additionalDataRepository = additionalDataRepository;
    }
}
