/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.Output;
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
public class OutputFacade extends AbstractFacade<Output> {

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OutputFacade() {
        super(Output.class);
    }

    public Output findOutputByPitm(ProvidedInterfaceItem pitm) {

        String query = "SELECT p.output FROM ProvidedInterfaceItem p WHERE p.id=" + pitm.getId();
        Output output = (Output) em.createQuery(query).getResultList().get(0);
        if (output == null) {
            output = new Output();
        }
        return output;
    }

    public Output findOutputByProvidedInterfaceItem(ProvidedInterfaceItem providedInterfaceItem) {
        String query = "SELECT o FROM Output o WHERE o.providedInterfaceItem.id=" + providedInterfaceItem.getId();
        return (Output) em.createQuery(query).getResultList().get(0);
    }
    
    private void clone(Output output, Output cloned){
        cloned.setId(output.getId());
        cloned.setName(output.getName());
        cloned.setType(output.getType());
        cloned.setProvidedInterfaceItem(output.getProvidedInterfaceItem());
    }
    
    public Output clone(Output output){
        Output cloned = new Output();
        clone(output, cloned);
        return cloned;
    }

}
