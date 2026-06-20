package com.ratemy.ratemy;
import com.ratemy.ratemy.models.StaffRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRatingRepository extends JpaRepository<StaffRating, Integer> {
    Optional<StaffRating> findByEmail(String email);
}
