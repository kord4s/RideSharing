package com.example.ridesharing.Journey;

import com.example.ridesharing.Car.Car;
import com.example.ridesharing.Journey.AdditionalData.AdditionalData;
import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import com.example.ridesharing.PendingRequest.PendingRequest;
import com.example.ridesharing.User.RidesharingUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //kierowca, twórca przejazdu
    @ManyToOne()
    @JsonBackReference(value="journeys-user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;


    @ManyToOne()
    @JsonBackReference(value="cars-journey")
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    private Double distance;
    private Integer countOfPassengers;
    private JourneyStatus status;

    //przystanki + userID goscia co dodaje przystanki

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference(value="journeyPickUps-journey")
    private List<JourneyPickUp> journeyPickUps;

    @OneToOne(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference(value="additionalData-journey")
    private AdditionalData additionalData;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference(value="pendingRequests-journey")
    private List<PendingRequest> pendingRequests;


}
