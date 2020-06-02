package nl.cge.jakartaee8.transakties.control;

import nl.cge.jakartaee8.transakties.entity.Transaktie;
import nl.cge.jakartaee8.transakties.entity.ZoekOpdracht;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class QueryTagService {

    @PersistenceContext(name = "my-pu")
    private EntityManager em;

    void execute() {
        List<Transaktie> transakties = em.createQuery("select t from Transaktie t", Transaktie.class).getResultList();
        List<ZoekOpdracht> zoekOpdrachten = em.createQuery("select z from ZoekOpdracht z", ZoekOpdracht.class).getResultList();
        zoekOpdrachten.forEach(z -> transakties.stream()
                .filter(z.getFilter())
                .map(tr -> tr.addTag(z.getTag2add()))
                .forEach(tr -> em.persist(tr)));
    }
}
