package com.example.ridesharing.User;

import com.example.ridesharing.Journey.Journey;
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

    ///do testowania tylko
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

    public Boolean register(RidesharingUser user){
        List<RidesharingUser> users = getAllUsers();
        for (RidesharingUser ridesharingUser : users) {
            if (ridesharingUser.getLogin().equals(user.getLogin()) || ridesharingUser.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        ridesharingUserRepository.save(user);
        return true;
    }

    public Boolean editUser(RidesharingUser dataUser, Long id){
        RidesharingUser user = ridesharingUserRepository.getOne(id);
        List<RidesharingUser> users = getAllUsers();
        for (RidesharingUser ridesharingUser : users) {
            if (ridesharingUser.getEmail().equals(dataUser.getEmail()) && !ridesharingUser.getId().equals(id)) {
                return false;
            }
        }
        user.setEmail(dataUser.getEmail());
        user.setName(dataUser.getName());
        user.setPassword(dataUser.getPassword());
        user.setSurname(dataUser.getSurname());
        ridesharingUserRepository.save(user);
        return true;
    }

    public Long login(String login, String password){
        RidesharingUser user = ridesharingUserRepository.findByLogin(login);
        if(user.getPassword().equals(password)){
            return user.getId();
        }
        return 0L;
    }


    public List<Journey> findPossibleJourneys(double Xmap, double Ymap){
        String uri = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248102ff08a64b64d45bd78062f20d3b9b8&start="+Xmap+","+Ymap+"end=";
        //zawiera tylko lokalizacje pasazera na ten moment
        return null;
    }



}
