package it.theapplegeek.studentcard;

import it.theapplegeek.shared.util.FakerGenerator;
import it.theapplegeek.shared.util.JsonUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Import({FakerGenerator.class, JsonUtils.class})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "it.theapplegeek.clients")
@EnableCaching
public class StudentCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
}
