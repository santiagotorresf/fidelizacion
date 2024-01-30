/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.converters;

import fttg.commons.model.entities.fidelizacion.Ubicaciones;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import java.io.Serializable;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Component;
import fttg.fidelizacion.services.UbicacionesService;
import static java.lang.Integer.valueOf;
import static java.lang.String.valueOf;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jvillanueva
 */
@Component
@SpringRequestScoped
public class UbicacionesConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private UbicacionesService ubicacionesService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        }
        return this.ubicacionesService.get(valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return valueOf(((Ubicaciones) value).getId());
        }
    }
    
}
