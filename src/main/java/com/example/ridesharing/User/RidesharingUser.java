package com.example.ridesharing.User;

import com.example.ridesharing.Car.Car;
import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import com.example.ridesharing.PendingRequest.PendingRequest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class RidesharingUser {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String email;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value="journeyPickups-user")
    private List<JourneyPickUp> journeyPickUps;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value="journeys-user")
    private List<Journey> journeys;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value="cars-user")
    private List<Car> cars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value="pendingRequests-user")
    private List<PendingRequest> pendingRequests;


    @Column(name="achievements")
    @ElementCollection(targetClass = Long.class)
    private List<Long> achievements;

}
