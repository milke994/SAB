package entiteti;

import entiteti.Grad;
import entiteti.Paket;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Opstina.class)
public class Opstina_ { 

    public static volatile SingularAttribute<Opstina, Grad> iDGrad;
    public static volatile SingularAttribute<Opstina, Integer> iDOpstina;
    public static volatile SingularAttribute<Opstina, String> naziv;
    public static volatile SingularAttribute<Opstina, BigDecimal> x;
    public static volatile SingularAttribute<Opstina, BigDecimal> y;
    public static volatile ListAttribute<Opstina, Paket> paketList;
    public static volatile ListAttribute<Opstina, Paket> paketList1;

}