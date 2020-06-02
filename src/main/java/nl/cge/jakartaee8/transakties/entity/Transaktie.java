package nl.cge.jakartaee8.transakties.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@Entity
@NamedQueries(value = {
        @NamedQuery(name = Transaktie.TRANSAKTIE_HOOGSTE_VOLGNUMMER,
                query = "SELECT t FROM Transaktie t WHERE t.volgnummer = (SELECT MAX(x.volgnummer) FROM Transaktie x)")
})
public class Transaktie {

    public static final String TRANSAKTIE_HOOGSTE_VOLGNUMMER = "transaktie.hoogste.volgnummer";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String volgnummer;
    private LocalDate datum;
    private LocalDate rentedatum;

    @Column(precision = 7, scale = 2)
    private BigDecimal bedrag;
    @Column(precision = 7, scale = 2)
    private BigDecimal saldoNaTransaktie;
    private String tegenrekening;
    private String naamTegenpartij;
    private String code;
    private String omschrijving1;
    private String omschrijving2;
    private String omschrijving3;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    boolean isTegenpartij(String tegenpartij) {
        String search = tegenpartij.toUpperCase();
        return tegenrekening.toUpperCase().contains(search) || naamTegenpartij.toUpperCase().contains(search);
    }

    boolean isOmschrijving(String omschrijving) {
        String search = omschrijving.toUpperCase();
        return omschrijving1.toUpperCase().contains(search);
    }

    public boolean hasTags(String tagNames) {
        List<String> tagsToSearchFor = Arrays.stream(tagNames.split(" "))
                .map(String::trim)
                .collect(Collectors.toList());
        return tags.stream().map(t -> t.getNaam()).collect(Collectors.toList()).containsAll(tagsToSearchFor);
    }

    public Long getVolgnummerAsLong() {
        return Long.valueOf(volgnummer);
    }

    public Transaktie addTag(String tag2add) {
        tags.add(new Tag(tag2add));
        return this;
    }
}
