package entiteti;

import entiteti.Isporuka;
import entiteti.Korisnik;
import entiteti.Opstina;
import entiteti.Ponuda;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Paket.class)
public class Paket_ { 

    public static volatile ListAttribute<Paket, Ponuda> ponudaList;
    public static volatile ListAttribute<Paket, Isporuka> isporukaList;
    public static volatile SingularAttribute<Paket, BigDecimal> tezina;
    public static volatile SingularAttribute<Paket, Integer> tip;
    public static volatile SingularAttribute<Paket, Opstina> oDostave;
    public static volatile SingularAttribute<Paket, Integer> iDPaketa;
    public static volatile SingularAttribute<Paket, Korisnik> iDKorisnik;
    public static volatile SingularAttribute<Paket, Opstina> oPreuzimanja;

}