package com.company.myapp.model;

public class Result {
    private String str;
    private Double distance;

    public Result(String str, Double distance) {
        this.str = str;
        this.distance = distance;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
