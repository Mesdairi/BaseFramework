package com.webapp.baseframework.controller;

import com.webapp.baseframework.bean.AssociatedDomains;
import com.webapp.baseframework.bean.Domaine;
import com.webapp.baseframework.controller.util.JsfUtil;
import com.webapp.baseframework.controller.util.JsfUtil.PersistAction;
import com.webapp.baseframework.service.AssociatedDomainsFacade;
import com.webapp.baseframework.service.DomaineFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("associatedDomainsController")
@SessionScoped
public class AssociatedDomainsController implements Serializable {

    @EJB
    private com.webapp.baseframework.service.AssociatedDomainsFacade ejbFacade;
    private List<AssociatedDomains> items = null;
    private AssociatedDomains selected;
    private List<Domaine> domains;
    private List<Domaine> relatedDomains;
    private List<Domaine> subDomains;
    private Domaine associatedDomain;
    private String associationType;
    private List<AssociatedDomains> associatedDomainsToCommit = null;
    private List<Domaine> subDomainItems;
    private List<Domaine> relatedDomainItems;
    private Domaine selectedDomaine;
    private Domaine selectedSubDomaine;
    private Domaine selectedRelatedDomaine;

    public AssociatedDomainsController() {
    }

    public Domaine getSelectedSubDomaine() {
        return selectedSubDomaine;
    }

    public void setSelectedSubDomaine(Domaine selectedSubDomaine) {
        this.selectedSubDomaine = selectedSubDomaine;
    }

    public Domaine getSelectedRelatedDomaine() {
        return selectedRelatedDomaine;
    }

    public void setSelectedRelatedDomaine(Domaine selectedRelatedDomaine) {
        this.selectedRelatedDomaine = selectedRelatedDomaine;
    }

    public Domaine getSelectedDomaine() {
        return selectedDomaine;
    }

    public void setSelectedDomaine(Domaine selectedDomaine) {
        this.selectedDomaine = selectedDomaine;
    }

    public List<Domaine> getSubDomainItems() {
        if (subDomainItems == null) {
            initSubDomains();
        }
        return subDomainItems;
    }

    public void setSubDomainItems(List<Domaine> subDomainItems) {
        this.subDomainItems = subDomainItems;
    }

    public List<Domaine> getRelatedDomainItems() {
        if (relatedDomainItems == null) {
            initRelatedDomains();
        }
        return relatedDomainItems;
    }

    public void setRelatedDomainItems(List<Domaine> relatedDomainItems) {
        this.relatedDomainItems = relatedDomainItems;
    }

    public List<AssociatedDomains> getAssociatedDomainsToCommit() {
        return associatedDomainsToCommit;
    }

    public void setAssociatedDomainsToCommit(List<AssociatedDomains> associatedDomainsToCommit) {
        this.associatedDomainsToCommit = associatedDomainsToCommit;
    }

    public AssociatedDomains getSelected() {
        return selected;
    }

    public Domaine getAssociatedDomain() {
        return associatedDomain;
    }

    public void setAssociatedDomain(Domaine associatedDomain) {
        this.associatedDomain = associatedDomain;
    }

    public void setSelected(AssociatedDomains selected) {
        this.selected = selected;
    }

    public String getAssociationType() {
        return associationType;
    }

    public void setAssociationType(String associationType) {
        this.associationType = associationType;
    }

    @EJB
    DomaineFacade domaineFacade;

    public List<Domaine> getDomains() {
        if (domains == null) {
            domains = domaineFacade.findAll();
        }
        return domains;
    }

    public void setDomains(List<Domaine> domains) {
        this.domains = domains;
    }

    public List<Domaine> getRelatedDomains() {
        return relatedDomains;
    }

    public void setRelatedDomains(List<Domaine> relatedDomains) {
        this.relatedDomains = relatedDomains;
    }

    public List<Domaine> getSubDomains() {
        return subDomains;
    }

    public void setSubDomains(List<Domaine> subDomains) {
        this.subDomains = subDomains;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AssociatedDomainsFacade getFacade() {
        return ejbFacade;
    }
    @EJB
    AssociatedDomainsFacade associatedDomainFacade;

    public void commitAssociatedDomainsList() {
        for (AssociatedDomains associatedDomains : associatedDomainsToCommit) {
            if (associatedDomains != null) {
                setEmbeddableKeys();
                try {
                    getFacade().edit(associatedDomains);
                } catch (Exception ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            }
        }
        associatedDomainsToCommit = null;
    }

    public void initSubDomains() {
        if (selectedDomaine != null) {
            subDomainItems = getFacade().availableAssociatedDomains(selectedDomaine);
        }
    }

    public void initRelatedDomains() {
        if (selectedDomaine != null) {
            relatedDomainItems = getFacade().availableAssociatedDomains(selectedDomaine);
        }
    }

    public void domaineSelected() {
        if (selectedDomaine == null) {
            selectedDomaine= domaineFacade.findAll().get(0);
        }
        initRelatedDomains();
        initSubDomains();
    }

    public void selectedRelatedDomain() {
        selected.setType(1);
        selected.setSourceDomaine(selectedDomaine);
        selected.setDestinationDomaine(selectedRelatedDomaine);
        if (associatedDomainsToCommit == null) {
            associatedDomainsToCommit = new ArrayList<>();
        }
        associatedDomainsToCommit.add(selected);
        relatedDomainItems.remove(selectedRelatedDomaine);
        subDomainItems.remove(selectedRelatedDomaine);
    }

    public void selectedSubDomain() {
        selected.setType(2);
        selected.setSourceDomaine(selectedDomaine);
        selected.setDestinationDomaine(selectedSubDomaine);
        if (associatedDomainsToCommit == null) {
            associatedDomainsToCommit = new ArrayList<>();
        }
        associatedDomainsToCommit.add(selected);
        subDomainItems.remove(selectedSubDomaine);
        relatedDomainItems.remove(selectedSubDomaine);

    }

    public void findRelatednSubDomains() {

        relatedDomains = associatedDomainFacade.findRelatedDomain(selectedDomaine);

        subDomains = associatedDomainFacade.findSubDomain(selectedDomaine);
    }

    public void prepareCreateSubDomain() {
        selected = new AssociatedDomains();
        selectedSubDomain();
        initializeEmbeddableKey();

    }

    public void prepareCreateRelatedDomain() {
        selected = new AssociatedDomains();
        selectedRelatedDomain();
        initializeEmbeddableKey();

    }

    public List<AssociatedDomains> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public AssociatedDomains prepareCreate() {
        selected = new AssociatedDomains();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AssociatedDomainsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public AssociatedDomains getAssociatedDomains(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<AssociatedDomains> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<AssociatedDomains> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = AssociatedDomains.class)
    public static class AssociatedDomainsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AssociatedDomainsController controller = (AssociatedDomainsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "associatedDomainsController");
            return controller.getAssociatedDomains(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AssociatedDomains) {
                AssociatedDomains o = (AssociatedDomains) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AssociatedDomains.class.getName()});
                return null;
            }
        }

    }

}
