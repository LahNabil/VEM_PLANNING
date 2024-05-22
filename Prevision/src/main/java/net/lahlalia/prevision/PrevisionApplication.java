package net.lahlalia.prevision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class PrevisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrevisionApplication.class, args);
    }

}
