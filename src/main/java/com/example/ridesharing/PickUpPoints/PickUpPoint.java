package com.example.ridesharing.PickUpPoints;

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
public class PickUpPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double XMap;
    private Double YMap;

    private String name;

    private PickUpPointStatus Status;

    @OneToMany(mappedBy = "startPoint", cascade = CascadeType.ALL)
    @JsonManagedReference(value="startPoints-journeyPickUps")
    private List<JourneyPickUp> startPoints;

    @OneToMany(mappedBy = "endPoint", cascade = CascadeType.ALL)
    @JsonManagedReference(value="endPoints-journeyPickUps")
    private List<JourneyPickUp> endPoint;

    @OneToMany(mappedBy = "startPoint", cascade = CascadeType.ALL)
    @JsonManagedReference(value="startPoints-pendingRequest")
    private List<PendingRequest> pendingRequestsStartPoints;


    public PickUpPoint(Double xmap, Double ymap) {
        XMap = xmap;
        YMap = ymap;
        Status = PickUpPointStatus.START;
    }
}
