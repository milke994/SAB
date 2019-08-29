package entiteti;

import entiteti.Isporuka;
import entiteti.Korisnik;
import entiteti.Ponuda;
import entiteti.Vozilo;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Kurir.class)
public class Kurir_ { 

    public static volatile ListAttribute<Kurir, Ponuda> ponudaList;
    public static volatile ListAttribute<Kurir, Isporuka> isporukaList;
    public static volatile SingularAttribute<Kurir, Integer> brIsporuka;
    public static volatile SingularAttribute<Kurir, BigDecimal> profit;
    public static volatile SingularAttribute<Kurir, Integer> iDKorisnik;
    public static volatile SingularAttribute<Kurir, Integer> status;
    public static volatile SingularAttribute<Kurir, Korisnik> korisnik;
    public static volatile SingularAttribute<Kurir, Vozilo> iDVozilo;

}