package ru.stuff.apigateway.route;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "account-service",
                        p -> p
                                .path("/profile/**")
                                .uri("lb://account-service"))
                .route(
                        "auth-service",
                        p -> p
                                .path("/auth/**")
                                .uri("lb://auth-service"))
                .build();
    }
}
