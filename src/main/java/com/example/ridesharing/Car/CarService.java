package com.example.ridesharing.Car;

import com.example.ridesharing.User.RidesharingUser;
import com.example.ridesharing.User.RidesharingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final RidesharingUserRepository ridesharingUserRepository;

    @Autowired
    public CarService(CarRepository carRepository, RidesharingUserRepository ridesharingUserRepository) {
        this.carRepository = carRepository;
        this.ridesharingUserRepository = ridesharingUserRepository;
    }

    public Car addCar(Car car, Long id){
        RidesharingUser user = ridesharingUserRepository.getOne(id);
        car.setUser(user);
        return carRepository.save(car);}

    public Boolean deleteCarById(Long id){
        try{
            carRepository.deleteById(id);
            return true;
        }catch(Exception e){return false;}}

    public Car editCar(Long id, Car carData ){
        Car car = carRepository.getOne(id);
        car.setUser(carData.getUser());
        car.setModel(carData.getModel());
        car.setProducer(carData.getProducer());
        car.setEngineCapacity(carData.getEngineCapacity());
        car.setEngineType(carData.getEngineType());
        return carRepository.save(car);
    }

    public List<Car> getAllCars(){ return carRepository.findAllByOrderByIdAsc();}
}
