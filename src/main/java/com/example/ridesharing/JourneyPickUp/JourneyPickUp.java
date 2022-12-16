package com.example.ridesharing.JourneyPickUp;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.PickUpPoints.PickUpPoint;
import com.example.ridesharing.User.RidesharingUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity

public class JourneyPickUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "startPoint_ID", referencedColumnName = "id")
    private PickUpPoint startPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "endPoint_ID", referencedColumnName = "id")
    private PickUpPoint endPoint;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private Journey journey;

    private Date date;
    private String time;

}
