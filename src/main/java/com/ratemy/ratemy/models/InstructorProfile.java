package com.ratemy.ratemy.models;

public class InstructorProfile extends StaffMemberProfile {

    public InstructorProfile(StaffRating rating) {
        super(rating);
    }

    @Override
    public String displayTitle() {
        return "Instructor " + rating.getName();
    }

    @Override
    public String displaySummary() {
        return String.format("%s (Instructor) — rated %.1f/10 overall", rating.getName(), getOverallScore());
    }
}