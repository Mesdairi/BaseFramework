/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Component;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ESDAIRI
 */
@Stateless
public class ComponentFacade extends AbstractFacade<Component> {

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComponentFacade() {
        super(Component.class);
    }
    
    private void clone(Component component, Component cloned){
        cloned.setId(component.getId());
        cloned.setName(component.getName());
        cloned.setComponentPath(component.getComponentPath());
        cloned.setDomaine(cloned.getDomaine());
        cloned.setProvidedInterface(component.getProvidedInterface());
    }
    
    public Component clone(Component component){
        Component cloned = new Component();
        clone(component, cloned);
        
        return cloned;
    }
    
}
