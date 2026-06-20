package com.ratemy.ratemy.models;

public abstract class StaffMemberProfile {

    protected final StaffRating rating;

    protected StaffMemberProfile(StaffRating rating) {
        this.rating = rating;
    }

    public abstract String displayTitle();

    public abstract String displaySummary();

    public String getName() {
        return rating.getName();
    }

    public double getOverallScore() {
        return rating.getOverallScore();
    }

    public StaffRating getRating() {
        return rating;
    }
}