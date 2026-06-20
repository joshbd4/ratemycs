package com.ratemy.ratemy.models;

public class StaffProfile extends StaffMemberProfile {

    public StaffProfile(StaffRating rating) {
        super(rating);
    }

    @Override
    public String displayTitle() {
        return rating.getName() + " (Staff)";
    }

    @Override
    public String displaySummary() {
        return String.format("%s (Staff) — rated %.1f/10 overall", rating.getName(), getOverallScore());
    }
}