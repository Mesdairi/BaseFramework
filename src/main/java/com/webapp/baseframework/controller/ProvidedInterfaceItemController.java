package com.webapp.baseframework.controller;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.Input;
import com.webapp.baseframework.bean.Output;
import com.webapp.baseframework.bean.ProvidedInterfaceItem;
import com.webapp.baseframework.controller.util.JsfUtil;
import com.webapp.baseframework.controller.util.JsfUtil.PersistAction;
import com.webapp.baseframework.service.InputFacade;
import com.webapp.baseframework.service.OutputFacade;
import com.webapp.baseframework.service.ProvidedInterfaceFacade;
import com.webapp.baseframework.service.ProvidedInterfaceItemFacade;

import java.io.Serializable;
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

@Named("providedInterfaceItemController")
@SessionScoped
public class ProvidedInterfaceItemController implements Serializable {

    @EJB
    private com.webapp.baseframework.service.ProvidedInterfaceItemFacade ejbFacade;
    private List<ProvidedInterfaceItem> items = null;
    private List<ProvidedInterfaceItem> foundPitms ;
    private ProvidedInterfaceItem selected;
    private Component selectedComponent ;
    private List<Input> inputs;
    private Output output;

    public ProvidedInterfaceItemController() {
    }

    public ProvidedInterfaceItem getSelected() {
        return selected;
    }

    public void setSelected(ProvidedInterfaceItem selected) {
        this.selected = selected;
    }

    public List<ProvidedInterfaceItem> getFoundPitms() {
        return foundPitms;
    }

    public void setFoundPitms(List<ProvidedInterfaceItem> foundPitms) {
        this.foundPitms = foundPitms;
    }
    
    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Component getSelectedComponent() {
        return selectedComponent;
    }

    public void setSelectedComponent(Component selectedComponent) {
        this.selectedComponent = selectedComponent;
    }
    

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProvidedInterfaceItemFacade getFacade() {
        return ejbFacade;
    }

    public ProvidedInterfaceItem prepareCreate() {
        selected = new ProvidedInterfaceItem();
        initializeEmbeddableKey();
        return selected;
    }

    InputFacade inputFacade = new InputFacade();
    OutputFacade outputFacade = new OutputFacade();
    ProvidedInterfaceFacade providedInterfaceFacade = new ProvidedInterfaceFacade();
    ProvidedInterfaceItemFacade pitmFacade = new ProvidedInterfaceItemFacade();

    public void findPitmByComponent() {
//        selected.setProvidedInterface(providedInterfaceFacade.findProvidedInterfaceByComponent(selected));
        setFoundPitms(pitmFacade.findPitmByComponent(selectedComponent));

    }

    public void findInputsnOutputsByPitm() {

        setInputs(inputFacade.findInputByPitm(selected));
        setOutput(outputFacade.findOutputByPitm(selected));
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProvidedInterfaceItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProvidedInterfaceItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProvidedInterfaceItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProvidedInterfaceItem> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
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

    public ProvidedInterfaceItem getProvidedInterfaceItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ProvidedInterfaceItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProvidedInterfaceItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = ProvidedInterfaceItem.class)
    public static class ProvidedInterfaceItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProvidedInterfaceItemController controller = (ProvidedInterfaceItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "providedInterfaceItemController");
            return controller.getProvidedInterfaceItem(getKey(value));
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
            if (object instanceof ProvidedInterfaceItem) {
                ProvidedInterfaceItem o = (ProvidedInterfaceItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProvidedInterfaceItem.class.getName()});
                return null;
            }
        }

    }

}
