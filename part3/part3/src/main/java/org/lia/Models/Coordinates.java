package org.lia.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Coordinates {

    private double x;
    private double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double computeDistanceTo(Coordinates other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}
