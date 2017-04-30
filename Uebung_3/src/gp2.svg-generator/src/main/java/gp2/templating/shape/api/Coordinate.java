package gp2.templating.shape.api;


import lombok.Getter;

import java.util.Objects;

/**
 * An instance of this class represents a coordinate of an shape.
 *
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class Coordinate {

    @Getter
    private final double x;
    @Getter
    private final double y;

    public Coordinate(double x,
                      double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
