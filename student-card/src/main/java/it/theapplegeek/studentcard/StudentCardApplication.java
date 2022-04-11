package it.theapplegeek.studentcard;

import it.theapplegeek.util.FakerGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({FakerGenerator.class})
public class StudentCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentCardApplication.class, args);
    }
}
