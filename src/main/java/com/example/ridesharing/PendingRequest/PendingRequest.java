package com.example.ridesharing.PendingRequest;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.PickUpPoints.PickUpPoint;
import com.example.ridesharing.User.RidesharingUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class PendingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RequestStatus status;


    @ManyToOne()
    @JsonBackReference(value="startPoints-pendingRequest")
    @JoinColumn(name = "startPoint_ID", referencedColumnName = "id")
    private PickUpPoint startPoint;

    @ManyToOne()
    @JsonBackReference(value="pendingRequests-user")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;

    @ManyToOne()
    @JsonBackReference(value="pendingRequests-journey")
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private Journey journey;
}
