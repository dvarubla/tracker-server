package ru.aborisov.testtask.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;

/*Главный класс инициализации*/
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
@EnableWebMvc
@ComponentScan("ru.aborisov.testtask")
public class AppInitializer extends AbstractSecurityWebApplicationInitializer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("file:testtaskstatic/");
    }

    @Bean
    public ObjectMapper myObjectMapper() {
        return new ObjectMapper().registerModule(new Jdk8Module());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        /*получение аргументов командной строки*/
        HashMap<String, Object> cmdlineProps = new HashMap<>();
        String[] dbParams = new String[]{
                "db.host", "db.port", "db.name", "db.username", "db.password"
        };
        for (String paramName : dbParams) {
            cmdlineProps.put(paramName, servletContext.getInitParameter(paramName));
        }
        cmdlineProps.put("app.isDebug", Boolean.valueOf(servletContext.getInitParameter("app.isDebug")));

        MapPropertySource cmdlinePropSrc = new MapPropertySource("cmdlineProps", cmdlineProps);

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.getEnvironment().getPropertySources().addFirst(cmdlinePropSrc);
        rootContext.register(AppInitializer.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

        ServletRegistration.Dynamic dispatcher = servletContext
                .addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
