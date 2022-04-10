package it.theapplegeek.util;

import java.time.LocalDate;
import java.util.Objects;

public class IsChangedChecker {
    public static Boolean isChanged(String oldString, String newString) {
        return (newString != null &&
                newString.length() > 0 &&
                !Objects.equals(newString, oldString)
        );
    }

    public static Boolean isChanged(LocalDate oldDate, LocalDate newDate) {
        return (newDate != null &&
                !Objects.equals(newDate, oldDate)
        );
    }

    public static Boolean isChanged(Long oldNumber, Long newNumber) {
        return (newNumber != null &&
                !Objects.equals(newNumber, oldNumber)
        );
    }
}
