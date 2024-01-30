/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.converters;

import fttg.commons.model.entities.fidelizacion.TiposNegocios;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import fttg.fidelizacion.services.TiposNegociosService;
import java.io.Serializable;
import static java.lang.Integer.valueOf;
import static java.lang.String.valueOf;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jvillanueva
 */
@Component
@SpringRequestScoped
public class TiposNegociosConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private TiposNegociosService tiposNegociosService;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        }
        return this.tiposNegociosService.get(valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return valueOf(((TiposNegocios) value).getId());
        }
    }
    
}
