/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.service;

import com.webapp.baseframework.bean.AssociatedDomains;
import com.webapp.baseframework.bean.Domaine;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ESDAIRI
 */
@Stateless
public class AssociatedDomainsFacade extends AbstractFacade<AssociatedDomains> {

    @PersistenceContext(unitName = "com.webapp_BaseFramework_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @EJB
    DomaineFacade domaineFacade;
    
    public List<Domaine> findByDomain(Domaine domain, int x) {
        return em.createQuery("SELECT a.destinationDomaine FROM AssociatedDomains a WHERE a.type=" + x + " AND a.sourceDomaine.id=" + domain.getId()).getResultList();
    }

    public List<Domaine> findRelatedDomain(Domaine domain) {
        return findByDomain(domain, 1);
    }

    public List<Domaine> findSubDomain(Domaine domain) {
        return findByDomain(domain, 2);
    }
    

    public List<Domaine> availableAssociatedDomains(Domaine sourceDomaine) {
        List<Domaine> availables = domaineFacade.findAll();
        for (Domaine domaine1 : findByDomain(sourceDomaine, 1)) {
            availables.remove(domaine1);
        }
        for (Domaine domaine2 : findByDomain(sourceDomaine, 2)) {
            availables.remove(domaine2);
        }
        availables.remove(sourceDomaine);

        return availables;
    }

    public void clone(AssociatedDomains associatedDomaineSource, AssociatedDomains associatedDomaineDestination) {
        associatedDomaineDestination.setId(associatedDomaineSource.getId());
        associatedDomaineDestination.setDestinationDomaine(associatedDomaineSource.getDestinationDomaine());
        associatedDomaineDestination.setSourceDomaine(associatedDomaineSource.getSourceDomaine());
        associatedDomaineDestination.setType(associatedDomaineSource.getType());
    }

    public AssociatedDomains clone(AssociatedDomains associatedDomains) {
        AssociatedDomains cloned = new AssociatedDomains();
        clone(associatedDomains, cloned);
        return cloned;
    }

    public AssociatedDomainsFacade() {
        super(AssociatedDomains.class);
    }

}
