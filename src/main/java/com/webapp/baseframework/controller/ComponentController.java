package com.webapp.baseframework.controller;

import com.webapp.baseframework.bean.Component;
import com.webapp.baseframework.bean.Domaine;
import com.webapp.baseframework.bean.Input;
import com.webapp.baseframework.bean.Output;
import com.webapp.baseframework.bean.ProvidedInterface;
import com.webapp.baseframework.bean.ProvidedInterfaceItem;
import com.webapp.baseframework.controller.util.IdGenerationUtil;
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
import java.util.Objects;
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
    private List<ProvidedInterface> providedInterfaces = null;
    private List<ProvidedInterfaceItem> providedInterfaceItems = null;
    private List<Output> outputs = null;
    private List<Input> inputs = null;

    private List<Component> newComponents = null;
    private List<ProvidedInterface> newProvidedInterfaces = null;
    private List<ProvidedInterfaceItem> newProvidedInterfaceItems = null;
    private List<Output> newOutputs = null;
    private List<Input> newInputs = null;

    private Component selectedComponent;
    private ProvidedInterface selectedProvidedInterface;
    private ProvidedInterfaceItem selectedProvidedInterfaceItem;
    private Output selectedOutput;
    private Input selectedInput;

    private Component createdComponent;
    private ProvidedInterface createdProvidedInterface;
    private ProvidedInterfaceItem createdProvidedInterfaceItem;
    private Output createdOutput;
    private Input createdInput;

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

    public Component getCreatedComponent() {
        if (createdComponent == null) {
            createdComponent = new Component();
        }
        return createdComponent;
    }

    public void setCreatedComponent(Component createdComponent) {
        this.createdComponent = createdComponent;
    }

    public ProvidedInterface getCreatedProvidedInterface() {
        if (createdProvidedInterface == null) {
            createdProvidedInterface = new ProvidedInterface();
        }
        return createdProvidedInterface;
    }

    public void setCreatedProvidedInterface(ProvidedInterface createdProvidedInterface) {
        this.createdProvidedInterface = createdProvidedInterface;
    }

    public ProvidedInterfaceItem getCreatedProvidedInterfaceItem() {
        if (createdProvidedInterfaceItem == null) {
            createdProvidedInterfaceItem = new ProvidedInterfaceItem();
            createdProvidedInterfaceItem.setOutput(getCreatedOutput());
        }
        return createdProvidedInterfaceItem;
    }

    public void setCreatedProvidedInterfaceItem(ProvidedInterfaceItem createdProvidedInterfaceItem) {
        this.createdProvidedInterfaceItem = createdProvidedInterfaceItem;
    }

    public Output getCreatedOutput() {
        if (createdOutput == null) {
            createdOutput = new Output();
        }
        return createdOutput;
    }

    public void setCreatedOutput(Output createdOutput) {
        this.createdOutput = createdOutput;
    }

    public Input getCreatedInput() {
        if (createdInput == null) {
            createdInput = new Input();
        }
        return createdInput;
    }

    public void setCreatedInput(Input createdInput) {
        this.createdInput = createdInput;
    }

    public List<ProvidedInterface> getProvidedInterfaces() {
        if (providedInterfaces == null) {
            providedInterfaces = providedInterfaceFacade.findAll();
        }
        return providedInterfaces;
    }

    public void setProvidedInterfaces(List<ProvidedInterface> providedInterfaces) {
        this.providedInterfaces = providedInterfaces;
    }

    public List<ProvidedInterfaceItem> getProvidedInterfaceItems() {
        if (providedInterfaceItems == null) {
            providedInterfaceItems = new ArrayList();
        }
        return providedInterfaceItems;
    }

    public void setProvidedInterfaceItems(List<ProvidedInterfaceItem> providedInterfaceItems) {
        this.providedInterfaceItems = providedInterfaceItems;
    }

    public List<Output> getOutputs() {
        if (outputs == null) {
            outputs = new ArrayList();
        }
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    public List<Input> getInputs() {
        if (inputs == null) {
            inputs = new ArrayList();
        }
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public List<Component> getNewComponents() {
        if (newComponents == null) {
            newComponents = new ArrayList<>();
        }
        return newComponents;
    }

    public void setNewComponents(List<Component> newComponents) {
        this.newComponents = newComponents;
    }

    public List<ProvidedInterface> getNewProvidedInterfaces() {
        if (newProvidedInterfaces == null) {
            newProvidedInterfaces = new ArrayList<>();
        }
        return newProvidedInterfaces;
    }

    public void setNewProvidedInterfaces(List<ProvidedInterface> newProvidedInterfaces) {
        this.newProvidedInterfaces = newProvidedInterfaces;
    }
    
    
    
    public List<ProvidedInterfaceItem> getNewProvidedInterfaceItems() {
        if (newProvidedInterfaceItems == null) {
            newProvidedInterfaceItems = new ArrayList();
        }
        return newProvidedInterfaceItems;
    }

    public void setNewProvidedInterfaceItems(List<ProvidedInterfaceItem> newProvidedInterfaceItems) {
        this.newProvidedInterfaceItems = newProvidedInterfaceItems;
    }

    public List<Output> getNewOutputs() {
        if (newOutputs == null) {
            newOutputs = new ArrayList();
        }
        return newOutputs;
    }

    public void setNewOutputs(List<Output> newOutputs) {
        this.newOutputs = newOutputs;
    }

    public List<Input> getNewInputs() {
        if (newInputs == null) {
            newInputs = new ArrayList();
        }
        return newInputs;
    }

    public void setNewInputs(List<Input> newInputs) {
        this.newInputs = newInputs;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ComponentCreated"));
        if (!JsfUtil.isValidationFailed()) {

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
     * this method will make sure that after each refresh 
     * all selected fields get initialiazed properly
     */
    public void initializeOnPageRefresh() {
        selectedComponent = null;
        selectedProvidedInterface = null;
        selectedProvidedInterfaceItem = null;
        selectedOutput = null;
        selectedInput = null;
    }

    /**
     * this method switchs the selected providedInterface
     */
    public void switchToSelectedProvidedInterface() {
        selectedProvidedInterface = selectedComponent.getProvidedInterface();
        loadOutputForProvidedInterfaceItem();
        displayProvidedInterfaceItems();
        selectedProvidedInterfaceItem = providedInterfaceItems.get(0);
        displayInputs();
    }

    /**
     * adds a component to the list and the datatable
     */
    public void saveComponent() {
        createdComponent.setId(IdGenerationUtil.generateCreateId("component"));
        createdProvidedInterface.setId(createdComponent.getId());
        createdProvidedInterface.setComponent(createdComponent);
        createdComponent.setProvidedInterface(createdProvidedInterface);
        components.add(createdComponent);
        newComponents.add(createdComponent);
        newProvidedInterfaces.add(createdProvidedInterface);
        createdComponent = null;
        createdProvidedInterface = null;
    }

    /**
     * add a providedInterfaceItem to the selected providedInterface
     */
    public void addProvidedInterfaceItem() {
    }

    /**
     * removes a providedInterfaceItem form the selected providedInterface
     */
    public void removeProvidedInterfaceItem() {

    }

    /**
     * edits a providedInterfaceItem from the selected providedInterface
     */
    public void editProvidedInterfaceItem() {

    }

    /**
     * add an input for the selected providedInterfaceItem
     */
    public void addInput() {
    }

    /**
     * removes the selected input form the selected provideInterfaceItem
     */
    public void removeInput() {
    }

    /**
     * edits the selected input form the selected provideInterfaceItem
     */
    public void editInput() {
    }

    /**
     * loads the providedInterface for each component
     */
    public void loadProvidedInterfaceForComponent() {
        for (int i = 0; i < components.size(); i++) {
            for (int j = 0; j < getProvidedInterfaces().size(); j++) {
                if (components.get(i).getId().equals(providedInterfaces.get(j).getComponent().getId())) {
                    components.get(i).setProvidedInterface(providedInterfaces.get(j));
                }
            }
        }
    }

    /**
     * loads the output for each providedInterfaceItem
     */
    public void loadOutputForProvidedInterfaceItem() {

        for (int i = 0; i < providedInterfaceItems.size(); i++) {
            providedInterfaceItems.get(i).setOutput(outputFacade.findOutputByProvidedInterfaceItem(providedInterfaceItems.get(i)));

        }
    }

    /**
     *
     */
    public void displayProvidedInterfaceItems() {
        providedInterfaceItems = providedInterfaceItemFacade.findProvidedInterfaceItemByProvidedInterface(selectedProvidedInterface);
    }

    /**
     * loads the inpus for each providedInterfaceItem
     */
    public void displayInputs() {
        inputs = inputFacade.findInputByProvidedInterfaceItem(selectedProvidedInterfaceItem);
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
