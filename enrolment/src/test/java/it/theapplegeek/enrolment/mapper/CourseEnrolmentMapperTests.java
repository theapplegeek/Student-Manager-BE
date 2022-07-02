package it.theapplegeek.enrolment.mapper;

import it.theapplegeek.clients.enrolment.CourseEnrolmentDto;
import it.theapplegeek.enrolment.model.Enrolment;
import it.theapplegeek.shared.util.FakerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class CourseEnrolmentMapperTests {

    @Autowired
    CourseEnrolmentMapper courseEnrolmentMapper;

    @Autowired
    FakerGenerator faker;

    @BeforeEach
    void setUp() {
    }

    @Test
    void itShouldFailToDto() {
        // given
        Enrolment enrolment = null;

        // when
        CourseEnrolmentDto courseEnrolmentDto = courseEnrolmentMapper.toDto(enrolment);

        // then
        assertThat(courseEnrolmentDto).isNull();
    }

    @Test
    void itShouldToDtoWithoutCourse() {
        // given
        Enrolment enrolment = new Enrolment(
                LocalDate.now()
        );

        // when
        CourseEnrolmentDto courseEnrolmentDto = courseEnrolmentMapper.toDto(enrolment);

        // then
        System.out.println(courseEnrolmentDto);
        assertThat(courseEnrolmentDto).isNotNull();
        assertThat(courseEnrolmentDto.getCreatedDate()).isEqualTo(enrolment.getCreatedDate());
        assertThat(courseEnrolmentDto.getCourseDto()).isNull();
    }

//    @Test
//    void itShouldToDtoWithCourse() {
//        // given
//        String firstName = faker.name().firstName();
//        String lastName = faker.name().lastName();
//        String email = String.format("%s.%s@gmail.com", firstName, lastName);
//        Student student = new Student(
//                1L,
//                firstName,
//                lastName,
//                LocalDate.now().minusYears(21),
//                email,
//                21,
//                null,
//                null
//        );
//        String name = faker.educator().course();
//        String department = faker.educator().university();
//        Course course = new Course(
//                1L,
//                name,
//                department,
//                null,
//                null,
//                null
//        );
//        Enrolment enrolment = new Enrolment(
//                student,
//                course,
//                LocalDate.now()
//        );
//
//        // when
//        CourseEnrolmentDto courseEnrolmentDto = courseEnrolmentMapper.toDto(enrolment);
//
//        // then
//        System.out.println(courseEnrolmentDto);
//        assertThat(courseEnrolmentDto).isNotNull();
//        assertThat(courseEnrolmentDto.getCreatedDate()).isEqualTo(enrolment.getCreatedDate());
//        assertThat(courseEnrolmentDto.getCourseDto().getId()).isEqualTo(enrolment.getCourse().getId());
//        assertThat(courseEnrolmentDto.getCourseDto().getName()).isEqualTo(enrolment.getCourse().getName());
//        assertThat(courseEnrolmentDto.getCourseDto().getDepartment()).isEqualTo(enrolment.getCourse().getDepartment());
//    }
}