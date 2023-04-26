package com.okula.cardjitsu.enums;

import lombok.Getter;

public enum BeltEnums {

    BLACK(200, "#000000"),
    BROWN(150, "#ffdac2"),
    RED(100, "#b70000"),
    PURPLE(75, "#8a14ff"),
    BLUE(50, "#055099"),
    GREEN(30, "#227907"),
    ORANGE(15, "#ce7e00"),
    YELLOW(5, "#c2a404"),
    WHITE(0, "#ffffff");

    @Getter
    private final int wins;
    @Getter
    private final String color;

    BeltEnums(int wins, String color) {
        this.wins = wins;
        this.color = color;

    }

}
