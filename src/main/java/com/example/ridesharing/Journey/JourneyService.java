package com.example.ridesharing.Journey;

import com.example.ridesharing.Car.CarRepository;
import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import com.example.ridesharing.JourneyPickUp.JourneyPickUpService;
import com.example.ridesharing.PickUpPoints.PickUpPoint;
import com.example.ridesharing.PickUpPoints.PickUpPointRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JourneyService {
    private final JourneyRepository journeyRepository;
    private final CarRepository carRepository;

    private final JourneyPickUpService journeyPickUpService;

    private final PickUpPointRepository pickUpPointRepository;




    @Autowired
    public JourneyService(JourneyRepository journeyRepository, CarRepository carRepository, JourneyPickUpService journeyPickUpService, PickUpPointRepository pickUpPointRepository) {
        this.journeyRepository = journeyRepository;
        this.carRepository = carRepository;
        this.journeyPickUpService = journeyPickUpService;
        this.pickUpPointRepository = pickUpPointRepository;
    }



    //test
    public Double getDistance(JourneyPickUp journeyPickUp){
        final String uri = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248102ff08a64b64d45bd78062f20d3b9b8&start="+
                journeyPickUp.getStartPoint().getXMap()+","+journeyPickUp.getStartPoint().getYMap()+"&end="+journeyPickUp.getEndPoint().getXMap()+","+journeyPickUp.getEndPoint().getYMap();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(result);
            return jsonNode.get("features").get(0).get("properties").get("summary").get("distance").asDouble();
        }catch(Exception e){
            return 0.0;
        }
    }

    public Journey addJourney(Journey journey){
        return journeyRepository.save(journey);
    }

    public Journey getJourneyByID(Long id){
        List<Journey> list = journeyRepository.findAll();
        for(int i=0; i<list.size(); i++){
            if(id.equals(list.get(i).getId()))
                return list.get(i);
        }
        return null;
    }

    public List<Journey> getAllJourneys(){
        return journeyRepository.findAll();
    }

    public Journey nextStepAddingJourney(Journey journey, Long carID){
        journey.setCar(carRepository.getOne(carID));
        journey.setUser(journey.getCar().getUser());
        return journeyRepository.save(journey);
    }

    public Journey finishAddingJourney(Long id, JourneyPickUp journeyPickUp, Long startID, Long endID){
        Journey journey = journeyRepository.getOne(id);
        journeyPickUp=journeyPickUpService.addFirstJourneyPickUp(journeyPickUp,journey,startID,endID);
        journey.getJourneyPickUps().add(journeyPickUp);
        journey.setDistance(getDistance(journey.getJourneyPickUps().get(0)));
        return journeyRepository.save(journey);
    }



    public Journey addNextJourneyPickUpPointWithExistingPickUpPoint(JourneyPickUp journeyPickUp, Long id, Long userID, Long startID){
        Journey journey = journeyRepository.getOne(id);
        journeyPickUp = journeyPickUpService.addPassengerJourneyPickUpWithExistingPoint(journeyPickUp,journey,startID,userID);
        journey.getJourneyPickUps().add(journeyPickUp);
        journey.setCountOfPassengers(journey.getCountOfPassengers()-1);
        findOrderOfPickUpPoints(journey.getId());
        return journeyRepository.save(journey);
    }

    public Journey addNextJourneyPickUpPointWithNewPickUpPoint(JourneyPickUp journeyPickUp, Long id, Long userID, Double XMap, Double YMap){
        Journey journey = journeyRepository.getOne(id);
        journeyPickUp = journeyPickUpService.addPassengerJourneyPickUpWithNewPoint(journeyPickUp,journey,XMap, YMap, userID);
        journey.getJourneyPickUps().add(journeyPickUp);
        journey.setCountOfPassengers(journey.getCountOfPassengers()-1);
        findOrderOfPickUpPoints(journey.getId());
        return journeyRepository.save(journey);
    }



    public Journey findOrderOfPickUpPoints(Long id){
        Journey journey = journeyRepository.getOne(id);
        int userIndex=0;
        String uri="https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248102ff08a64b64d45bd78062f20d3b9b8&start="
                , fullURI, result;
        Double distance=0.0, tempDistance=0.0, fullDistance=0.0;
        Integer indexOfWaypoint=0, tempIndex=0;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Integer> indexList = new ArrayList<Integer>();


        //finding user JourneyPickUp
        for(int i=0; i<journey.getJourneyPickUps().size(); i++){
            if(journey.getJourneyPickUps().get(i).getUser().equals(journey.getUser())){
                userIndex=i;
                indexList.add(i);
                break;
            }
        }

        //trying to find order
        for(int i=0; i<journey.getJourneyPickUps().size(); i++){
            distance=0.0;
            tempDistance=0.0;
            //

            //driver starting point is always the starting point of journey
            if(i==0){
                indexOfWaypoint=userIndex;
                journey.getJourneyPickUps().get(indexOfWaypoint).setPickUpOrder(i);
                System.out.println("i: "+i+" index: "+indexOfWaypoint);
                continue;
            }


            for(int j=0; j<journey.getJourneyPickUps().size(); j++){
                if(indexList.contains(j)) continue;
                // if this JourneyPickUp is already used, pass to next iteration

                fullURI=uri+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap()
                        +"&end="+journey.getJourneyPickUps().get(j).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(j).getStartPoint().getYMap();
                result = restTemplate.getForObject(fullURI, String.class);

                try{
                    JsonNode jsonNode = mapper.readTree(result);
                    tempDistance=jsonNode.get("features").get(0).get("properties").get("summary").get("distance").asDouble();

                    //assigment for first iteration
                    if(distance.equals(0.0)){
                        distance=tempDistance;
                        tempIndex=j;
                    }

                }catch(Exception e){
                    tempDistance=0.0;
                }

                //same starting point as last one
                if(tempDistance==0.0){
                    tempIndex=j;
                    break;
                }

                //if the distance between points is shorter than last one, change it and assign new index
                if(tempDistance<distance){
                    distance=tempDistance;
                    tempIndex=j;
                }
                System.out.println("j: "+j+" distance: "+tempDistance+ " index: "+ tempIndex);
            }
            indexList.add(tempIndex);
            indexOfWaypoint=tempIndex;
            fullDistance+=distance;
            System.out.println("przekazany indeks: "+indexOfWaypoint);
        }

        //distance from last pickUpPoint to final destination
        fullURI=uri+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap()
                +"&end="+journey.getJourneyPickUps().get(indexOfWaypoint).getEndPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap();
        result = restTemplate.getForObject(fullURI, String.class);
        try {
            JsonNode jsonNode = mapper.readTree(result);
            fullDistance+=jsonNode.get("features").get(0).get("properties").get("summary").get("distance").asDouble();
        }catch(Exception e){
            fullDistance+=0;
        }

        journey.setDistance(fullDistance);
        //making order
        for(int i=0; i<indexList.size(); i++){
            journey.getJourneyPickUps().get(indexList.get(i)).setPickUpOrder(i);
        }


        return journeyRepository.save(journey);

    }

    //override

    public Double findDistanceWithMultiplePickUpPoints(Journey journey){
        int userIndex=0;
        String uri="https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248102ff08a64b64d45bd78062f20d3b9b8&start="
                , fullURI, result;
        Double distance=0.0, tempDistance=0.0, fullDistance=0.0;
        Integer indexOfWaypoint=0, tempIndex=0;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Integer> indexList = new ArrayList<Integer>();


        //finding user JourneyPickUp
        for(int i=0; i<journey.getJourneyPickUps().size(); i++){
            if(journey.getJourneyPickUps().get(i).getUser().equals(journey.getUser())){
                userIndex=i;
                indexList.add(i);
                break;
            }
        }

        //trying to find order
        for(int i=0; i<journey.getJourneyPickUps().size(); i++){
            distance=0.0;
            tempDistance=0.0;
            //

            //driver starting point is always the starting point of journey
            if(i==0){
                indexOfWaypoint=userIndex;
                journey.getJourneyPickUps().get(indexOfWaypoint).setPickUpOrder(i);
                //System.out.println("i: "+i+" index: "+indexOfWaypoint);
                continue;
            }


            for(int j=0; j<journey.getJourneyPickUps().size(); j++){
                if(indexList.contains(j)) continue;
                // if this JourneyPickUp is already used, pass to next iteration

                fullURI=uri+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap()
                        +"&end="+journey.getJourneyPickUps().get(j).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(j).getStartPoint().getYMap();
                result = restTemplate.getForObject(fullURI, String.class);

                try{
                    JsonNode jsonNode = mapper.readTree(result);
                    tempDistance=jsonNode.get("features").get(0).get("properties").get("summary").get("distance").asDouble();

                    //assigment for first iteration
                    if(distance.equals(0.0)){
                        distance=tempDistance;
                        tempIndex=j;
                    }

                }catch(Exception e){
                    tempDistance=0.0;
                }

                //same starting point as last one
                if(tempDistance==0.0){
                    tempIndex=j;
                    break;
                }

                //if the distance between points is shorter than last one, change it and assign new index
                if(tempDistance<distance){
                    distance=tempDistance;
                    tempIndex=j;
                }
                //System.out.println("j: "+j+" distance: "+tempDistance+ " index: "+ tempIndex);
            }
            indexList.add(tempIndex);
            indexOfWaypoint=tempIndex;
            fullDistance+=distance;
            //System.out.println("przekazany indeks: "+indexOfWaypoint);
        }

        //distance from last pickUpPoint to final destination
        fullURI=uri+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap()
                +"&end="+journey.getJourneyPickUps().get(indexOfWaypoint).getEndPoint().getXMap()+","+journey.getJourneyPickUps().get(indexOfWaypoint).getStartPoint().getYMap();
        result = restTemplate.getForObject(fullURI, String.class);
        try {
            JsonNode jsonNode = mapper.readTree(result);
            fullDistance+=jsonNode.get("features").get(0).get("properties").get("summary").get("distance").asDouble();
        }catch(Exception e){
            fullDistance+=0;
        }


        return fullDistance;

    }


    List<Journey> findPossibleJourneysWithExistingPickUpPoint(Long startID, Long endID){
        List<Journey> list= journeyRepository.findAll();
        List<Journey> list2 = new ArrayList<>();
        Double distanceBefore;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getJourneyPickUps().get(0).getEndPoint().getId().equals(endID) && list.get(i).getCountOfPassengers()!=0) {
                distanceBefore = list.get(i).getDistance();
                list.get(i).getJourneyPickUps().add(new JourneyPickUp(pickUpPointRepository.getOne(startID), pickUpPointRepository.getOne(endID)));
                System.out.println("id: " + i +  " distance before adding: " + distanceBefore + " distance after : " + findDistanceWithMultiplePickUpPoints(list.get(i)) + " free seats: " + list.get(i).getCountOfPassengers() );
                if (distanceBefore + 1000 > findDistanceWithMultiplePickUpPoints(list.get(i))) {
                    list2.add(list.get(i));
                }
            }
        }
        return list2;
    }

    List<Journey> findPossibleJourneysWithNewPickUpPoint(Double XMap, Double YMap, Long endID){
        List<Journey> list= journeyRepository.findAll();
        List<Journey> list2 = new ArrayList<>();
        Double distanceBefore;
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getJourneyPickUps().get(0).getEndPoint().getId().equals(endID) && list.get(i).getCountOfPassengers()!=0) {
                distanceBefore = list.get(i).getDistance();
                list.get(i).getJourneyPickUps().add(new JourneyPickUp(new PickUpPoint(XMap, YMap), pickUpPointRepository.getOne(endID)));
                System.out.println("id: " + i+1 +  " distance before adding: " + distanceBefore + " distance after : " + findDistanceWithMultiplePickUpPoints(list.get(i)) + " free seats: " + list.get(i).getCountOfPassengers() );
                if (distanceBefore + 1000 > findDistanceWithMultiplePickUpPoints(list.get(i))) {
                    list2.add(list.get(i));
                }
            }
        }
        return list2;
    }

    Journey finishJourney(Long id){
        Journey journey = getJourneyByID(id);
        journey.setStatus(JourneyStatus.FINISHED);
        return journeyRepository.save(journey);
    }

    Journey notFinishedJourney(Long id){
        Journey journey = getJourneyByID(id);
        journey.setStatus(JourneyStatus.NOT_FINISHED);
        return journeyRepository.save(journey);
    }

}
