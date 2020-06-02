package nl.cge.jakartaee8.transakties.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ZoekResultaat {

    private List<Transaktie> transakties = new ArrayList<>();
    private List<Maandtotaal> maandoverzicht;

    public ZoekResultaat(List<Transaktie> transakties) {
        this.transakties = transakties;
    }

}
