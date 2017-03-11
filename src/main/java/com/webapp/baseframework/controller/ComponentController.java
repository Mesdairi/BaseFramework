package com.webapp.baseframework.controller;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.Domaine;
import com.webapp.baseframework.bean.Input;
import com.webapp.baseframework.bean.Output;
import com.webapp.baseframework.bean.ProvidedInterface;
import com.webapp.baseframework.bean.ProvidedInterfaceItem;
import com.webapp.baseframework.controller.util.JsfUtil;
import com.webapp.baseframework.controller.util.JsfUtil.PersistAction;
import com.webapp.baseframework.service.ComponentFacade;
import com.webapp.baseframework.service.DomaineFacade;
import com.webapp.baseframework.service.InputFacade;
import com.webapp.baseframework.service.OutputFacade;
import com.webapp.baseframework.service.ProvidedInterfaceFacade;
import com.webapp.baseframework.service.ProvidedInterfaceItemFacade;

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

@Named("componentController")
@SessionScoped
public class ComponentController implements Serializable {

    @EJB
    private ComponentFacade componentFacade;
    @EJB
    private ProvidedInterfaceFacade providedInterfaceFacade;
    @EJB
    private ProvidedInterfaceItemFacade providedInterfaceItemFacade;
    @EJB
    private OutputFacade outputFacade;
    @EJB
    private InputFacade inputFacade;
    @EJB
    private DomaineFacade domaineFacade;

    private List<Domaine> domaines = null;
    private List<Component> components = null;

    private Component selectedComponent;
    private ProvidedInterface selectedProvidedInterface;
    private ProvidedInterfaceItem selectedProvidedInterfaceItem;
    private Output selectedOutput;
    private Input selectedInput;

    public ComponentController() {
    }

    public Component getSelectedComponent() {
        if (selectedComponent == null) {
            selectedComponent = new Component();
        }
        return selectedComponent;
    }

    public void setSelectedComponent(Component selectedComponent) {
        this.selectedComponent = selectedComponent;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComponentFacade getFacade() {
        return componentFacade;
    }

    public Component prepareCreate() {
        selectedComponent = new Component();
        initializeEmbeddableKey();
        return selectedComponent;
    }

    public ComponentFacade getComponentFacade() {
        return componentFacade;
    }

    public void setComponentFacade(ComponentFacade componentFacade) {
        this.componentFacade = componentFacade;
    }

    public ProvidedInterfaceFacade getProvidedInterfaceFacade() {
        return providedInterfaceFacade;
    }

    public void setProvidedInterfaceFacade(ProvidedInterfaceFacade providedInterfaceFacade) {
        this.providedInterfaceFacade = providedInterfaceFacade;
    }

    public ProvidedInterfaceItemFacade getProvidedInterfaceItemFacade() {
        return providedInterfaceItemFacade;
    }

    public void setProvidedInterfaceItemFacade(ProvidedInterfaceItemFacade providedInterfaceItemFacade) {
        this.providedInterfaceItemFacade = providedInterfaceItemFacade;
    }

    public OutputFacade getOutputFacade() {
        return outputFacade;
    }

    public void setOutputFacade(OutputFacade outputFacade) {
        this.outputFacade = outputFacade;
    }

    public InputFacade getInputFacade() {
        return inputFacade;
    }

    public void setInputFacade(InputFacade inputFacade) {
        this.inputFacade = inputFacade;
    }

    public ProvidedInterface getSelectedProvidedInterface() {
        if (selectedProvidedInterface == null) {
            selectedProvidedInterface = new ProvidedInterface();
        }
        return selectedProvidedInterface;
    }

    public void setSelectedProvidedInterface(ProvidedInterface selectedProvidedInterface) {
        this.selectedProvidedInterface = selectedProvidedInterface;
    }

    public ProvidedInterfaceItem getSelectedProvidedInterfaceItem() {
        if (selectedProvidedInterfaceItem == null) {
            selectedProvidedInterfaceItem = new ProvidedInterfaceItem();
        }
        return selectedProvidedInterfaceItem;
    }

    public void setSelectedProvidedInterfaceItem(ProvidedInterfaceItem selectedProvidedInterfaceItem) {
        this.selectedProvidedInterfaceItem = selectedProvidedInterfaceItem;
    }

    public Output getSelectedOutput() {
        if (selectedOutput == null) {
            selectedOutput = new Output();
        }
        return selectedOutput;
    }

    public void setSelectedOutput(Output selectedOutput) {
        this.selectedOutput = selectedOutput;
    }

    public Input getSelectedInput() {
        if (selectedInput == null) {
            selectedInput = new Input();
        }
        return selectedInput;
    }

    public void setSelectedInput(Input selectedInput) {
        this.selectedInput = selectedInput;
    }

    public DomaineFacade getDomaineFacade() {
        if (domaineFacade == null) {
            domaineFacade = new DomaineFacade();
        }
        return domaineFacade;
    }

    public void setDomaineFacade(DomaineFacade domaineFacade) {
        this.domaineFacade = domaineFacade;
    }

    public List<Domaine> getDomaines() {
        if (domaines == null) {
            domaines = domaineFacade.findAll();
        }
        return domaines;
    }

    public void setDomaines(List<Domaine> domaines) {
        this.domaines = domaines;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ComponentCreated"));
        if (!JsfUtil.isValidationFailed()) {
            components = null;    // Invalidate list of components to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ComponentUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ComponentDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selectedComponent = null; // Remove selection
            components = null;    // Invalidate list of components to trigger re-query.
        }
    }

    public List<Component> getComponents() {
        if (components == null) {
            components = getFacade().findAll();
            loadProvidedInterfaceForComponent();
        }
        return components;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selectedComponent != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selectedComponent);
                } else {
                    getFacade().remove(selectedComponent);
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

    public Component getComponent(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Component> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Component> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * this method switchs the selected providedInterface 
     */
    public void switchToSelectedProvidedInterface() {
        selectedProvidedInterface = selectedComponent.getProvidedInterface();
        loadOutputForProvidedInterfaceItem();
        loadIputsForProvidedInterfaceItem();
    }
    
    /**
     * add a providedInterfaceItem to the selected providedInterface
     */
    public void addProvidedInterfaceItem() {
        List<ProvidedInterfaceItem> providedInterfaceItems = selectedProvidedInterface.getProvidedInterfaceItems();
        if (providedInterfaceItems == null) {
            providedInterfaceItems = new ArrayList<>();
        }
        providedInterfaceItems.add(selectedProvidedInterfaceItem);
        selectedProvidedInterface.setProvidedInterfaceItems(new ArrayList<ProvidedInterfaceItem>());
        selectedProvidedInterface.setProvidedInterfaceItems(providedInterfaceItems);
    }

    /**
     * removes a providedInterfaceItem form the selected providedInterface
     */
    public void removeProvidedInterfaceItem() {
        List<ProvidedInterfaceItem> providedInterfaceItems = selectedProvidedInterface.getProvidedInterfaceItems();
        if (providedInterfaceItems == null) {
            return;
        }
        providedInterfaceItems.remove(selectedProvidedInterfaceItem);
        selectedProvidedInterface.setProvidedInterfaceItems(new ArrayList<ProvidedInterfaceItem>());
        selectedProvidedInterface.setProvidedInterfaceItems(providedInterfaceItems);
    }

    /**
     * edits a providedInterfaceItem from the selected providedInterface
     */
    public void editProvidedInterfaceItem() {
        List<ProvidedInterfaceItem> providedInterfaceItems = selectedProvidedInterface.getProvidedInterfaceItems();
        if (providedInterfaceItems == null) {
            return;
        }
        int index = selectedProvidedInterface.getProvidedInterfaceItems().indexOf(selectedProvidedInterfaceItem);
        providedInterfaceItems.set(index, selectedProvidedInterfaceItem);
        selectedProvidedInterface.setProvidedInterfaceItems(new ArrayList<ProvidedInterfaceItem>());
        selectedProvidedInterface.setProvidedInterfaceItems(providedInterfaceItems);
    }

    /**
     * add an input for the selected providedInterfaceItem
     */
    public void addInput() {
        List<Input> inputs = selectedProvidedInterfaceItem.getInputs();
        if (inputs == null) {
            inputs = new ArrayList<>();
        }
        inputs.add(selectedInput);
        selectedProvidedInterfaceItem.setInputs(new ArrayList<Input>());
        selectedProvidedInterfaceItem.setInputs(inputs);
    }

    /**
     * removes the selected input form the selected provideInterfaceItem
     */
    public void removeInput() {
        List<Input> inputs = selectedProvidedInterfaceItem.getInputs();
        if (inputs == null) {
            return;
        }
        inputs.remove(selectedInput);
        selectedProvidedInterfaceItem.setInputs(new ArrayList<Input>());
        selectedProvidedInterfaceItem.setInputs(inputs);
    }

    /**
     * edits the selected input form the selected provideInterfaceItem
     */
    public void editInput() {
        List<Input> inputs = selectedProvidedInterfaceItem.getInputs();
        if (inputs == null) {
            return;
        }
        int index = inputs.indexOf(selectedInput);
        if (index < 0) {
            return;
        }
        inputs.set(index, selectedInput);
        selectedProvidedInterfaceItem.setInputs(new ArrayList<Input>());
        selectedProvidedInterfaceItem.setInputs(inputs);
    }

    /**
     * loads the providedInterface for each component
     */
    public void loadProvidedInterfaceForComponent() {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getProvidedInterface() == null) {
                components.get(i).setProvidedInterface(providedInterfaceFacade.findProvidedInterfaceByComponent(components.get(i)));
            }
        }
    }

    /**
     * loads the output for each providedInterfaceItem
     */
    public void loadOutputForProvidedInterfaceItem() {

        for (int i = 0; i < selectedProvidedInterface.getProvidedInterfaceItems().size(); i++) {
            if (selectedProvidedInterface.getProvidedInterfaceItems().get(i).getOutput() == null) {
                selectedProvidedInterface.getProvidedInterfaceItems().get(i).setOutput(outputFacade.findOutputByPitm(selectedProvidedInterface.getProvidedInterfaceItems().get(i)));
            }
        }
    }
    
    /**
     * loads the inpus for each providedInterfaceItem
     */
    public void loadIputsForProvidedInterfaceItem(){
          for (int i = 0; i < selectedProvidedInterface.getProvidedInterfaceItems().size(); i++) {
            if (selectedProvidedInterface.getProvidedInterfaceItems().get(i).getInputs() == null) {
                selectedProvidedInterface.getProvidedInterfaceItems().get(i).setInputs(inputFacade.findInputByPitm(selectedProvidedInterfaceItem));
            }
        }
    }

    @FacesConverter(forClass = Component.class)
    public static class ComponentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComponentController controller = (ComponentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "componentController");
            return controller.getComponent(getKey(value));
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
            if (object instanceof Component) {
                Component o = (Component) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Component.class.getName()});
                return null;
            }
        }

    }

}
