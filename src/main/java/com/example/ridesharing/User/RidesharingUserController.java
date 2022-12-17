package com.example.ridesharing.User;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "") <--- adres frontu
public class RidesharingUserController {
    private final RidesharingUserService ridesharingUserService;

    @Autowired
    public RidesharingUserController(RidesharingUserService ridesharingUserService) {
        this.ridesharingUserService = ridesharingUserService;
    }

    @PostMapping(value = "/add")
    ResponseEntity<RidesharingUser> addUser(@RequestBody RidesharingUser user){
        return new ResponseEntity<>(ridesharingUserService.addUser(user), HttpStatus.OK);
    }

    @GetMapping(value = "/get/all")
    ResponseEntity<List<RidesharingUser>> getAllUsers(){
        return new ResponseEntity<>(ridesharingUserService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    ResponseEntity<RidesharingUser> getUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(ridesharingUserService.getUserById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    ResponseEntity<Boolean> deleteUserById(@PathVariable("id") Long id){
        return new ResponseEntity<>(ridesharingUserService.deleteUserById(id), HttpStatus.OK);
    }

}
