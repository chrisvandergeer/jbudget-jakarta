package nl.cge.jakartaee8.transakties.boundary;

import nl.cge.jakartaee8.transakties.control.FindTransaktiesService;
import nl.cge.jakartaee8.transakties.control.MaandoverzichtService;
import nl.cge.jakartaee8.transakties.entity.Maandtotaal;
import nl.cge.jakartaee8.transakties.entity.Transaktie;
import nl.cge.jakartaee8.transakties.entity.ZoekOpdracht;
import nl.cge.jakartaee8.transakties.entity.ZoekResultaat;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Consumes("application/json")
@Produces("application/json")
@Path("transaktie")
public class TransaktieResource {

    @PersistenceContext(name = "my-pu")
    private EntityManager em;

    @Inject
    private FindTransaktiesService findTransaktiesService;

    @Inject
    private MaandoverzichtService maandoverzichtService;

    @GET
    public ZoekResultaat findAll() {
        return find(new ZoekOpdracht());
    }

    @POST
    public ZoekResultaat find(ZoekOpdracht zoekOpdracht) {
        List<Transaktie> transacties = findTransaktiesService.findTransacties(zoekOpdracht);
        return createZoekResultaat(zoekOpdracht, transacties);
    }

    @POST
    @Path("tag")
    public ZoekResultaat tag(ZoekOpdracht zoekOpdracht) {
        List<Transaktie> transakties = findTransaktiesAndTag(zoekOpdracht);
        return createZoekResultaat(zoekOpdracht, transakties);
    }

    @POST
    @Path("tagAndSave")
    public ZoekResultaat tagAndSave(ZoekOpdracht zoekOpdracht) {
        List<Transaktie> transakties = findTransaktiesAndTag(zoekOpdracht);
        em.persist(zoekOpdracht);
        return createZoekResultaat(zoekOpdracht, transakties);
    }

    private ZoekResultaat createZoekResultaat(ZoekOpdracht zoekOpdracht, List<Transaktie> transacties) {
        ZoekResultaat zoekResultaat = new ZoekResultaat(transacties);
        if (zoekOpdracht.isZoekenOpTag() && !transacties.isEmpty()) {
            zoekResultaat.setMaandoverzicht(Maandtotaal.create(maandoverzichtService.aggregeer(transacties)));
        }
        return zoekResultaat;
    }

    private List<Transaktie> findTransaktiesAndTag(ZoekOpdracht zoekOpdracht) {
        return findTransaktiesService.findTransacties(zoekOpdracht).stream()
                .map(tr -> tr.addTag(zoekOpdracht.getTag2add()))
                .collect(Collectors.toList());
    }

}
