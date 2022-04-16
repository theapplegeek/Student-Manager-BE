package it.theapplegeek.studentcard.mapper;

import it.theapplegeek.studentcard.dto.StudentCardDto;
import it.theapplegeek.studentcard.model.StudentCard;
import it.theapplegeek.shared.util.FakerGenerator;
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
class StudentCardMapperTests {

    @Autowired
    StudentCardMapper studentCardMapper;

    @Autowired
    FakerGenerator faker;

    @BeforeEach
    void setUp() {}

    @Test
    void itShouldFailToDto() {
        // given
        StudentCard studentCard = null;

        // when
        StudentCardDto studentCardDto = studentCardMapper.toDto(studentCard);

        // then
        assertThat(studentCardDto).isNull();
    }

    @Test
    void itShouldToDto() {
        // given
        String cardNumber = faker.code().asin();
        LocalDate createdDate = LocalDate.now();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        Period period = Period.between(createdDate, expiredDate);
        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        StudentCard studentCard = new StudentCard(
                1L,
                cardNumber,
                createdDate,
                expiredDate,
                periodStr,
                2L
        );

        // when
        StudentCardDto studentCardDto = studentCardMapper.toDto(studentCard);

        // then
        System.out.println(studentCardDto);
        assertThat(studentCardDto).isNotNull();
        assertThat(studentCardDto.getId()).isEqualTo(1L);
        assertThat(studentCardDto.getCreatedDate()).isEqualTo(studentCard.getCreatedDate());
        assertThat(studentCardDto.getExpiredDate()).isEqualTo(studentCard.getExpiredDate());
        assertThat(studentCardDto.getValidationPeriod()).isEqualTo(studentCard.getValidationPeriod());
        assertThat(studentCardDto.getStudentId()).isEqualTo(2L);
    }

    @Test
    void isShouldFailToEntity() {
        // given
        StudentCardDto studentCardDto = null;

        // when
        StudentCard studentCard = studentCardMapper.toEntity(studentCardDto);

        // then
        assertThat(studentCard).isNull();
    }

    @Test
    void itShouldToEntity() {
        // given
        String cardNumber = faker.code().asin();
        LocalDate createdDate = LocalDate.now();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        Period period = Period.between(createdDate, expiredDate);
        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        StudentCardDto studentCardDto = new StudentCardDto(
                1L,
                cardNumber,
                createdDate,
                expiredDate,
                periodStr,
                1L
        );

        // when
        StudentCard studentCard = studentCardMapper.toEntity(studentCardDto);

        // then
        System.out.println(studentCard.toString());
        assertThat(studentCard).isNotNull();
        assertThat(studentCard.getId()).isEqualTo(studentCardDto.getId());
        assertThat(studentCard.getCardNumber()).isEqualTo(studentCardDto.getCardNumber());
        assertThat(studentCard.getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(studentCard.getExpiredDate()).isEqualTo(studentCardDto.getExpiredDate());
        assertThat(studentCard.getStudentId()).isEqualTo(studentCardDto.getStudentId());
        assertThat(studentCard.getValidationPeriod()).isEqualTo(studentCardDto.getValidationPeriod());
    }

    @Test
    void itShouldToEntityWhenAddOrPutCard() {
        // given
        String cardNumber = faker.code().asin();
        LocalDate expiredDate = LocalDate.now().plusYears(faker.number().numberBetween(1, 5));
        StudentCardDto studentCardDto = new StudentCardDto(
                cardNumber,
                expiredDate,
                1L
        );

        // when
        StudentCard studentCard = studentCardMapper.toEntity(studentCardDto);

        // then
        System.out.println(studentCard.toString());
        Period period = Period.between(studentCard.getCreatedDate(), studentCard.getExpiredDate());
        String periodStr = String.format("%d years, %d months, %d days", period.getYears(), period.getMonths(), period.getDays());
        assertThat(studentCard).isNotNull();
        assertThat(studentCard.getId()).isNull();
        assertThat(studentCard.getCardNumber()).isEqualTo(studentCardDto.getCardNumber());
        assertThat(studentCard.getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(studentCard.getExpiredDate()).isEqualTo(studentCardDto.getExpiredDate());
        assertThat(studentCard.getStudentId()).isEqualTo(studentCardDto.getStudentId());
        assertThat(studentCard.getValidationPeriod()).isEqualTo(periodStr);
    }
}
