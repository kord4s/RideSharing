package com.example.ridesharing.Journey.AdditionalData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalDataRepository extends JpaRepository<AdditionalData, Long> {
}
