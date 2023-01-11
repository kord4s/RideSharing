package com.example.ridesharing.Journey.AdditionalData;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.Journey.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.ridesharing.Car.typeOfEngine.BENZYNOWY;
import static com.example.ridesharing.Car.typeOfEngine.DIESEL;

@RestController
@RequestMapping
//@CrossOrigin(origins = "") <--- adres frontu
public class AdditionalDataService {
    private final AdditionalDataRepository additionalDataRepository;

    private final JourneyRepository journeyRepository;

    @Autowired
    public AdditionalDataService(AdditionalDataRepository additionalDataRepository, JourneyRepository journeyRepository) {
        this.additionalDataRepository = additionalDataRepository;
        this.journeyRepository = journeyRepository;
    }

    public AdditionalData createAdditionalData(Long journeyID) {
        Journey journey = journeyRepository.getOne(journeyID);
        AdditionalData additionalData = new AdditionalData();
        additionalData.setJourney(journey);
        double fuelUsage = 0;

        //typ silnika -> wielkość silnika -> spalanie -> zanieczyszczenia
        switch (journey.getCar().getEngineType()) {
            case LPG -> {

                if (journey.getCar().getEngineCapacity() < 1.0) {
                    fuelUsage = 9;
                } else if (journey.getCar().getEngineCapacity() < 1.5) {
                    fuelUsage = 10;
                } else if (journey.getCar().getEngineCapacity() < 2.0) {
                    fuelUsage = 11;
                } else if (journey.getCar().getEngineCapacity() < 2.5) {
                    fuelUsage = 13;
                } else {
                    fuelUsage = 16;
                }

                additionalData.setUsedFuel((journey.getDistance()/1000.0) * (fuelUsage / 100));
                additionalData.setCO(String.valueOf(additionalData.getUsedFuel() * 0.001));
                additionalData.setCO2(String.valueOf(additionalData.getUsedFuel() * 1.51));
                additionalData.setSOx(String.valueOf(additionalData.getUsedFuel() * 0.0001));
                additionalData.setNOx(String.valueOf(additionalData.getUsedFuel() * 0.0003));

            }
            case DIESEL -> {

                if (journey.getCar().getEngineCapacity() < 1.0) {
                    fuelUsage = 7;
                } else if (journey.getCar().getEngineCapacity() < 1.5) {
                    fuelUsage = 8;
                } else if (journey.getCar().getEngineCapacity() < 2.0) {
                    fuelUsage = 8;
                } else if (journey.getCar().getEngineCapacity() < 2.5) {
                    fuelUsage = 9;
                } else {
                    fuelUsage = 10;
                }
                additionalData.setUsedFuel((journey.getDistance()/1000.0) * (fuelUsage / 100));
                additionalData.setCO(String.valueOf(additionalData.getUsedFuel() * 2.6));
                additionalData.setCO2(String.valueOf(additionalData.getUsedFuel() * 2.81));
                additionalData.setSOx(String.valueOf(additionalData.getUsedFuel() * 0.04));
                additionalData.setNOx(String.valueOf(additionalData.getUsedFuel() * 0.03));

            }
            case BENZYNOWY -> {

                if (journey.getCar().getEngineCapacity() < 1.0) {
                    fuelUsage = 8;
                } else if (journey.getCar().getEngineCapacity() < 1.5) {
                    fuelUsage = 9;
                } else if (journey.getCar().getEngineCapacity() < 2.0) {
                    fuelUsage = 10;
                } else if (journey.getCar().getEngineCapacity() < 2.5) {
                    fuelUsage = 11;
                } else {
                    fuelUsage = 12;
                }

                additionalData.setUsedFuel((journey.getDistance()/1000.0) * (fuelUsage / 100));
                additionalData.setCO(String.valueOf(additionalData.getUsedFuel() * 0.0023));
                additionalData.setCO2(String.valueOf(additionalData.getUsedFuel() * 2.5));
                additionalData.setSOx(String.valueOf(additionalData.getUsedFuel() * 0.00006));
                additionalData.setNOx(String.valueOf(additionalData.getUsedFuel() * 0.0002));
            }
        }

        if(Double.parseDouble(additionalData.getCO())<0.01){
            additionalData.setCO("0.01");
        }

        if(Double.parseDouble(additionalData.getCO2())<0.01){
            additionalData.setCO2("0.01");
        }

        if(Double.parseDouble(additionalData.getSOx())<0.01){
            additionalData.setSOx("0.01");
        }

        if(Double.parseDouble(additionalData.getNOx())<0.01){
            additionalData.setNOx("0.01");
        }

        return additionalData;
    }

}
