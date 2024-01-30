/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import java.util.Locale;
import static java.util.Locale.forLanguageTag;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author jvillanueva
 */
@Component
@SpringRequestScoped
public class LocalizacionBean extends GenericBean {

    private static final Logger LOGGER  = getLogger(LocalizacionBean.class);
    
    private Locale locale;
    
    @Autowired
    private FacesContext facesContext;
    
    @Override
    public void init() {
        LOGGER.info("evento init");
        this.locale = forLanguageTag("es_MX");
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    public String getLanguage() {
        return this.locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
        facesContext.getViewRoot().setLocale(this.locale);
    }
    
}
