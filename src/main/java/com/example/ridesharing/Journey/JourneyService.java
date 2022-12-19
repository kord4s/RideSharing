package com.example.ridesharing.Journey;

import com.example.ridesharing.Car.CarRepository;
import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import com.example.ridesharing.JourneyPickUp.JourneyPickUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyService {
    private final JourneyRepository journeyRepository;
    private final CarRepository carRepository;

    private final JourneyPickUpService journeyPickUpService;




    @Autowired
    public JourneyService(JourneyRepository journeyRepository, CarRepository carRepository, JourneyPickUpService journeyPickUpService) {
        this.journeyRepository = journeyRepository;
        this.carRepository = carRepository;
        this.journeyPickUpService = journeyPickUpService;
    }

    public Journey addJourney(Journey journey){
        return journeyRepository.save(journey);
    }

    public Journey nextStepAddingJourney(Journey journey, Long carID){
        journey.setCar(carRepository.getOne(carID));
        journey.setUser(journey.getCar().getUser());
        return journeyRepository.save(journey);
    }

    public Journey finishAddingJourney(Long id, JourneyPickUp journeyPickUp, Long startID, Long endID){
        Journey journey = journeyRepository.getOne(id);
        journeyPickUp=journeyPickUpService.addNewJourneyPickUp(journeyPickUp,journey,startID,endID);
        journey.getJourneyPickUps().add(journeyPickUp);
        return journeyRepository.save(journey);
    }
}
