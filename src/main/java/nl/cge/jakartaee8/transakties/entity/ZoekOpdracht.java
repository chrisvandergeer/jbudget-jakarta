package nl.cge.jakartaee8.transakties.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
@Entity
public class ZoekOpdracht {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tegenpartij;
    private String omschrijving;
    private String tag;
    private String tag2add;

    private boolean isZoekenOpTegenpartij() {
        return !"".equals(tegenpartij.trim());
    }
    private boolean isZoekenOpOmschrijving() {
        return !"".equals(omschrijving.trim());
    }
    public boolean isZoekenOpTag() {
        return !"".equals(tag.trim());
    }

    public Predicate<Transaktie> getFilter() {
        Predicate<Transaktie> filter = tr -> true;
        if (isZoekenOpOmschrijving()) filter = filter.and(tr -> tr.isOmschrijving(omschrijving));
        if (isZoekenOpTegenpartij()) filter = filter.and((tr -> tr.isTegenpartij(tegenpartij)));
        if (isZoekenOpTag()) filter = filter.and(tr -> tr.hasTags(tag));
        return filter;
    }

}
