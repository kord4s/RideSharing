package com.example.ridesharing.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RidesharingUserService {
    private final RidesharingUserRepository ridesharingUserRepository;

    @Autowired
    public RidesharingUserService(RidesharingUserRepository ridesharingUserRepository) {
        this.ridesharingUserRepository = ridesharingUserRepository;
    }

    public RidesharingUser addUser(RidesharingUser user){
        return ridesharingUserRepository.save(user);
    }

    public List<RidesharingUser> getAllUsers() {return ridesharingUserRepository.findAll();}

    public RidesharingUser getUserById(Long id) {return ridesharingUserRepository.findById(id).orElseThrow();}
    public Boolean deleteUserById(Long id) {
        try{
            ridesharingUserRepository.deleteById(id);
            return true;
        }catch(Exception e){return false;}
    }
}
