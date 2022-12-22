package com.example.ridesharing.PendingRequest;

import com.example.ridesharing.Journey.JourneyRepository;
import com.example.ridesharing.Journey.JourneyService;
import com.example.ridesharing.PickUpPoints.PickUpPointService;
import com.example.ridesharing.User.RidesharingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PendingRequestService {
    private final PendingRequestRepository pendingRequestRepository;
    private final RidesharingUserRepository ridesharingUserRepository;
    private final JourneyRepository journeyRepository;


    private final PickUpPointService pickUpPointService;

    @Autowired
    public PendingRequestService(PendingRequestRepository pendingRequestRepository, RidesharingUserRepository ridesharingUserRepository, JourneyRepository journeyRepository,  PickUpPointService pickUpPointService) {
        this.pendingRequestRepository = pendingRequestRepository;
        this.ridesharingUserRepository = ridesharingUserRepository;
        this.journeyRepository = journeyRepository;
        this.pickUpPointService = pickUpPointService;
    }

    public PendingRequest addNewPendingRequestWithNewPickUpPoint(Long userID, Long journeyID, Double XMap, Double YMap){
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setUser(ridesharingUserRepository.getOne(userID));
        pendingRequest.setJourney(journeyRepository.getOne(journeyID));
        pendingRequest.setStatus(RequestStatus.SENT);
        pendingRequest.setStartPoint(pickUpPointService.addNewPickUpPoint(XMap, YMap));
        return pendingRequestRepository.save(pendingRequest);
    }

    public PendingRequest addNewPendingRequestWithExistingPickUpPoint(Long userID, Long journeyID, Long startID){
        PendingRequest pendingRequest = new PendingRequest();
        pendingRequest.setUser(ridesharingUserRepository.getOne(userID));
        pendingRequest.setJourney(journeyRepository.getOne(journeyID));
        pendingRequest.setStatus(RequestStatus.SENT);
        pendingRequest.setStartPoint(pickUpPointService.getPickUpPoint(startID));
        return pendingRequestRepository.save(pendingRequest);
    }

    public PendingRequest rejectRequest(Long id){
        PendingRequest pendingRequest = pendingRequestRepository.getOne(id);
        pendingRequest.setStatus(RequestStatus.REJECTED);
        return pendingRequestRepository.save(pendingRequest);
    }

    public PendingRequest acceptRequest(Long id){
        PendingRequest pendingRequest = pendingRequestRepository.getOne(id);
        pendingRequest.setStatus(RequestStatus.ACCEPTED);
        return pendingRequestRepository.save(pendingRequest);
    }





}
