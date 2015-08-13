/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.omer.kapalicarsi.bean;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.omer.kapalicarsi.Kullanici;

/**
 *
 * @author pc
 */
@Stateless
@Named
public class KullaniciFacade extends AbstractFacade<Kullanici> {
    @PersistenceContext(unitName = "org.omer_KapaliCarsi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KullaniciFacade() {
        super(Kullanici.class);
    }
    public List<Kullanici> getKullanicilar(){
        
        return  em.createNamedQuery("Kullanici.findAll").getResultList() ;
    }
    public Kullanici getKullanici(String email,String sifre){
        
        return  em.createNamedQuery("Kullanici.findByEmailAndSifre",Kullanici.class)
                    .setParameter("email", email)
                    .setParameter("sifre", sifre)
                    .getSingleResult();
    }
    public Kullanici getKullaniciById(String id){
        
        return em.createNamedQuery("Kullanici.findByKulId",Kullanici.class)
                .setParameter("kulId", id).getSingleResult();
        
    }
    
}
