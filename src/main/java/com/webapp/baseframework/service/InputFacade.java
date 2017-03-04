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

    @PersistenceContext(unitName = "base_framework")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Input> findInputByPitm(ProvidedInterfaceItem pitm){
  //  return getEntityManager().createQuery("SELECT i FROM Input i WHERE i.providedInterfaceItem.name="+pitm.getName()).getResultList();
    return pitm.getInputs();
    }

    public InputFacade() {
        super(Input.class);
    }
    
}
