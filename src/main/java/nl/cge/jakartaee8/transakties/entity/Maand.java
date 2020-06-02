package nl.cge.jakartaee8.transakties.entity;

import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
public class Maand implements Comparable<Maand> {

    private LocalDate datum;
    private String jaar;
    private String maand;

    public Maand(LocalDate datum) {
        this.datum = datum;
        this.jaar = DateTimeFormatter.ofPattern("yyyy").format(datum);
        this.maand = DateTimeFormatter.ofPattern("MMM").format(datum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maand maand = (Maand) o;
        return Objects.equals(datum, maand.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datum);
    }

    @Override
    public int compareTo(Maand other) {
        return this.datum.compareTo(other.datum);
    }
}
