package ma.xproce.myjobmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MyJobMatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyJobMatchApplication.class, args);
    }


}
