package com.ratemy.ratemy;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import com.ratemy.ratemy.models.StaffRating;
import com.ratemy.ratemy.models.RoleType; 


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class StaffRatingValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private StaffRating validRating() {
        return new StaffRating(
                "Jordan Lee",
                "jordan@example.com",
                RoleType.TA,
                8,
                9,
                7,
                "Great TA, very helpful."
        );
    }

    @Test
    void validRatingHasNoViolations() {
        StaffRating rating = validRating();
        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertThat(violations).isEmpty();
    }

    @Test
    void invalidEmailIsRejected() {
        StaffRating rating = validRating();
        rating.setEmail("not-an-email");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    void blankNameIsRejected() {
        StaffRating rating = validRating();
        rating.setName("");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void missingNameIsRejected() {
        StaffRating rating = validRating();
        rating.setName(null);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }

    @Test
    void clarityBelowRangeIsRejected() {
        StaffRating rating = validRating();
        rating.setClarity(0);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));
    }

    @Test
    void clarityAboveRangeIsRejected() {
        StaffRating rating = validRating();
        rating.setClarity(11);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("clarity"));
    }

    @Test
    void missingRoleTypeIsRejected() {
        StaffRating rating = validRating();
        rating.setRoleType(null);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("roleType"));
    }

    @Test
    void commentOverLengthLimitIsRejected() {
        StaffRating rating = validRating();
        rating.setComment("x".repeat(501)); // limit is 500 chars

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);

        assertThat(violations)
                .anyMatch(v -> v.getPropertyPath().toString().equals("comment"));
    }
}
