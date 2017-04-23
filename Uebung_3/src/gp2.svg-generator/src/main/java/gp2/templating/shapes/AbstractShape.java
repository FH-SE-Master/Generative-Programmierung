package gp2.templating.shapes;

import freemarker.template.TemplateException;
import gp2.templating.Diagram;
import gp2.templating.interfaces.Generator;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public abstract class AbstractShape {

    @Getter
    @Setter
    private Diagram diagram;
    @Getter
    @Setter
    private Generator generator;

    public AbstractShape(Diagram diagram,
                         Generator generator) {
        this.diagram = diagram;
        this.generator = generator;
    }

    public String execute() throws TemplateException, IOException {
        return generator.generate(this);
    }
}
