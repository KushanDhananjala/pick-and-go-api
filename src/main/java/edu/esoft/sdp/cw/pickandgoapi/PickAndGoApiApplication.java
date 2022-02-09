package edu.esoft.sdp.cw.pickandgoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PickAndGoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickAndGoApiApplication.class, args);
    }

}
