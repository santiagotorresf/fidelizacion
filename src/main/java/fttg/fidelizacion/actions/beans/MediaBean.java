/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import fttg.fidelizacion.services.PromocionesDetallesService;
import static fttg.fidelizacion.util.Util.obtainCuponStream;
import static fttg.fidelizacion.util.Util.preProcessDetallePromocion;
import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;
import javax.faces.context.FacesContext;
import static javax.faces.event.PhaseId.RENDER_RESPONSE;
import javax.servlet.ServletContext;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringRequestScoped
public class MediaBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(MediaBean.class);
    
    private StreamedContent cupon;
    
    @Autowired
    private PromocionesDetallesService promocionesDetallesService;
    @Autowired
    private FacesContext facesContext;
    @Autowired
    private ServletContext servletContext;

    @Override
    public void init() {
        LOGGER.info("evento init");
        if (this.facesContext.getCurrentPhaseId() == RENDER_RESPONSE) {
            this.cupon =  new DefaultStreamedContent();
        } else {
            var id = valueOf(this.facesContext.getExternalContext().getRequestParameterMap().get("promocionDetalle")); 
            var promocionDetalle = this.promocionesDetallesService.get(id);
            var data = preProcessDetallePromocion(promocionDetalle);
            var stream = obtainCuponStream(this.servletContext, "rpt_cupones.jrxml", asList(data));
            this.cupon = DefaultStreamedContent.builder()
                .name(randomAlphanumeric(20).toLowerCase())
                .contentType("application/pdf")
                .contentEncoding("UTF-8")
                .stream( () -> stream)
                .build();
        }
    }

    public StreamedContent getCupon() {
        return cupon;
    }

    public void setCupon(StreamedContent cupon) {
        this.cupon = cupon;
    }
    
}
