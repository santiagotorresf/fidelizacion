/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fttg.fidelizacion.actions.converters;

import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import java.io.Serializable;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Component;
import fttg.fidelizacion.services.TiposClientesService;
import static java.lang.Integer.valueOf;
import static java.lang.String.valueOf;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author storres
 */
@Component
@SpringRequestScoped
public class TiposClientesConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private TiposClientesService tiposClientesService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        }
        return this.tiposClientesService.get(valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return valueOf(((TiposClientes) value).getId());
        }
    }
    
}
