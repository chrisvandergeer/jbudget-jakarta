package nl.cge.jakartaee8.transakties.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;

@Getter
@AllArgsConstructor
@ToString
public class Periode {

    private LocalDate begindatum;
    private LocalDate einddatum;


    public Periode beperkMaanden(Integer aantalMaanden) {
        LocalDate beperkingOpBegindatum = einddatum.minusMonths(aantalMaanden).with(firstDayOfMonth());
        LocalDate nieuweBegindatum = beperkingOpBegindatum.isAfter(begindatum) ? beperkingOpBegindatum : begindatum;
        System.out.println(new Periode(nieuweBegindatum, einddatum));
        return new Periode(nieuweBegindatum, einddatum);
    }

    public boolean isDatumInPeriode(LocalDate datum) {
        return !datum.isBefore(begindatum) && !datum.isAfter(einddatum);
    }
}
