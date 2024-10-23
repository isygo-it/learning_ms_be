package eu.novobit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The type Discovery starter.
 */
//http://localhost:8061/
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryStarter {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryStarter.class).run(args);
    }
}
