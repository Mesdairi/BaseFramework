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

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProvidedInterface findProvidedInterfaceByComponent(Component component) {
        String query = "SELECT p FROM ProvidedInterface p WHERE p.component.id=" + component.getId();
        return (ProvidedInterface) em.createQuery(query).getResultList().get(0);
    }

    public ProvidedInterfaceFacade() {
        super(ProvidedInterface.class);
    }

    private void clone(ProvidedInterface providedInterface, ProvidedInterface cloned) {
        cloned.setId(providedInterface.getId());
        cloned.setName(providedInterface.getName());
        cloned.setComponent(providedInterface.getComponent());
    }

    public ProvidedInterface clone(ProvidedInterface providedInterface) {
        ProvidedInterface cloned = new ProvidedInterface();
        clone(providedInterface, cloned);
        return cloned;
    }

}
