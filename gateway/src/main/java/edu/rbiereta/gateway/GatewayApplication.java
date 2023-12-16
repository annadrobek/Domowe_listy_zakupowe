package edu.rbiereta.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("msfront", r -> r.path("/msfront/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:8080/msfront"))
                .route("msclient", r -> r.path("/msclient/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:8080/msclient"))
                .route("mscart", r -> r.path("/mscart/**")
                .filters(f -> f.stripPrefix(1))
                .uri("http://localhost:8080/mscart"))
                .build();
    }

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
