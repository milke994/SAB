package entiteti;

import entiteti.Administrator;
import entiteti.Kurir;
import entiteti.Paket;
import entiteti.ZahtevKurira;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-21T20:41:58")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> ime;
    public static volatile SingularAttribute<Korisnik, String> prezime;
    public static volatile ListAttribute<Korisnik, ZahtevKurira> zahtevKuriraList;
    public static volatile SingularAttribute<Korisnik, Administrator> administrator;
    public static volatile SingularAttribute<Korisnik, String> kIme;
    public static volatile SingularAttribute<Korisnik, String> jmbg;
    public static volatile SingularAttribute<Korisnik, String> sifra;
    public static volatile ListAttribute<Korisnik, Paket> paketList;
    public static volatile SingularAttribute<Korisnik, Kurir> kurir;
    public static volatile SingularAttribute<Korisnik, Integer> iDKorisnik;
    public static volatile SingularAttribute<Korisnik, Integer> brPosl;

}