package it.theapplegeek.studentcard;

import it.theapplegeek.shared.util.FakerGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@Import({FakerGenerator.class})
@SpringBootApplication
@EnableEurekaClient
public class StudentCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
}
