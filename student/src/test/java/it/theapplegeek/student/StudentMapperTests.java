package it.theapplegeek.student;

import it.theapplegeek.util.FakerGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class StudentMapperTests {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    FakerGenerator faker;

    @BeforeEach
    void setUp() {}

    @Test
    void itShouldToDtoWithoutStudentCard() {
        // given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@gmail.com", firstName, lastName);
        Student student = new Student(
                1L,
                firstName,
                lastName,
                LocalDate.now().minusYears(21),
                email,
                21,
                null
        );

        // when
        StudentDto studentDto = studentMapper.toDto(student);

        // then
        System.out.println(studentDto);
        assertThat(studentDto).isNotNull();
        assertThat(studentDto.getId()).isEqualTo(1L);
        assertThat(studentDto.getFirstName()).isEqualTo(firstName);
        assertThat(studentDto.getLastName()).isEqualTo(lastName);
        assertThat(studentDto.getDob()).isEqualTo(LocalDate.now().minusYears(21));
        assertThat(studentDto.getAge()).isEqualTo(21);
        assertThat(studentDto.getEmail()).isEqualTo(email);
        assertThat(studentDto.getStudentCardDto()).isNull();
    }

    @Test
    void itShouldToDtoWithStudentCard() {
        // given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@gmail.com", firstName, lastName);
        String cardNumber = faker.code().asin();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        StudentCard studentCard = new StudentCard(
                2L,
                cardNumber,
                LocalDate.now(),
                expiredDate,
                "",
                1L,
                null
        );
        Student student = new Student(
                1L,
                firstName,
                lastName,
                LocalDate.now().minusYears(21),
                email,
                21,
                studentCard
        );

        // when
        StudentDto studentDto = studentMapper.toDto(student);

        // then
        System.out.println(studentDto);
        assertThat(studentDto.getId()).isEqualTo(1L);
        assertThat(studentDto.getFirstName()).isEqualTo(firstName);
        assertThat(studentDto).isNotNull();
        assertThat(studentDto.getLastName()).isEqualTo(lastName);
        assertThat(studentDto.getDob()).isEqualTo(LocalDate.now().minusYears(21));
        assertThat(studentDto.getAge()).isEqualTo(21);
        assertThat(studentDto.getEmail()).isEqualTo(email);
        assertThat(studentDto.getStudentCardDto()).isNotNull();
        assertThat(studentDto.getStudentCardDto().getId()).isEqualTo(student.getStudentCard().getId());
        assertThat(studentDto.getStudentCardDto().getStudentId()).isEqualTo(student.getStudentCard().getStudentId());
        assertThat(studentDto.getStudentCardDto().getCardNumber()).isEqualTo(studentCard.getCardNumber());
        assertThat(studentDto.getStudentCardDto().getCreatedDate()).isEqualTo(student.getStudentCard().getCreatedDate());
        assertThat(studentDto.getStudentCardDto().getExpiredDate()).isEqualTo(student.getStudentCard().getExpiredDate());
        assertThat(studentDto.getStudentCardDto().getValidationPeriod()).isEqualTo(student.getStudentCard().getValidationPeriod());
    }

    @Test
    void itShouldFailToDto() {
        // given
        Student student = null;

        // when
        StudentDto studentDto = studentMapper.toDto(student);

        // then
        assertThat(studentDto).isNull();
    }

    @Test
    void isShouldToEntityWithoutStudentCard() {
        // given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@gmail.com", firstName, lastName);
        StudentDto studentDto = new StudentDto(
                firstName,
                lastName,
                LocalDate.now().minusYears(21),
                email
        );

        // when
        Student student = studentMapper.toEntity(studentDto);

        // then
        System.out.println(student);
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNull();
        assertThat(student.getEmail()).isEqualTo(studentDto.getEmail());
        assertThat(student.getFirstName()).isEqualTo(studentDto.getFirstName());
        assertThat(student.getLastName()).isEqualTo(studentDto.getLastName());
        assertThat(student.getDob()).isEqualTo(studentDto.getDob());
        assertThat(student.getAge()).isEqualTo(21);
        assertThat(student.getStudentCard()).isNull();
    }

    @Test
    void isShouldToEntityWithStudentCard() {
        // given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@gmail.com", firstName, lastName);
        String cardNumber = faker.code().asin();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        StudentCardDto studentCardDto = new StudentCardDto(
                cardNumber,
                expiredDate
        );
        StudentDto studentDto = new StudentDto(
                firstName,
                lastName,
                LocalDate.now().minusYears(21),
                email,
                studentCardDto
        );

        // when
        Student student = studentMapper.toEntity(studentDto);

        // then
        System.out.println(student);
        Period period = Period.between(student.getStudentCard().getCreatedDate(), student.getStudentCard().getExpiredDate());
        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNull();
        assertThat(student.getEmail()).isEqualTo(studentDto.getEmail());
        assertThat(student.getFirstName()).isEqualTo(studentDto.getFirstName());
        assertThat(student.getLastName()).isEqualTo(studentDto.getLastName());
        assertThat(student.getDob()).isEqualTo(studentDto.getDob());
        assertThat(student.getAge()).isEqualTo(21);
        assertThat(student.getStudentCard()).isNotNull();
        assertThat(student.getStudentCard().getId()).isNull();
        assertThat(student.getStudentCard().getStudentId()).isNull();
        assertThat(student.getStudentCard().getStudent()).isNull();
        assertThat(student.getStudentCard().getCardNumber()).isEqualTo(studentDto.getStudentCardDto().getCardNumber());
        assertThat(student.getStudentCard().getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(student.getStudentCard().getExpiredDate()).isEqualTo(studentDto.getStudentCardDto().getExpiredDate());
        assertThat(student.getStudentCard().getValidationPeriod()).isEqualTo(periodStr);
    }

    @Test
    void isShouldToEntityWithStudentCardAndCardCreatedDate() {
        // given
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = String.format("%s.%s@gmail.com", firstName, lastName);
        String cardNumber = faker.code().asin();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        StudentCardDto studentCardDto = new StudentCardDto(
                cardNumber,
                LocalDate.now().plusYears(1),
                expiredDate
        );
        StudentDto studentDto = new StudentDto(
                firstName,
                lastName,
                LocalDate.now().minusYears(21),
                email,
                studentCardDto
        );

        // when
        Student student = studentMapper.toEntity(studentDto);

        // then
        System.out.println(student);
        Period period = Period.between(student.getStudentCard().getCreatedDate(), student.getStudentCard().getExpiredDate());
        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        assertThat(student).isNotNull();
        assertThat(student.getId()).isNull();
        assertThat(student.getEmail()).isEqualTo(studentDto.getEmail());
        assertThat(student.getFirstName()).isEqualTo(studentDto.getFirstName());
        assertThat(student.getLastName()).isEqualTo(studentDto.getLastName());
        assertThat(student.getDob()).isEqualTo(studentDto.getDob());
        assertThat(student.getAge()).isEqualTo(21);
        assertThat(student.getStudentCard()).isNotNull();
        assertThat(student.getStudentCard().getId()).isNull();
        assertThat(student.getStudentCard().getStudentId()).isNull();
        assertThat(student.getStudentCard().getStudent()).isNull();
        assertThat(student.getStudentCard().getCardNumber()).isEqualTo(studentDto.getStudentCardDto().getCardNumber());
        assertThat(student.getStudentCard().getCreatedDate()).isEqualTo(studentDto.getStudentCardDto().getCreatedDate());
        assertThat(student.getStudentCard().getExpiredDate()).isEqualTo(studentDto.getStudentCardDto().getExpiredDate());
        assertThat(student.getStudentCard().getValidationPeriod()).isEqualTo(periodStr);
    }

    @Test
    void itShouldFailToEntity() {
        // given
        StudentDto studentDto = null;

        // when
        Student student = studentMapper.toEntity(studentDto);

        // than
        assertThat(student).isNull();
    }
}
