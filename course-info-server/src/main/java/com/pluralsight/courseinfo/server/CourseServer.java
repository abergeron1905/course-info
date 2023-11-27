package com.pluralsight.courseinfo.server;

import com.pluralsight.courseinfo.repository.CourseRepository;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {

    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }

    private static final Logger LOG = LoggerFactory.getLogger(CourseServer.class);

    public static void main(String... args) {
        CourseInfoProperties properties = loadProperties();
        LOG.info("Starting HTTP server with database {}", properties.databaseFilename());
        CourseRepository courseRepository = CourseRepository.openCourseRepository(properties.databaseFilename());
        ResourceConfig config = new ResourceConfig().register(new CourseResource(courseRepository));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(properties.baseUri()), config);
    }

    private static CourseInfoProperties loadProperties() {
        try (InputStream propertiesStream =
                CourseServer.class.getResourceAsStream("/server.properties")){
            Properties properties = new Properties();
            properties.load(propertiesStream);
            String databaseName =  properties.getProperty("course-info.database");
            String baseUri = properties.getProperty("course-info.base-uri");
            return new CourseInfoProperties(databaseName, baseUri);

        } catch (IOException e) {
            throw new IllegalStateException("Could not load database exception");
        }

    }



}
