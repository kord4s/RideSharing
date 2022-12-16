package com.example.ridesharing.JourneyPickUp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyPickUpRepository extends JpaRepository<JourneyPickUp, Long> {
}
