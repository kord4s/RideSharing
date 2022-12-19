package com.example.ridesharing.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
//@CrossOrigin(origins = "") <--- adres frontu
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    // body aka json + id w add?id=1 -> dalej w serwisie
    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car, @RequestParam Long id){
        return new ResponseEntity<>(carService.addCar(car,id), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") Long id){
        return new ResponseEntity<>(carService.deleteCarById(id), HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Car>> getAllCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Car> editCar(@RequestBody Car car, @PathVariable("id") Long id){
        return new ResponseEntity<>(carService.editCar(id,car), HttpStatus.OK);
    }


}
