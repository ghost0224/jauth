package com.hp.security.jauth.core.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Class GeneratorCore.java
 * 
 * @author Ethan Huang (yiqingh@hp.com)
 * @since Aug 3, 2012
 */
@Service
public class FreemarkerUtil {

    private Configuration configuration;
    private Logger log = Logger.getLogger(this.getClass().getName());

    public FreemarkerUtil() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // find system template
        log.info("finding jauth core template...");
        Resource[] coreResources = resolver.getResources("jauth/ftl/core/*/*.vm");
        StringTemplateLoader stringTempLoader = new StringTemplateLoader();
        for(Resource r : coreResources) {
            InputStream ins = r.getInputStream();
            String content = StringUtil.convertStreamToString(ins);
            stringTempLoader.addTemplate(r.getFilename(), content);
            log.info(r.getFilename());
        }
        log.info(coreResources.length + " jauth core templates in all.");
        try {
	        log.info("finding jauth admin template...");
	        Resource[] adminResources = resolver.getResources("jauth/ftl/admin/*/*.vm");
	        for(Resource r : adminResources) {
	            InputStream ins = r.getInputStream();
	            String content = StringUtil.convertStreamToString(ins);
	            stringTempLoader.addTemplate(r.getFilename(), content);
	            log.info(r.getFilename());
	        }
	        log.info(adminResources.length + " jauth admin templates in all.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find any jauth admin templates.");
        }
        try {
	        log.info("finding jauth soap template...");
	        Resource[] soapResources = resolver.getResources("jauth/ftl/soap/*/*.vm");
	        for(Resource r : soapResources) {
	            InputStream ins = r.getInputStream();
	            String content = StringUtil.convertStreamToString(ins);
	            stringTempLoader.addTemplate(r.getFilename(), content);
	            log.info(r.getFilename());
	        }
	        log.info(soapResources.length + " jauth soap templates in all.");
        } catch(FileNotFoundException e) {
        	log.info("didn't find any jauth soap templates.");
        }
        // find custom template
        try {
            log.info("finding jauth custom template...");
            Resource[] customResources = resolver.getResources("jauth/ftl/custom/*/*.vm");
            for (Resource r : customResources) {
                InputStream ins = r.getInputStream();
                String content = StringUtil.convertStreamToString(ins);
                stringTempLoader.addTemplate(r.getFilename(), content);
                log.info(r.getFilename());
            }
            log.info(customResources.length + " jauth custom templates in all.");
        } catch (FileNotFoundException e) {
            log.info("didn't find any jauth custom templates.");
        }
        configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(stringTempLoader);
        configuration.setNumberFormat("#");
    }

    public String buildView(Map<String, Object> root, String templatePath) throws IOException, TemplateException {
        Template tpl = configuration.getTemplate(templatePath + ".vm");
        Writer out = new StringWriter();
        tpl.process(root, out);
        String content = out.toString();
        return content;
    }
    
    public String baseReturn(HttpServletRequest request, HttpServletResponse response, Map<String, Object> root, String template) throws IOException, TemplateException {
    	String requestURL = request.getRequestURL().toString();
    	if(requestURL.indexOf("\\.") <= 0) {
        	String view = buildView(root, template);
            response.setContentType("text/html");
            ServletOutputStream out = response.getOutputStream();
            out.write(view.getBytes());
            out.flush();
            out.close();
        }
        return null;
	}
    
}
