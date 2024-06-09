package com.company.library.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MembershipType {

    STANDARD(1),
    PREMIUM(2),
    STUDENT(3),
    SENIOR(4),
    GUEST(5);

    private final int value;

    MembershipType(int value) {
        this.value = value;
    }


    public static MembershipType fromString(String name) {
        for (MembershipType type : MembershipType.values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }



}
