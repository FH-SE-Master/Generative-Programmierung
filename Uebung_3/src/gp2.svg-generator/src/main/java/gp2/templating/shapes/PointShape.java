package gp2.templating.shapes;

import gp2.templating.Diagram;
import gp2.templating.interfaces.Generator;
import lombok.Getter;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public class PointShape extends AbstractShape {

    @Getter
    private final double x;
    @Getter
    private final double y;
    @Getter
    private final double size;

    public PointShape(Diagram diagram,
                      Generator generator,
                      double x,
                      double y,
                      double size) {

        super(diagram, generator);
        this.x = x;
        this.y = y;
        this.size = size;
    }
}
