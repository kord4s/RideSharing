package com.example.ridesharing.PendingRequest;

import com.example.ridesharing.Journey.Journey;
import com.example.ridesharing.User.RidesharingUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private RidesharingUser user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private Journey journey;
}
