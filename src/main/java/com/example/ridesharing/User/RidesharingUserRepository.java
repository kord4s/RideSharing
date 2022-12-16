package com.example.ridesharing.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RidesharingUserRepository extends JpaRepository<RidesharingUser, Long> {
}
