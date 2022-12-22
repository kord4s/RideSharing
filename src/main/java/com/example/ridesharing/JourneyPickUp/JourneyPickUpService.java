package com.example.ridesharing.JourneyPickUp;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.PickUpPoints.PickUpPointRepository;
import com.example.ridesharing.PickUpPoints.PickUpPointService;
import com.example.ridesharing.User.RidesharingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyPickUpService {
    private final JourneyPickUpRepository journeyPickUpRepository;
    private final PickUpPointRepository pickUpPointRepository;

    private final RidesharingUserRepository ridesharingUserRepository;

    private final PickUpPointService pickUpPointService;

    @Autowired
    public JourneyPickUpService(JourneyPickUpRepository journeyPickUpRepository, PickUpPointRepository pickUpPointRepository, RidesharingUserRepository ridesharingUserRepository, PickUpPointService pickUpPointService) {
        this.journeyPickUpRepository = journeyPickUpRepository;
        this.pickUpPointRepository = pickUpPointRepository;
        this.ridesharingUserRepository = ridesharingUserRepository;
        this.pickUpPointService = pickUpPointService;
    }

    public JourneyPickUp addFirstJourneyPickUp(JourneyPickUp journeyPickUp, Journey journey, Long startID, Long endID){
        journeyPickUp.setUser(journey.getUser());
        journeyPickUp.setStartPoint(pickUpPointRepository.getOne(startID));
        journeyPickUp.setEndPoint(pickUpPointRepository.getOne(endID));
        journeyPickUp.setPickUpOrder(0);
        journeyPickUp.setJourney(journey);
        return journeyPickUpRepository.save(journeyPickUp);
    }

    public JourneyPickUp addPassengerJourneyPickUpWithExistingPoint(JourneyPickUp journeyPickUp, Journey journey, Long startID, Long userID){
        journeyPickUp.setUser(ridesharingUserRepository.getOne(userID));
        journeyPickUp.setStartPoint(pickUpPointRepository.getOne(startID));
        journeyPickUp.setEndPoint(journey.getJourneyPickUps().get(0).getEndPoint());
        journeyPickUp.setPickUpOrder(journey.getJourneyPickUps().size());
        journeyPickUp.setJourney(journey);
        return journeyPickUpRepository.save(journeyPickUp);
    }
    public JourneyPickUp addPassengerJourneyPickUpWithNewPoint(JourneyPickUp journeyPickUp, Journey journey, Double XMap, Double YMap, Long userID){
        journeyPickUp.setUser(ridesharingUserRepository.getOne(userID));
        journeyPickUp.setStartPoint(pickUpPointService.addNewPickUpPoint(XMap, YMap));
        journeyPickUp.setEndPoint(journey.getJourneyPickUps().get(0).getEndPoint());
        journeyPickUp.setPickUpOrder(journey.getJourneyPickUps().size());
        journeyPickUp.setJourney(journey);
        return journeyPickUpRepository.save(journeyPickUp);
    }
}
