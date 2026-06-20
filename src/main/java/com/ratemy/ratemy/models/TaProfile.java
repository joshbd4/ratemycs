package com.ratemy.ratemy.models;

public class TaProfile extends StaffMemberProfile {

    public TaProfile(StaffRating rating) {
        super(rating);
    }

    @Override
    public String displayTitle() {
        return "TA " + rating.getName();
    }

    @Override
    public String displaySummary() {
        return String.format("%s (TA) — rated %.1f/10 overall", rating.getName(), getOverallScore());
    }
}