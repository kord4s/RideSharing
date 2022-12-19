package com.example.ridesharing.PickUpPoints;

import com.example.ridesharing.JourneyPickUp.JourneyPickUp;
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

    @OneToMany(mappedBy = "startPoint", cascade = CascadeType.ALL)
    @JsonManagedReference(value="startPoints-journeyPickUps")
    private List<JourneyPickUp> startPoints;

    @OneToMany(mappedBy = "endPoint", cascade = CascadeType.ALL)
    @JsonManagedReference(value="endPoints-journeyPickUps")
    private List<JourneyPickUp> endPoint;

    public PickUpPoint(Double xmap, Double ymap) {
        XMap = xmap;
        YMap = ymap;
    }
}
