package com.ratemy.ratemy.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
import com.ratemy.ratemy.StaffRatingRepository;
import com.ratemy.ratemy.models.RoleType;
import com.ratemy.ratemy.models.StaffRating;
import com.ratemy.ratemy.models.StaffMemberProfileFactory;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/ratings")
public class StaffRatingController {

    private final StaffRatingRepository repository;

    public StaffRatingController(StaffRatingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model) {
        List<StaffRating> ratings = repository.findAll();
        model.addAttribute("ratings", ratings);
        return "index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        StaffRating rating = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("rating", rating);
        model.addAttribute("profile", StaffMemberProfileFactory.create(rating));
        return "detail";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        return "create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("staffRating") StaffRating staffRating,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "create";
        }
        repository.save(staffRating);
        return "redirect:/ratings";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable int id, Model model) {
        StaffRating rating = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("staffRating", rating);
        model.addAttribute("roleTypes", RoleType.values());
        return "edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable int id,
                          @Valid @ModelAttribute("staffRating") StaffRating formInput,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            return "edit";
        }
        StaffRating existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existing.setName(formInput.getName());
        existing.setEmail(formInput.getEmail());
        existing.setRoleType(formInput.getRoleType());
        existing.setClarity(formInput.getClarity());
        existing.setNiceness(formInput.getNiceness());
        existing.setKnowledgeableScore(formInput.getKnowledgeableScore());
        existing.setComment(formInput.getComment());
        repository.save(existing);
        return "redirect:/ratings/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        repository.deleteById(id);
        return "redirect:/ratings";
    }
}