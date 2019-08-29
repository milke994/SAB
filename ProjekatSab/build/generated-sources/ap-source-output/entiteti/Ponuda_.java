package entiteti;

import entiteti.Isporuka;
import entiteti.Kurir;
import entiteti.Paket;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Ponuda.class)
public class Ponuda_ { 

    public static volatile SingularAttribute<Ponuda, BigDecimal> procenat;
    public static volatile ListAttribute<Ponuda, Isporuka> isporukaList;
    public static volatile SingularAttribute<Ponuda, Integer> iDPonuda;
    public static volatile SingularAttribute<Ponuda, Kurir> iDKurir;
    public static volatile SingularAttribute<Ponuda, Paket> iDPaketa;

}