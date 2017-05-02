package at.fh.ooe.gp2.template.impl.generator;

import at.fh.ooe.gp2.template.api.generator.Generator;
import at.fh.ooe.gp2.template.api.exception.GeneratorException;
import at.fh.ooe.gp2.template.api.shape.Shape;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public abstract class AbstractFreemarkerGenerator<S extends Shape> implements Generator<S> {

    private final Template template;

    private static final Configuration config;

    static {
        config = new Configuration(Configuration.VERSION_2_3_24);
        config.setClassForTemplateLoading(AbstractFreemarkerGenerator.class, "/templates/shapes/svg");
        config.setDefaultEncoding("UTF-8");
        config.setLocale(Locale.US);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public AbstractFreemarkerGenerator(final String templateLocation) throws Exception {
        Objects.requireNonNull(templateLocation, "Template root location must not be null");

        template = config.getTemplate(templateLocation);
    }

    @Override
    public String generate(final S shape) throws GeneratorException {
        Objects.requireNonNull(shape, "Shape for generation must not be null");

        final String result;
        try (final Writer writer = new StringWriter();) {
            template.process(shape, writer);
            writer.flush();
            result = writer.toString();
        } catch (Exception e) {
            throw new GeneratorException("Generation failed", e);
        }

        return result;
    }
}
