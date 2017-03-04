/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.ProvidedInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Soufiane
 */
@Stateless
public class ProvidedInterfaceFacade extends AbstractFacade<ProvidedInterface> {

    @PersistenceContext(unitName = "base_framework")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public ProvidedInterface findProvidedInterfaceByComponent(Component c){
    return  (ProvidedInterface) em.createQuery("SELECT p FROM ProvidedInterface p WHERE p.component.id="+c.getId()).getResultList().get(0);
//    return c.getProvidedInterface();
    }

    public ProvidedInterfaceFacade() {
        super(ProvidedInterface.class);
    }
    
}
