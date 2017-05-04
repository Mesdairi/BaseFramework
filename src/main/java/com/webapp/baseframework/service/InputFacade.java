/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Input;
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
public class InputFacade extends AbstractFacade<Input> {

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Input> findInputByPitm(ProvidedInterfaceItem pitm) {
        String query = "SELECT i FROM Input i WHERE i.providedInterfaceItem.id="+pitm.getId();
        return em.createQuery(query).getResultList();
    }
    
    public List<Input> findInputByProvidedInterfaceItem(ProvidedInterfaceItem providedInterfaceItem) {
        String query = "SELECT i FROM Input i WHERE i.providedInterfaceItem.id="+providedInterfaceItem.getId();
        return em.createQuery(query).getResultList();
    }

    public InputFacade() {
        super(Input.class);
    }
    
    private void clone(Input output, Input cloned){
        cloned.setId(output.getId());
        cloned.setName(output.getName());
        cloned.setType(output.getType());
        cloned.setProvidedInterfaceItem(output.getProvidedInterfaceItem());
    }
    
    public Input clone(Input output){
        Input cloned = new Input();
        clone(output, cloned);
        return cloned;
    }

}
