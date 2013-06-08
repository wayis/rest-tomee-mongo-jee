package org.wayis.restmongo.application;

import com.mongodb.jee.jaxrs.JaxrsMongoApplication;
import org.wayis.restmongo.service.BlogService;

import javax.ws.rs.ApplicationPath;
import java.util.Set;

@ApplicationPath("/api")
public class ApplicationConfig extends JaxrsMongoApplication {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = super.getClasses();
        classes.add(BlogService.class);
        return classes;
    }
}
