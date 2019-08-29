package entiteti;

import entiteti.Kurir;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Vozilo.class)
public class Vozilo_ { 

    public static volatile SingularAttribute<Vozilo, String> regBroj;
    public static volatile SingularAttribute<Vozilo, Integer> tipGoriva;
    public static volatile SingularAttribute<Vozilo, BigDecimal> potrosnja;
    public static volatile ListAttribute<Vozilo, Kurir> kurirList;
    public static volatile SingularAttribute<Vozilo, Integer> iDVozilo;

}