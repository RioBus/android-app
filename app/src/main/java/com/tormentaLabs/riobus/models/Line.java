package com.tormentaLabs.riobus.models;

public class Line {

    private String line;
    private String description;

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
