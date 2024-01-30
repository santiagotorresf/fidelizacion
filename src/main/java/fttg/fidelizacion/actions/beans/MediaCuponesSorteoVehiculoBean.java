/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.spring.web.util.scope.annotation.SpringRequestScoped;
import fttg.fidelizacion.services.CuponesSorteosService;
import static fttg.fidelizacion.util.Util.obtainCuponesSorteoVehiculoStream;
import static fttg.fidelizacion.util.Util.preProcessCuponesSorteoVehiculo;
import static java.lang.Long.parseLong;
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
public class MediaCuponesSorteoVehiculoBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(MediaCuponesSorteoVehiculoBean.class);
    
    private StreamedContent report;
    
    @Autowired
    private CuponesSorteosService cuponesSorteosService;
    @Autowired
    private FacesContext facesContext;
    @Autowired
    private ServletContext servletContext;

    @Override
    public void init() {
        LOGGER.info("evento init");
        if (this.facesContext.getCurrentPhaseId() == RENDER_RESPONSE) {
            this.report =  new DefaultStreamedContent();
        } else {
            var beneficio = parseLong(this.facesContext.getExternalContext().getRequestParameterMap().get("beneficio"));
            var cupones = this.cuponesSorteosService.findAllByBeneficio(beneficio);
            var data = preProcessCuponesSorteoVehiculo(cupones);
            var stream = obtainCuponesSorteoVehiculoStream(this.servletContext, "rpt_cupones_sorteo.jrxml", data);
            this.report = DefaultStreamedContent.builder()
                .name(randomAlphanumeric(20).toLowerCase())
                .contentType("application/pdf")
                .contentEncoding("UTF-8")
                .stream( () -> stream)
                .build();
        }
    }

    public StreamedContent getReport() {
        return report;
    }

    public void setReport(StreamedContent report) {
        this.report = report;
    }
    
}
