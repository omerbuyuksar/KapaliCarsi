package org.omer.kapalicarsi;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.omer.kapalicarsi.Kullanici;
import org.omer.kapalicarsi.MucevherDetay;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T19:03:07")
@StaticMetamodel(MucevherPiyasa.class)
public class MucevherPiyasa_ { 

    public static volatile SingularAttribute<MucevherPiyasa, Double> fiyat;
    public static volatile SingularAttribute<MucevherPiyasa, Integer> mucPyId;
    public static volatile SingularAttribute<MucevherPiyasa, Kullanici> kaydedenKullaniciId;
    public static volatile SingularAttribute<MucevherPiyasa, MucevherDetay> kaydedilenMucevherId;

}