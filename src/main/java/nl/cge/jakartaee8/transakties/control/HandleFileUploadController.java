package nl.cge.jakartaee8.transakties.control;

import nl.cge.jakartaee8.transakties.entity.Transaktie;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static nl.cge.jakartaee8.transakties.entity.Transaktie.TRANSAKTIE_HOOGSTE_VOLGNUMMER;

@Stateless
public class HandleFileUploadController {

    @Inject
    private TransaktieAssembler assembler;

    @PersistenceContext(name = "my-pu")
    private EntityManager em;

    @Inject
    private QueryTagService queryTagService;

    public void execute(InputStream inputStream) {
        List<Transaktie> transakties = assembler.assemble(inputStream);
        Long laagsteVolgnummerInBestand = laagsteVolgnummerInBestand(transakties);
        Long hoogsteVolgnummerInDatabase = leesHoogsteVolgnummerUitDatabase();

        System.out.println("Laagste volgnummer in bestand: " + laagsteVolgnummerInBestand);
        System.out.println("Hoogste volgnumer in database: " + hoogsteVolgnummerInDatabase);
        System.out.println("Aantal records in bestand voor opschonen: " + transakties.size());
        transakties = transakties.stream().filter(t -> t.getVolgnummerAsLong() > hoogsteVolgnummerInDatabase).collect(Collectors.toList());
        System.out.println("Aantal records in bestand na opschonen:  " + transakties.size());
        valideerVolgnummers(laagsteVolgnummerInBestand, hoogsteVolgnummerInDatabase);
        valideerOpNieuweTransakties(transakties.size());
        transakties.forEach(tr -> em.persist(tr));
        queryTagService.execute();
    }

    private void valideerOpNieuweTransakties(int size) {
        if (size == 0) {
            throw new InvalidTransaktiebestandException("Geen nieuwe transakties te importeren");
        }
    }

    private void valideerVolgnummers(Long laagsteVolgnummerInBestand, Long hoogsteVolgnummerInDatabase) {
        if (laagsteVolgnummerInBestand < hoogsteVolgnummerInDatabase) {
            throw new InvalidTransaktiebestandException("Upload eerst oudere transakties, laagste volgnummer moet minimaal zijn: " + hoogsteVolgnummerInDatabase + 1);
        }
    }

    private Long leesHoogsteVolgnummerUitDatabase() {
        return em.createNamedQuery(TRANSAKTIE_HOOGSTE_VOLGNUMMER, Transaktie.class).getResultList().stream()
                .map(Transaktie::getVolgnummerAsLong)
                .findAny().orElse(0L);
    }

    private Long laagsteVolgnummerInBestand(List<Transaktie> transakties) {
        return transakties.stream()
                .min(Comparator.comparing(Transaktie::getVolgnummer, String::compareTo))
                .map(Transaktie::getVolgnummerAsLong)
                .orElse(0L);
    }
}
