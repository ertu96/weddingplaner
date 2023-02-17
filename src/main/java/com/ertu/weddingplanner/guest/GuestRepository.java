package com.ertu.weddingplanner.guest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    @Query("SELECT s FROM Guest s WHERE s.email = ?1")
    Optional<Guest> findGuestByEmail(String email);

}
