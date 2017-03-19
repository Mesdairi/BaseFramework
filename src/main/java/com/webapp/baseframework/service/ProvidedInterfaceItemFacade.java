/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.ProvidedInterface;
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

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<ProvidedInterfaceItem> findPitmByComponent(Component component) {
        return em.createQuery("SELECT pitm FROM ProvidedInterfaceItem pitm WHERE pitm.providedInterface.component.id=" + component.getId()).getResultList();
//        return c.getProvidedInterface().getProvidedInterfaceItems();
    }
    
    public List<ProvidedInterfaceItem> findProvidedInterfaceItemByProvidedInterface(ProvidedInterface providedInterface) {
        return em.createQuery("SELECT pitm FROM ProvidedInterfaceItem pitm WHERE pitm.providedInterface.id=" + providedInterface.getId()).getResultList();
    }
    
    public ProvidedInterfaceItemFacade() {
        super(ProvidedInterfaceItem.class);
    }

    public Long generateId() {
        return getMaxId("ProvidedInterfaceItem");
    }

}
