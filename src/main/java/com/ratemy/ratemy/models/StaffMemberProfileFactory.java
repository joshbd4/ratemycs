package com.ratemy.ratemy.models;

public final class StaffMemberProfileFactory {

    private StaffMemberProfileFactory() {
    }

    public static StaffMemberProfile create(StaffRating rating) {
        return switch (rating.getRoleType()) {
            case TA -> new TaProfile(rating);
            case PROF -> new ProfProfile(rating);
            case INSTRUCTOR -> new InstructorProfile(rating);
            case STAFF -> new StaffProfile(rating);
        };
    }
}