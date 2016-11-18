package com.tormentaLabs.riobus.common.models;

import java.io.Serializable;

public class Line {

    private String line;
    private String description;

    public Line() {}

    public Line(String line, String description) {
        this.line = line;
        this.description = description;
    }

    public String getLine() {
        return line;
    }

    public String getDescription() {
        return description;
    }
}
