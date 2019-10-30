package controller;

import model.PresencasEve;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import model.PresencasEveFacade;

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

@Named("presencasEveController")
@SessionScoped
public class PresencasEveController implements Serializable {

    @EJB
    private model.PresencasEveFacade ejbFacade;
    private List<PresencasEve> items = null;
    private PresencasEve selected;

    public PresencasEveController() {
    }

    public PresencasEve getSelected() {
        return selected;
    }

    public void setSelected(PresencasEve selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getPresencasEvePK().setIdevento(selected.getEventos().getIdevento());
        selected.getPresencasEvePK().setIdparticipante(selected.getParticipantes().getIdparticipante());
    }

    protected void initializeEmbeddableKey() {
        selected.setPresencasEvePK(new model.PresencasEvePK());
    }

    private PresencasEveFacade getFacade() {
        return ejbFacade;
    }

    public PresencasEve prepareCreate() {
        selected = new PresencasEve();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PresencasEveCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PresencasEveUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PresencasEveDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PresencasEve> getItems() {
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

    public PresencasEve getPresencasEve(model.PresencasEvePK id) {
        return getFacade().find(id);
    }

    public List<PresencasEve> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PresencasEve> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PresencasEve.class)
    public static class PresencasEveControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PresencasEveController controller = (PresencasEveController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "presencasEveController");
            return controller.getPresencasEve(getKey(value));
        }

        model.PresencasEvePK getKey(String value) {
            model.PresencasEvePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new model.PresencasEvePK();
            key.setIdparticipante(Integer.parseInt(values[0]));
            key.setIdevento(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(model.PresencasEvePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdparticipante());
            sb.append(SEPARATOR);
            sb.append(value.getIdevento());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PresencasEve) {
                PresencasEve o = (PresencasEve) object;
                return getStringKey(o.getPresencasEvePK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PresencasEve.class.getName()});
                return null;
            }
        }

    }

}
