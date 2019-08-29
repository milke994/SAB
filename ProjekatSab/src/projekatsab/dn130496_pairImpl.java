/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekatsab;

import java.math.BigDecimal;
import operations.PackageOperations;
import operations.PackageOperations.Pair;

/**
 *
 * @author Nemanja
 */
public class dn130496_pairImpl<A extends Object, B extends Object> implements PackageOperations.Pair {

    private A prvi;
    private B drugi;

    public dn130496_pairImpl(A prvi, B drugi) {
        this.prvi = prvi;
        this.drugi = drugi;
    }

    @Override
    public A getFirstParam() {
        return this.prvi;
    }

    @Override
    public B getSecondParam() {
        return this.drugi;
    }

    //da li jedan kurir moze da postavlja vise zahteva, NE
    // da li treba sortirati listu
    //provera sifre
    // kurir postavlja zahtev za koje vozilo, koje postoji u bazi ili se pravi novo vozilo sa regbroj
    //zahtev kurira menja tablicu vozila u tabeli vozilo ili dodati red regbroj
    //getAllCourierRequest vraca listu cega, username, regbroj?
}
