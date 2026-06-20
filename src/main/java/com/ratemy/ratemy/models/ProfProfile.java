package com.ratemy.ratemy.models;

public class ProfProfile extends StaffMemberProfile {

    public ProfProfile(StaffRating rating) {
        super(rating);
    }

    @Override
    public String displayTitle() {
        return "Prof. " + rating.getName();
    }

    @Override
    public String displaySummary() {
        return String.format("Prof. %s — rated %.1f/10 overall", rating.getName(), getOverallScore());
    }
}