package nl.cge.jakartaee8.transakties.control;

import nl.cge.jakartaee8.transakties.entity.Transaktie;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class TransaktieAssembler {

    private static final int VOLGNUMMER = 3;
    private static final int DATUM = 4;
    private static final int RENTEDATUM = 5;
    private static final int BEDRAG = 6;
    private static final int SALDONATRANSAKTIE = 7;
    private static final int TEGENREKENING = 8;
    private static final int NAAMTEGENPARTIJ = 9;
    private static final int CODE = 13;
    private static final int OMSCHRIJVING1 = 19;
    private static final int OMSCHRIJVING2 = 20;
    private static final int OMSCHRIJVING3 = 21;

    private Function<String, BigDecimal> toBigDecimal = (value) -> new BigDecimal(value.replace(",", "."));
    private Function<String, LocalDate> toLocalDate = LocalDate::parse;

    List<Transaktie> assemble(InputStream csvInputStream) {
        List<Transaktie> result = new ArrayList<>();
        Scanner scanner = new Scanner(csvInputStream);
        scanner.nextLine();
        while (scanner.hasNext()) {
            String transaktieRegel = scanner.nextLine();
            String[] transaktieArray = transaktieRegel.split("\",\"");
            result.add(new Transaktie()
                    .setVolgnummer(transaktieArray[VOLGNUMMER])
                    .setDatum(toLocalDate.apply(transaktieArray[DATUM]))
                    .setRentedatum(toLocalDate.apply(transaktieArray[RENTEDATUM]))
                    .setBedrag(toBigDecimal.apply(transaktieArray[BEDRAG]))
                    .setSaldoNaTransaktie(toBigDecimal.apply(transaktieArray[SALDONATRANSAKTIE]))
                    .setTegenrekening(transaktieArray[TEGENREKENING])
                    .setNaamTegenpartij(transaktieArray[NAAMTEGENPARTIJ])
                    .setCode(transaktieArray[CODE])
                    .setOmschrijving1(transaktieArray[OMSCHRIJVING1])
                    .setOmschrijving2(transaktieArray[OMSCHRIJVING2])
                    .setOmschrijving3(transaktieArray[OMSCHRIJVING3])
            );
        }
        return result;
    }

}
