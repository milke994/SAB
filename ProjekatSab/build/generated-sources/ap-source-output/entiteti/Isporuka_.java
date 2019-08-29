package entiteti;

import entiteti.Kurir;
import entiteti.Paket;
import entiteti.Ponuda;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Isporuka.class)
public class Isporuka_ { 

    public static volatile SingularAttribute<Isporuka, Integer> iDIsporuka;
    public static volatile SingularAttribute<Isporuka, Date> vreme;
    public static volatile SingularAttribute<Isporuka, Ponuda> iDPonuda;
    public static volatile SingularAttribute<Isporuka, BigDecimal> cena;
    public static volatile SingularAttribute<Isporuka, Paket> iDPaketa;
    public static volatile SingularAttribute<Isporuka, Integer> status;
    public static volatile SingularAttribute<Isporuka, Kurir> iDKurira;

}