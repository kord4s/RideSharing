package com.example.ridesharing.Journey;

import com.example.ridesharing.Journey.AdditionalData.AdditionalData;
import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
import com.example.ridesharing.PendingRequest.PendingRequest;
import com.example.ridesharing.User.RidesharingUser;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;


    private Long carID;
    private Double distance;
    private Integer countOfPassengers;
    private JourneyStatus status;

    //przystanki + userID goscia co dodaje przystanki

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<JourneyPickUp> journeyPickUps;

    @OneToOne(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference
    private AdditionalData additionalData;

    @OneToMany(mappedBy = "journey", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PendingRequest> pendingRequests;


}
