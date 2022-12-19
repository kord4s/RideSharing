package com.example.ridesharing.JourneyPickUp;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.PickUpPoints.PickUpPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyPickUpService {
    private final JourneyPickUpRepository journeyPickUpRepository;
    private final PickUpPointRepository pickUpPointRepository;

    @Autowired
    public JourneyPickUpService(JourneyPickUpRepository journeyPickUpRepository, PickUpPointRepository pickUpPointRepository) {
        this.journeyPickUpRepository = journeyPickUpRepository;
        this.pickUpPointRepository = pickUpPointRepository;
    }

    public JourneyPickUp addNewJourneyPickUp(JourneyPickUp journeyPickUp, Journey journey, Long startID, Long endID){
        journeyPickUp.setUser(journey.getUser());
        journeyPickUp.setStartPoint(pickUpPointRepository.getOne(startID));
        journeyPickUp.setEndPoint(pickUpPointRepository.getOne(endID));
        journeyPickUp.setJourney(journey);
        journeyPickUpRepository.save(journeyPickUp);
        return journeyPickUpRepository.save(journeyPickUp);
    }
}
