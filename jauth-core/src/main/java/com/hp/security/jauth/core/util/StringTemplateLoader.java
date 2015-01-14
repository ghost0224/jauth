package com.hp.security.jauth.core.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import freemarker.cache.TemplateLoader;

/**
 * @author huangyiq
 */
public class StringTemplateLoader implements TemplateLoader {

    private String key;
    private Map<String, String> templates = new HashMap<String, String>();

    public StringTemplateLoader(String key, String defaultTemplate) {
        if (defaultTemplate != null && !defaultTemplate.equals("")) {
            templates.put(key, defaultTemplate);
        }
    }

    public StringTemplateLoader() {
    }

    public void addTemplate(String templateName, String template) {
        if (templateName == null || template == null || templateName.equals("") || template.equals("")) {
            return;
        }
        // if (!templates.containsKey(templateName)) {
        templates.put(templateName, template);
        // }
    }

    public void closeTemplateSource(Object templateSource) throws IOException {
    }

    public Object findTemplateSource(String templateName) throws IOException {
        if (templateName == null || templateName.equals("")) {
            return templates.get(key);
        } else {
            return templates.get(templateName);
        }
    }

    public long getLastModified(Object templateSource) {
        return 0;
    }

    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader((String) templateSource);
    }

}
