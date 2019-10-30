package controller;

import model.PresencasEquipeSub;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import model.PresencasEquipeSubFacade;

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

@Named("presencasEquipeSubController")
@SessionScoped
public class PresencasEquipeSubController implements Serializable {

    @EJB
    private model.PresencasEquipeSubFacade ejbFacade;
    private List<PresencasEquipeSub> items = null;
    private PresencasEquipeSub selected;

    public PresencasEquipeSubController() {
    }

    public PresencasEquipeSub getSelected() {
        return selected;
    }

    public void setSelected(PresencasEquipeSub selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPresencasEquipeSubPK().setIdsubevento(selected.getSubeventos().getIdsubevento());
        selected.getPresencasEquipeSubPK().setIdequipe(selected.getEquipes().getIdequipe());
    }

    protected void initializeEmbeddableKey() {
        selected.setPresencasEquipeSubPK(new model.PresencasEquipeSubPK());
    }

    private PresencasEquipeSubFacade getFacade() {
        return ejbFacade;
    }

    public PresencasEquipeSub prepareCreate() {
        selected = new PresencasEquipeSub();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PresencasEquipeSubCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PresencasEquipeSubUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PresencasEquipeSubDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PresencasEquipeSub> getItems() {
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

    public PresencasEquipeSub getPresencasEquipeSub(model.PresencasEquipeSubPK id) {
        return getFacade().find(id);
    }

    public List<PresencasEquipeSub> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PresencasEquipeSub> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PresencasEquipeSub.class)
    public static class PresencasEquipeSubControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PresencasEquipeSubController controller = (PresencasEquipeSubController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "presencasEquipeSubController");
            return controller.getPresencasEquipeSub(getKey(value));
        }

        model.PresencasEquipeSubPK getKey(String value) {
            model.PresencasEquipeSubPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.PresencasEquipeSubPK();
            key.setIdequipe(Integer.parseInt(values[0]));
            key.setIdsubevento(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.PresencasEquipeSubPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdequipe());
            sb.append(SEPARATOR);
            sb.append(value.getIdsubevento());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PresencasEquipeSub) {
                PresencasEquipeSub o = (PresencasEquipeSub) object;
                return getStringKey(o.getPresencasEquipeSubPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PresencasEquipeSub.class.getName()});
                return null;
            }
        }

    }

}
