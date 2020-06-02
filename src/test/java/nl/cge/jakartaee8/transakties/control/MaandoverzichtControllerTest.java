package nl.cge.jakartaee8.transakties.control;

import org.junit.jupiter.api.Test;

import javax.swing.text.DateFormatter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MaandoverzichtControllerTest {

    @Test
    void test() {
        LocalDate now = LocalDate.now();

        System.out.println(DateTimeFormatter.ofPattern("MMM yyyy").format(now));

    }

}