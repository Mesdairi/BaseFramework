/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.ProvidedInterfaceItem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Soufiane
 */
@Stateless
public class ProvidedInterfaceItemFacade extends AbstractFacade<ProvidedInterfaceItem> {

    @PersistenceContext(unitName = "base_framework")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<ProvidedInterfaceItem> findPitmByComponent(Component c){
   return em.createQuery("SELECT pitm FROM ProvidedInterfaceItem pitm WHERE pitm.providedInterface.component.id="+c.getId()).getResultList();
//        return c.getProvidedInterface().getProvidedInterfaceItems();
    }

    public ProvidedInterfaceItemFacade() {
        super(ProvidedInterfaceItem.class);
    }
    
}
