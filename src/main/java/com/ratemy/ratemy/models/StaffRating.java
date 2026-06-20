package com.ratemy.ratemy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime; 

@Entity
@Table(name="staffrating")
public class StaffRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotNull(message = "Select a role")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoleType roleType;


    @NotNull(message = "Clarity score is required")
    @Min(value = 1, message = "Clarity must be between 1 and 10")
    @Max(value = 10, message = "Clarity must be between 1 and 10")
    @Column(nullable = false)
    private Integer clarity;

    @NotNull(message = "Niceness score is required")
    @Min(value = 1, message = "Niceness must be between 1 and 10")
    @Max(value = 10, message = "Niceness must be between 1 and 10")
    @Column(nullable = false)
    private Integer niceness;

    @NotNull(message = "Knowledgeable score is required")
    @Min(value = 1, message = "Knowledgeable score must be between 1 and 10")
    @Max(value = 10, message = "Knowledgeable score must be between 1 and 10")
    @Column(nullable = false)
    private Integer knowledgeableScore;

    @Size(max = 300, message = "Comment cannot exceed 300 characters")
    @Column(length = 300)
    private String comment;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public StaffRating() {
    }

    public StaffRating(String name, String email, RoleType roleType, Integer clarity, Integer niceness, Integer knowledgeableScore, String comment) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeableScore = knowledgeableScore;
        this.comment = comment;
    } 

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public double getOverallScore() {
        return Math.round((clarity + niceness + knowledgeableScore) / 3.0);
    } 

    public int getId() {
        return uid;
    }

    public void setId(int id) {
        this.uid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeableScore() {
        return knowledgeableScore;
    }

    public void setKnowledgeableScore(Integer knowledgeableScore) {
        this.knowledgeableScore = knowledgeableScore;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "StaffRating{" + "id=" + uid +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", roleType=" + roleType +
            ", clarity=" + clarity +
            ", niceness=" + niceness +
            ", knowledgeableScore=" + knowledgeableScore +
            '}';
    } 
}
