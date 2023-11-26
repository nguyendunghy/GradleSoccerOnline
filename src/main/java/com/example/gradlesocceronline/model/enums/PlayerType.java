package com.example.gradlesocceronline.model.enums;

public enum PlayerType {
    GOAL_KEEPER("GOAL_KEEPER"),
    DEFENDER("DEFENDER"),
    MIDFIELDER("MIDFIELDER"),
    ATTACKER("ATTACKER");

    private String value;

    PlayerType(String value) {
        this.value = value;
    }


}
