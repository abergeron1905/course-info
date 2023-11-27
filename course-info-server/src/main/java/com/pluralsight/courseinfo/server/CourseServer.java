package com.pluralsight.courseinfo.server;

import java.net.URI;
import java.util.logging.LogManager;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import com.pluralsight.courseinfo.repository.CourseRepository;

public class CourseServer {

    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }

    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI = "http://localhost:8080/";

    public static void main(String... args) {
        LOG.info("Starting HTTP server");
        CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
        ResourceConfig config = new ResourceConfig().register(new CourseResource(courseRepository));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config); 
    }

    
   
    
}
