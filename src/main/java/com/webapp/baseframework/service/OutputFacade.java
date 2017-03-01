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

    @PersistenceContext(unitName = "base_framework")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     public Output findOutputByPitm(ProvidedInterfaceItem pitm){
   // return  em.createQuery("SELECT o FROM Output o WHERE o.providedInterfaceItem.name="+pitm.getName()).getResultList();
 return pitm.getOutput();
    }

    public OutputFacade() {
        super(Output.class);
    }
    
}
