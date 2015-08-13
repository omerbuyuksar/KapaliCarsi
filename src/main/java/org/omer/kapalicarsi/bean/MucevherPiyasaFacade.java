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
import org.omer.kapalicarsi.MucevherPiyasa;

/**
 *
 * @author pc
 */
@Stateless
@Named
public class MucevherPiyasaFacade extends AbstractFacade<MucevherPiyasa> {
    @PersistenceContext(unitName = "org.omer_KapaliCarsi_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MucevherPiyasaFacade() {
        super(MucevherPiyasa.class);
    }

    public List<MucevherPiyasa> getMucevherPiyasalar() {
        return em.createNamedQuery("MucevherPiyasa.findAll").getResultList();
    }
   
    
    
}
