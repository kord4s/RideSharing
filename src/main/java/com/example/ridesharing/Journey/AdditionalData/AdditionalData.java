package com.example.ridesharing.Journey.AdditionalData;

import com.example.ridesharing.Journey.Journey;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class AdditionalData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String CO2;
    private String SOx;
    private String CO;
    private String NOx;
    private Double usedFuel;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value="additionalData-journey")
    @JoinColumn(name = "journey_id", referencedColumnName = "id")
    private Journey journey;


}
