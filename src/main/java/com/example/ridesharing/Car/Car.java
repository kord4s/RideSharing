package com.example.ridesharing.Car;

import com.example.ridesharing.Journey.Journey;
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
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String producer;
    private String model;
    private typeOfEngine engineType;
    private Double engineCapacity;

    @ManyToOne()
    @JsonBackReference(value="cars-user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference(value="cars-journey")
    private List<Journey> journeys;

}
