package org.omer.kapalicarsi;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.omer.kapalicarsi.MucevherPiyasa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T19:03:07")
@StaticMetamodel(MucevherDetay.class)
public class MucevherDetay_ { 

    public static volatile SingularAttribute<MucevherDetay, Integer> mucId;
    public static volatile SingularAttribute<MucevherDetay, Serializable> resim;
    public static volatile CollectionAttribute<MucevherDetay, MucevherPiyasa> mucevherPiyasaCollection;
    public static volatile SingularAttribute<MucevherDetay, String> adi;
    public static volatile SingularAttribute<MucevherDetay, Date> kayittarih;

}