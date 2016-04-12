package common;

import common.initializer.AppConfig;
import common.initializer.MvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setServletContext(servletContext);
        applicationContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(applicationContext));

        applicationContext.getServletContext().setAttribute("ctx", applicationContext.getServletContext().getContextPath());

        AnnotationConfigWebApplicationContext springMVCApplicationContext = new AnnotationConfigWebApplicationContext();
        springMVCApplicationContext.setServletContext(servletContext);
        springMVCApplicationContext.setParent(applicationContext);
        springMVCApplicationContext.register(MvcConfig.class);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(springMVCApplicationContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("mvc", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }
}
