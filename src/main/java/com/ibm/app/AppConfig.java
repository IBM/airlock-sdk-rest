//package com.ibm.app;
//
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.ServerProperties;
//import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
//
//import javax.inject.Inject;
//import javax.inject.Provider;
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//
//@ApplicationPath("/airlock")
//public class AppConfig extends ResourceConfig {
//    public AppConfig() {
//        packages("com.ibm.app.services");
//        property(ServerProperties.MONITORING_STATISTICS_ENABLED, true);
//    }
//}