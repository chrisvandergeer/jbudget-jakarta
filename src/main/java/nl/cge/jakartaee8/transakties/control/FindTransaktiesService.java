package nl.cge.jakartaee8.transakties.control;

import nl.cge.jakartaee8.transakties.entity.Transaktie;
import nl.cge.jakartaee8.transakties.entity.ZoekOpdracht;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

public class FindTransaktiesService {

    @PersistenceContext(name = "my-pu")
    private EntityManager em;

    public List<Transaktie> findTransakties() {
        return em.createQuery("select t from Transaktie t order by t.volgnummer desc", Transaktie.class).getResultList();
    }

    public List<Transaktie> findTransacties(ZoekOpdracht zoekOpdracht) {
        return em.createQuery("select t from Transaktie t order by t.volgnummer desc", Transaktie.class)
                .getResultList().stream()
                .filter(zoekOpdracht.getFilter())
                .collect(Collectors.toList());
    }
}
