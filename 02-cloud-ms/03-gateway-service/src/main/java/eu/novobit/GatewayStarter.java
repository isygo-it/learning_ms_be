package eu.novobit;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Gateway starter.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayStarter {

    /**
     * The Locator.
     */
    @Autowired
    RouteDefinitionLocator locator;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayStarter.class, args);
    }

    /**
     * Apis list.
     *
     * @return the list
     */
    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        assert definitions != null;
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("-service", "");
            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
        });
        return groups;
    }
}
