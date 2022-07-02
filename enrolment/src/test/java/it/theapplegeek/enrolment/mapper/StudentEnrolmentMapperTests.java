package it.theapplegeek.enrolment.mapper;

import it.theapplegeek.clients.enrolment.StudentEnrolmentDto;
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
class StudentEnrolmentMapperTests {

    @Autowired
    StudentEnrolmentMapper studentEnrolmentMapper;

    @Autowired
    FakerGenerator faker;

    @BeforeEach
    void setUp() {}

    @Test
    void itShouldFailToDto() {
        // given
        Enrolment enrolment = null;

        // when
        StudentEnrolmentDto studentEnrolmentDto = studentEnrolmentMapper.toDto(enrolment);

        // then
        assertThat(studentEnrolmentDto).isNull();
    }

    @Test
    void itShouldToDtoWithoutStudent() {
        // given
        Enrolment enrolment = new Enrolment(
                LocalDate.now()
        );

        // when
        StudentEnrolmentDto studentEnrolmentDto = studentEnrolmentMapper.toDto(enrolment);

        // then
        System.out.println(studentEnrolmentDto);
        assertThat(studentEnrolmentDto).isNotNull();
        assertThat(studentEnrolmentDto.getCreatedDate()).isEqualTo(enrolment.getCreatedDate());
        assertThat(studentEnrolmentDto.getStudentDto()).isNull();
    }

//    @Test
//    void itShouldToDtoWithStudent() {
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
//        StudentEnrolmentDto studentEnrolmentDto = studentEnrolmentMapper.toDto(enrolment);
//
//        // then
//        System.out.println(studentEnrolmentDto);
//        assertThat(studentEnrolmentDto).isNotNull();
//        assertThat(studentEnrolmentDto.getCreatedDate()).isEqualTo(enrolment.getCreatedDate());
//        assertThat(studentEnrolmentDto.getStudentDto().getId()).isEqualTo(enrolment.getStudent().getId());
//        assertThat(studentEnrolmentDto.getStudentDto().getAge()).isEqualTo(enrolment.getStudent().getAge());
//        assertThat(studentEnrolmentDto.getStudentDto().getFirstName()).isEqualTo(enrolment.getStudent().getFirstName());
//        assertThat(studentEnrolmentDto.getStudentDto().getLastName()).isEqualTo(enrolment.getStudent().getLastName());
//        assertThat(studentEnrolmentDto.getStudentDto().getDob()).isEqualTo(enrolment.getStudent().getDob());
//        assertThat(studentEnrolmentDto.getStudentDto().getEmail()).isEqualTo(enrolment.getStudent().getEmail());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto()).isNull();
//    }

//    @Test
//    void itShouldToDtoWithStudentAndStudentCard() {
//        // given
//        String firstName = faker.name().firstName();
//        String lastName = faker.name().lastName();
//        String email = String.format("%s.%s@gmail.com", firstName, lastName);
//        String cardNumber = faker.code().asin();
//        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
//        Period period = Period.between(LocalDate.now(), expiredDate);
//        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
//        StudentCard studentCard = new StudentCard(
//                2L,
//                cardNumber,
//                LocalDate.now(),
//                expiredDate,
//                "",
//                1L,
//                null
//        );
//        Student student = new Student(
//                1L,
//                firstName,
//                lastName,
//                LocalDate.now().minusYears(21),
//                email,
//                21,
//                studentCard,
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
//        StudentEnrolmentDto studentEnrolmentDto = studentEnrolmentMapper.toDto(enrolment);
//
//        // then
//        System.out.println(studentEnrolmentDto);
//        assertThat(studentEnrolmentDto).isNotNull();
//        assertThat(studentEnrolmentDto.getCreatedDate()).isEqualTo(enrolment.getCreatedDate());
//        assertThat(studentEnrolmentDto.getStudentDto().getId()).isEqualTo(enrolment.getStudent().getId());
//        assertThat(studentEnrolmentDto.getStudentDto().getAge()).isEqualTo(enrolment.getStudent().getAge());
//        assertThat(studentEnrolmentDto.getStudentDto().getFirstName()).isEqualTo(enrolment.getStudent().getFirstName());
//        assertThat(studentEnrolmentDto.getStudentDto().getLastName()).isEqualTo(enrolment.getStudent().getLastName());
//        assertThat(studentEnrolmentDto.getStudentDto().getDob()).isEqualTo(enrolment.getStudent().getDob());
//        assertThat(studentEnrolmentDto.getStudentDto().getEmail()).isEqualTo(enrolment.getStudent().getEmail());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto().getStudentId()).isEqualTo(enrolment.getStudent().getId());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto().getId()).isEqualTo(enrolment.getStudent().getStudentCard().getId());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto().getCreatedDate()).isEqualTo(enrolment.getStudent().getStudentCard().getCreatedDate());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto().getExpiredDate()).isEqualTo(enrolment.getStudent().getStudentCard().getExpiredDate());
//        assertThat(studentEnrolmentDto.getStudentDto().getStudentCardDto().getValidationPeriod()).isEqualTo(periodStr);
//    }
}