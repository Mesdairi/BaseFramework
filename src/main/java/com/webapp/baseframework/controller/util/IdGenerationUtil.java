/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webapp.baseframework.controller.util;

import com.webapp.baseframework.service.ComponentFacade;
import com.webapp.baseframework.service.ProvidedInterfaceItemFacade;
import javax.ejb.EJB;

/**
 *
 * @author ESDAIRI
 */
public class IdGenerationUtil {

    private static Long componentId = 0L;
    private static Long providedInterfaceItemId = 0L;
    @EJB
    private static ComponentFacade componentFacade;
    @EJB
    private static ProvidedInterfaceItemFacade providedInterfaceItemFacade;

    /**
     *
     */
    private static void  initialiseFields() {
        if (componentId == 0L) {
            componentId = componentFacade.generateId() - 1;
        }
        if (providedInterfaceItemId == 0L) {
            providedInterfaceItemId = providedInterfaceItemFacade.generateId() - 1;
        }
    }

    public static Long getComponentId() {
        return componentId;
    }
    
    public static Long getProvidedInterfaceItemId() {
        return providedInterfaceItemId;
    }

    public static void setComponentId(Long componentId) {
        IdGenerationUtil.componentId = componentId;
    }

    public static void setProvidedInterfaceItemId(Long providedInterfaceItemId) {
        IdGenerationUtil.providedInterfaceItemId = providedInterfaceItemId;
    }

    /**
     * this method returns the next id for the Component entities before being
     * saved to the database.
     *
     * @param bean the name of the entity class for which the id will be
     *        generated
     * @return the next id
     */
    public static Long generateCreateId(String bean) {
        switch (bean) {
            case "component":
                componentId++;
                return componentId;
            case "providedInterface":
                componentId++;
                return componentId;
            case "providedInterfaceItem":
                providedInterfaceItemId++;
                return providedInterfaceItemId;
            default:
                break;
        }
        return -1L;
    }

    /**
     * this method will decrement the value of the id when we delete an element
     *
     * @param bean the name of the entity class for which the id will be
     */
    public static void adjustDeleteId(String bean) {
        switch (bean) {
            case "component":
                componentId--;
            case "providedInterface":
                componentId--;
            case "providedInterfaceItem":
                providedInterfaceItemId--;
            default:
                break;
        }
    }

    
    /**
     * this method still under experiment if not needed it will be deleted
     * @param bean
     * @return the next id or an error code of -1L
     */
    public static Long getEditId(String bean) {
        switch (bean) {
            case "component":
                return componentId;
            case "providedInterface":
                return componentId;
            case "providedInterfaceItem":
                return providedInterfaceItemId;
            default:
                break;
        }
        return -1L;
    }

    /**
     * this method controls both the modification and gernation of the id for the entities.
     * 
     * @param bean the name of the entity class for which the id will be
     * @param dbAction the action that requires a modification for the id value
     * @return the next id or an error code
     */
    public static Long generateId(String bean, String dbAction) {
        initialiseFields();
        if (bean == null) {
            return -2L;
        } else if (dbAction == null) {
            return -3L;
        } else {
            bean = bean.toLowerCase();
            dbAction = dbAction.toLowerCase();
            if ("create".equals(dbAction)) {
                return generateCreateId(bean);
            } else if ("delete".equals(dbAction)) {
                adjustDeleteId(bean);
                return 0L;
            } else {
                return -4L;
            }
        }

    }

}
