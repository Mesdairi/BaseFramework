/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author ESDAIRI
 */
@Entity
public class SensibleValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @Enumerated(EnumType.STRING)
    private SensibleValueType sensibleValueType;
    @ManyToOne
    private SensibleParameter sensibleParameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SensibleValueType getSensibleValueType() {
        return sensibleValueType;
    }

    public void setSensibleValueType(SensibleValueType sensibleValueType) {
        this.sensibleValueType = sensibleValueType;
    }

    public SensibleParameter getSensibleParameter() {
        return sensibleParameter;
    }

    public void setSensibleParameter(SensibleParameter sensibleParameter) {
        this.sensibleParameter = sensibleParameter;
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
        if (!(object instanceof SensibleValue)) {
            return false;
        }
        SensibleValue other = (SensibleValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.webapp.baseframework.bean.SensibleValue[ id=" + id + " ]";
    }
    
}
