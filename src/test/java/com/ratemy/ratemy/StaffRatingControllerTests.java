package com.ratemy.ratemy;

import com.ratemy.ratemy.controllers.StaffRatingController;
import com.ratemy.ratemy.models.RoleType;
import com.ratemy.ratemy.models.StaffRating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffRatingController.class)
class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffRatingRepository repository;

    private StaffRating sampleRating() {
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
    void indexReturnsOkAndListsRatings() throws Exception {
        when(repository.findAll()).thenReturn(List.of(sampleRating()));

        mockMvc.perform(get("/ratings"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    void createWithValidDataRedirects() throws Exception {
        when(repository.save(any(StaffRating.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/ratings")
                        .param("name", "Jordan Lee")
                        .param("email", "jordan@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "9")
                        .param("knowledgeableScore", "7")
                        .param("comment", "Great TA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings"));

        verify(repository, times(1)).save(any(StaffRating.class));
    }

    @Test
    void createWithInvalidDataShowsFormAgain() throws Exception {
        mockMvc.perform(post("/ratings")
                        .param("name", "") // invalid
                        .param("email", "jordan@example.com")
                        .param("roleType", "TA")
                        .param("clarity", "8")
                        .param("niceness", "9")
                        .param("knowledgeableScore", "7")
                        .param("comment", "Great TA"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("roleTypes"))
                .andExpect(model().attributeHasFieldErrors("staffRating", "name"));

        verify(repository, never()).save(any());
    }
}
