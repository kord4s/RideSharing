package com.example.ridesharing.JourneyPickUp;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.PickUpPoints.PickUpPoint;
import com.example.ridesharing.User.RidesharingUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne()
    @JsonBackReference(value="journeyPickups-user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;

    @ManyToOne()
    @JsonBackReference(value="startPoints-journeyPickUps")
    @JoinColumn(name = "startPoint_ID", referencedColumnName = "id")
    private PickUpPoint startPoint;

    @ManyToOne()
    @JsonBackReference(value="endPoints-journeyPickUps")
    @JoinColumn(name = "endPoint_ID", referencedColumnName = "id")
    private PickUpPoint endPoint;

    @ManyToOne()
    @JsonBackReference(value="journeyPickUps-journey")
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private Journey journey;

    private Date date;
    private String time;

}
