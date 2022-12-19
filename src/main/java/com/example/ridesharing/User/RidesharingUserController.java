package com.example.ridesharing.User;

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

    @PostMapping(value = "/register")
    ResponseEntity<Boolean> registerUser(@RequestBody RidesharingUser user){
        return new ResponseEntity<>(ridesharingUserService.register(user), HttpStatus.OK);
    }

    @PutMapping(value = "/edit/{id}")
    ResponseEntity<Boolean> editUser(@RequestBody RidesharingUser user, @PathVariable("id") Long id){
        return new ResponseEntity<>(ridesharingUserService.editUser(user, id), HttpStatus.OK);
    }

    @GetMapping(value = "/login")
    ResponseEntity<Long> login(@RequestParam String login, @RequestParam String password ){
        return new ResponseEntity<>(ridesharingUserService.login(login,password), HttpStatus.OK);
    }

}
