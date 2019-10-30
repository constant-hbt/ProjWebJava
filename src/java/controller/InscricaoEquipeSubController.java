package controller;

import model.InscricaoEquipeSub;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import model.InscricaoEquipeSubFacade;

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

@Named("inscricaoEquipeSubController")
@SessionScoped
public class InscricaoEquipeSubController implements Serializable {

    @EJB
    private model.InscricaoEquipeSubFacade ejbFacade;
    private List<InscricaoEquipeSub> items = null;
    private InscricaoEquipeSub selected;

    public InscricaoEquipeSubController() {
    }

    public InscricaoEquipeSub getSelected() {
        return selected;
    }

    public void setSelected(InscricaoEquipeSub selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InscricaoEquipeSubFacade getFacade() {
        return ejbFacade;
    }

    public InscricaoEquipeSub prepareCreate() {
        selected = new InscricaoEquipeSub();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InscricaoEquipeSubCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InscricaoEquipeSubUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InscricaoEquipeSubDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<InscricaoEquipeSub> getItems() {
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

    public InscricaoEquipeSub getInscricaoEquipeSub(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<InscricaoEquipeSub> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<InscricaoEquipeSub> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = InscricaoEquipeSub.class)
    public static class InscricaoEquipeSubControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            InscricaoEquipeSubController controller = (InscricaoEquipeSubController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "inscricaoEquipeSubController");
            return controller.getInscricaoEquipeSub(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof InscricaoEquipeSub) {
                InscricaoEquipeSub o = (InscricaoEquipeSub) object;
                return getStringKey(o.getIdequipesub());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), InscricaoEquipeSub.class.getName()});
                return null;
            }
        }

    }

}
