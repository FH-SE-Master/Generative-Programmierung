package gp2.templating.generator.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gp2.templating.generator.api.Generator;
import gp2.templating.generator.exception.GeneratorException;
import gp2.templating.shape.api.Shape;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/23/17
 */
public class FreemarkerGenerator implements Generator {

    private final Configuration config;
    private Map<Class<? extends Shape>, Template> shapeToTemplateMap = new HashMap<>();

    public FreemarkerGenerator(Class<?> loaderClass,
                               String templateLocation) {
        Objects.requireNonNull(loaderClass, "Class for template loading must not be null");
        Objects.requireNonNull(templateLocation, "Template root location must not be null");

        config = new Configuration(Configuration.VERSION_2_3_24);
        config.setClassForTemplateLoading(loaderClass, templateLocation);
        config.setDefaultEncoding("UTF-8");
        config.setLocale(Locale.US);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void registerShapeTemplate(final Class<? extends Shape> shapeClazz,
                                      final String templateName) throws TemplateException, IOException {
        Objects.requireNonNull(shapeClazz, "Shape class must not be null");
        Objects.requireNonNull(templateName, "Template name must not be null");

        final Template template = config.getTemplate(templateName);
        shapeToTemplateMap.put(shapeClazz, template);
    }

    public void clearTemplates() {
        shapeToTemplateMap = new HashMap<>();
    }

    @Override
    public String generate(final Shape shape) throws GeneratorException {
        Objects.requireNonNull(shape, "Shape for generation must not be null");

        Template template = shapeToTemplateMap.get(shape.getClass());
        if (template == null) {
            throw new IllegalStateException("No template registered for shape: " + shape.getClass().getName());
        }

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
