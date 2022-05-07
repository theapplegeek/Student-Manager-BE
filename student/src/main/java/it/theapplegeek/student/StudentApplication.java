package it.theapplegeek.student;

import it.theapplegeek.shared.util.FakerGenerator;
import it.theapplegeek.shared.util.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@Import({FakerGenerator.class, JsonUtils.class})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "it.theapplegeek.clients"
)
public class StudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApplication.class, args);
    }
}
