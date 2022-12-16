package com.example.ridesharing.PendingRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingRequestRepository extends JpaRepository<PendingRequest,Long> {
}
