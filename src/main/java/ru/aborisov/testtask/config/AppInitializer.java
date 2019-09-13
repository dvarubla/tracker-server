package ru.aborisov.testtask.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;

/*Главный класс инициализации*/
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@EnableWebMvc
@ComponentScan("ru.aborisov.testtask")
public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        /*получение аргументов командной строки*/
        HashMap<String, Object> dbProps = new HashMap<>();
        String[] dbParams = new String[]{
                "db.host", "db.port", "db.name", "db.username", "db.password"
        };
        for (String paramName : dbParams) {
            dbProps.put(paramName, servletContext.getInitParameter(paramName));
        }
        MapPropertySource dbPropSrc = new MapPropertySource("dbProps", dbProps);

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.getEnvironment().getPropertySources().addFirst(dbPropSrc);
        rootContext.register(AppInitializer.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

        ServletRegistration.Dynamic dispatcher = servletContext
                .addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
