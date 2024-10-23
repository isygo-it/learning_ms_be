package eu.novobit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * The type Config starter.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigStarter {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigStarter.class).run(args);
    }
}
