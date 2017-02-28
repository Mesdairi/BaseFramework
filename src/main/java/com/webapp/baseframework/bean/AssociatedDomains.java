/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author ESDAIRI
 */
@Entity
public class AssociatedDomains implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Domaine sourceDomaine;
    @ManyToOne
    private Domaine destinationDomaine;
    private int type; // 1 for association of type: related domain, 2 for an association of type destination is a sub domain of source.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Domaine getSourceDomaine() {
        return sourceDomaine;
    }

    public void setSourceDomaine(Domaine sourceDomaine) {
        this.sourceDomaine = sourceDomaine;
    }

    public Domaine getDestinationDomaine() {
        return destinationDomaine;
    }

    public void setDestinationDomaine(Domaine destinationDomaine) {
        this.destinationDomaine = destinationDomaine;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssociatedDomains)) {
            return false;
        }
        AssociatedDomains other = (AssociatedDomains) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapp.baseframework.bean.AssociatedDomains[ id=" + id + " ]";
    }
    
}
