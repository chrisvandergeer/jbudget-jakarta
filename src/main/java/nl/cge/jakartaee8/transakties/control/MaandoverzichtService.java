package nl.cge.jakartaee8.transakties.control;

import nl.cge.jakartaee8.transakties.entity.Maand;
import nl.cge.jakartaee8.transakties.entity.Periode;
import nl.cge.jakartaee8.transakties.entity.Transaktie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

public class MaandoverzichtService {

    public Map<Maand, BigDecimal> aggregeer(List<Transaktie> transakties) {
        Periode periode = bepaalPeriode(transakties).beperkMaanden(12);
        Map<Maand, BigDecimal> maandoverzicht = transakties.stream()
                .filter(tr -> periode.isDatumInPeriode(tr.getDatum()))
                .collect(
                        groupingBy(tr -> new Maand(tr.getDatum().with(firstDayOfMonth())),
                                reducing(BigDecimal.ZERO, Transaktie::getBedrag, BigDecimal::add)));
        return new TreeMap<>(maandoverzicht);
    }

    private Periode bepaalPeriode(List<Transaktie> transakties) {
        LocalDate begindatum = transakties.stream()
                .min(comparing(Transaktie::getDatum))
                .map(tr -> tr.getDatum().with(firstDayOfMonth()).plusMonths(1))
                .orElseThrow(() -> new IllegalStateException("geen transakties gevonden voor maandoverzicht"));
        LocalDate einddatum = transakties.stream()
                .max(comparing(Transaktie::getDatum))
                .map(tr -> tr.getDatum().minusMonths(1).with(lastDayOfMonth()))
                .orElseThrow(() -> new IllegalStateException("geen transakties gevonden voor maandoverzicht"));
        return new Periode(begindatum, einddatum);
    }

}
