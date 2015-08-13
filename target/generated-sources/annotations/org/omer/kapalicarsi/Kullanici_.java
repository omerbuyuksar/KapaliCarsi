package org.omer.kapalicarsi;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.omer.kapalicarsi.MucevherPiyasa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T19:03:07")
@StaticMetamodel(Kullanici.class)
public class Kullanici_ { 

    public static volatile CollectionAttribute<Kullanici, MucevherPiyasa> mucevherPiyasaCollection;
    public static volatile SingularAttribute<Kullanici, String> sifre;
    public static volatile SingularAttribute<Kullanici, Integer> kulId;
    public static volatile SingularAttribute<Kullanici, String> adi;
    public static volatile SingularAttribute<Kullanici, Date> kayitTarih;
    public static volatile SingularAttribute<Kullanici, Boolean> isAdmin;
    public static volatile SingularAttribute<Kullanici, String> email;

}