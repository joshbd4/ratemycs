package com.ratemy.ratemy;

import com.ratemy.ratemy.models.RoleType;
import com.ratemy.ratemy.models.StaffRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class StaffRatingRepositoryTests {

    @Autowired
    private StaffRatingRepository repository;

    private StaffRating newRating(String name, String email) {
        return new StaffRating(
                name,
                email,
                RoleType.PROF,
                8,
                9,
                7,
                "Excellent professor."
        );
    }

    @Test
    void savingAndRetrievingEntryWorks() {
        StaffRating saved = repository.save(newRating("Ada Lovelace", "ada@example.com"));

        Optional<StaffRating> found = repository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Ada Lovelace");
        assertThat(found.get().getEmail()).isEqualTo("ada@example.com");
        assertThat(found.get().getRoleType()).isEqualTo(RoleType.PROF);
        assertThat(found.get().getCreatedAt()).isNotNull();
        assertThat(found.get().getUpdatedAt()).isNotNull();
    }

    @Test
    void findByEmailReturnsMatchingEntry() {
        repository.save(newRating("Grace Hopper", "grace@example.com"));

        Optional<StaffRating> found = repository.findByEmail("grace@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Grace Hopper");
    }

    @Test
    void findByEmailReturnsEmptyWhenNoMatch() {
        Optional<StaffRating> found = repository.findByEmail("nobody@example.com");

        assertThat(found).isEmpty();
    }

    @Test
    void deleteRemovesTheEntry() {
        StaffRating saved = repository.save(newRating("Alan Turing", "alan@example.com"));
        int id = saved.getId();

        repository.deleteById(id);

        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    void updatingAnEntryPersistsChanges() {
        StaffRating saved = repository.save(newRating("Margaret Hamilton", "margaret@example.com"));

        saved.setNiceness(10);
        repository.save(saved);

        Optional<StaffRating> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getNiceness()).isEqualTo(10);
    }
} 
