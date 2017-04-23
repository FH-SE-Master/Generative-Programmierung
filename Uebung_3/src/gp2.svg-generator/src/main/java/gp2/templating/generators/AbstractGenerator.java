package gp2.templating.generators;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import gp2.templating.Main;
import gp2.templating.interfaces.Generator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public abstract class AbstractGenerator implements Generator {

    private final Template template;
    private static final Configuration config;

    static {
        config = new Configuration(Configuration.VERSION_2_3_24);
        config.setClassForTemplateLoading(Main.class, "templates");
        config.setDefaultEncoding("UTF-8");
        config.setLocale(Locale.US);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    protected AbstractGenerator(String template) {
        try {
            this.template = config.getTemplate(template);
        } catch (IOException e) {
            throw new IllegalArgumentException("Template load failed", e);
        }
    }

    public String generate(Object args) throws TemplateException, IOException {
        try (final Writer writer = new StringWriter();) {
            template.process(args, writer);
            writer.flush();
            return writer.toString();
        }
    }
}
