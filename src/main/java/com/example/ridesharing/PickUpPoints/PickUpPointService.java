package com.example.ridesharing.PickUpPoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PickUpPointService {
    private final PickUpPointRepository pickUpPointRepository;

    @Autowired
    public PickUpPointService(PickUpPointRepository pickUpPointRepository) {
        this.pickUpPointRepository = pickUpPointRepository;
    }

    public PickUpPoint addNewPickUpPoint(Double xmap, Double ymap){
        PickUpPoint pickUpPoint = new PickUpPoint(xmap,ymap);
        return pickUpPointRepository.save(pickUpPoint);
    }

    public PickUpPoint getPickUpPoint(Long id){
        return pickUpPointRepository.getOne(id);
    }

    public List<PickUpPoint> getAllPickUpPoints(){
        return pickUpPointRepository.findAll();
    }
}
