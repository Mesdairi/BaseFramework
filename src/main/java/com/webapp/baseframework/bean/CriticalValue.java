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
public class CriticalValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String theValueType;
    private String theValue;
    private CriticalValueType type;
    @ManyToOne
    private ConstraintItem constraintItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CriticalValueType getType() {
        return type;
    }

    public void setType(CriticalValueType type) {
        this.type = type;
    }

    public ConstraintItem getConstraintItem() {
        return constraintItem;
    }

    public void setConstraintItem(ConstraintItem constraintItem) {
        this.constraintItem = constraintItem;
    }

    public String getTheValueType() {
        return theValueType;
    }

    public void setTheValueType(String theValueType) {
        this.theValueType = theValueType;
    }

    public String getTheValue() {
        return theValue;
    }

    public void setTheValue(String theValue) {
        this.theValue = theValue;
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
        if (!(object instanceof CriticalValue)) {
            return false;
        }
        CriticalValue other = (CriticalValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapp.baseframework.bean.CriticalValue[ id=" + id + " ]";
    }

}
