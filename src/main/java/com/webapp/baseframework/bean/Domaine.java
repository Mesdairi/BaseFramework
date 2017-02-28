/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ESDAIRI
 */
@Entity
public class Domaine implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "domaine")
    private List<Component> components;
    @OneToMany(mappedBy = "sourceDomaine")
    private List<AssociatedDomains> sourceDomains; 
    @OneToMany(mappedBy = "destinationDomaine")
    private List<AssociatedDomains> destinationDomains;
   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Component> getComponents() {
        if (components == null) {
            components = new ArrayList();
        }
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<AssociatedDomains> getSourceDomains() {
        if (sourceDomains == null) {
            sourceDomains = new ArrayList();
        }
        return sourceDomains;
    }

    public void setSourceDomains(List<AssociatedDomains> sourceDomains) {
        this.sourceDomains = sourceDomains;
    }

    public List<AssociatedDomains> getDestinationDomains() {
        if (destinationDomains == null) {
            destinationDomains = new ArrayList();
        }
        return destinationDomains;
    }

    public void setDestinationDomains(List<AssociatedDomains> destinationDomains) {
        this.destinationDomains = destinationDomains;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Domaine)) {
            return false;
        }
        Domaine other = (Domaine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

}
