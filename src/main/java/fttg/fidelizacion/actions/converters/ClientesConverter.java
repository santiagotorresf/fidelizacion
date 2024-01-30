/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.converters;

import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import fttg.fidelizacion.services.ClientesService;
import java.io.Serializable;
import javax.faces.convert.Converter;
import org.springframework.stereotype.Component;
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
public class ClientesConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private ClientesService clientesService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        }
        return this.clientesService.get(valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return valueOf(((Clientes) value).getId());
        }
    }
    
}
