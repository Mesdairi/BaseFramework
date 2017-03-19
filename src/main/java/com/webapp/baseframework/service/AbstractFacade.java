/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ESDAIRI
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    
    
    /**
     * if no parameter is specified the method will try and 
     * figure out the name of the entity from the calling 
     * Facade class
     * 
     * @return the maximum id
     */
    public Long getMaxId(){
        String bean = entityClass.getName().replaceAll("/Facade/", "");
        return AbstractFacade.this.getMaxId(bean);
    }    
    
    /**
     * this is a simple adapter for the getMaxId method
     * 
     * @param bean
     *        the name of the entity to get the maximum id from 
     * @return the maximum id 
     */
    public Long getMaxId(String bean){
        return AbstractFacade.this.getMaxId(bean, "null");
    }
    
    
    /**
     * this method returns the next value of the id for any auto-incremented field, it has 
     * a default value of "id" for the idName parameter if none is specified in the call
     * 
     * @param bean
     *        the name of the entity to get the maximum id from 
     * @param idName
     *        the name of the field that is considered as id and that is auto incremented
     * @return the maximum id
     */
    public Long getMaxId(String bean, String idName) {
        if (idName == null || idName.length() <2 ) {
            idName = "id";
        }
        List<Long> maxId = getEntityManager().createQuery(" Select max(item." + idName + ") FROM " + bean + " item").getResultList();
        if (maxId == null || maxId.isEmpty() || maxId.get(0) == null) {
            return 1L;
        }
        return maxId.get(0) + 1;
    }

}
