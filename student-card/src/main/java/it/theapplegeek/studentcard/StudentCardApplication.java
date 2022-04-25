package it.theapplegeek.studentcard;

import it.theapplegeek.shared.util.FakerGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Import({FakerGenerator.class})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "it.theapplegeek.clients"
)
public class StudentCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
}
