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
        pickUpPoint.setStatus(PickUpPointStatus.START);
        return pickUpPointRepository.save(pickUpPoint);
    }

    public PickUpPoint getPickUpPoint(Long id){
        List<PickUpPoint> list = pickUpPointRepository.findAll();
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getId().equals(id))
                return list.get(i);
        }
        return null;
    }

    public List<PickUpPoint> getAllPickUpPoints(){
        return pickUpPointRepository.findAll();
    }

    public List<PickUpPoint> getAllEndingPickUpPoints(){
        List <PickUpPoint> list = pickUpPointRepository.findAll();
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getStatus().equals(PickUpPointStatus.START))
                list.remove(i);
        }
        return list;
    }
}
