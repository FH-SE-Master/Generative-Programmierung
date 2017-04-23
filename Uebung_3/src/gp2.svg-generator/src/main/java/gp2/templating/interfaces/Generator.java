package gp2.templating.interfaces;

import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author Thomas Herzog <t.herzog@curecomp.com>
 * @since 04/21/17
 */
public interface Generator {

    String generate(Object args) throws TemplateException, IOException;
}
