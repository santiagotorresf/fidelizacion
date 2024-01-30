/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Concesionarios;
import fttg.commons.model.entities.fidelizacion.LocalesComerciales;
import fttg.commons.model.entities.fidelizacion.LocalesComercialesBuilder;
import fttg.commons.model.entities.fidelizacion.TiposLocales;
import fttg.commons.model.entities.fidelizacion.TiposNegocios;
import fttg.commons.model.entities.fidelizacion.Ubicaciones;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import fttg.fidelizacion.services.ConcesionariosService;
import fttg.fidelizacion.services.LocalesComercialesService;
import fttg.fidelizacion.services.TiposLocalesService;
import fttg.fidelizacion.services.TiposNegociosService;
import fttg.fidelizacion.services.UbicacionesService;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.util.ArrayList;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class LocalesComercialesBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(LocalesComercialesBean.class);
    
    private List<Concesionarios> concesionarios;
    private List<TiposLocales> tiposLocales;
    private List<TiposNegocios> tiposNegocios;
    private List<Ubicaciones> ubicaciones;
    private List<LocalesComerciales> localesComerciales,localesComercialesFiltered;
    private LocalesComerciales localComercial;
    private Boolean editar;
    
    @Autowired
    private ConcesionariosService concesionariosService;
    @Autowired
    private TiposLocalesService tiposLocalesService;
    @Autowired
    private TiposNegociosService TiposNegociosService;
    @Autowired
    private UbicacionesService ubicacionesService;
    @Autowired
    private LocalesComercialesService localesComercialesService;
    
    @Override
    public void init() {
        LOGGER.info("evento init");
        this.concesionarios = this.concesionariosService.findAll();
        this.tiposLocales = this.tiposLocalesService.findAll();
        this.tiposNegocios = this.TiposNegociosService.findAll();
        this.ubicaciones = this.ubicacionesService.findAll();
        this.localesComerciales = this.localesComercialesService.findAll();
        this.localComercial = new LocalesComercialesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }

    public List<Concesionarios> getConcesionarios() {
        return concesionarios;
    }

    public void setConcesionarios(List<Concesionarios> concesionarios) {
        this.concesionarios = concesionarios;
    }

    public List<TiposLocales> getTiposLocales() {
        return tiposLocales;
    }

    public void setTiposLocales(List<TiposLocales> tiposLocales) {
        this.tiposLocales = tiposLocales;
    }

    public List<TiposNegocios> getTiposNegocios() {
        return tiposNegocios;
    }

    public void setTiposNegocios(List<TiposNegocios> tiposNegocios) {
        this.tiposNegocios = tiposNegocios;
    }

    public List<Ubicaciones> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicaciones> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<LocalesComerciales> getLocalesComerciales() {
        return localesComerciales;
    }

    public void setLocalesComerciales(List<LocalesComerciales> localesComerciales) {
        this.localesComerciales = localesComerciales;
    }

    public List<LocalesComerciales> getLocalesComercialesFiltered() {
        return localesComercialesFiltered;
    }

    public void setLocalesComercialesFiltered(List<LocalesComerciales> localesComercialesFiltered) {
        this.localesComercialesFiltered = localesComercialesFiltered;
    }

    public LocalesComerciales getLocalComercial() {
        return localComercial;
    }

    public void setLocalComercial(LocalesComerciales localComercial) {
        this.localComercial = localComercial;
    }

    public Boolean getEditar() {
        return editar;
    }

    public void setEditar(Boolean editar) {
        this.editar = editar;
    }
    
    public List<Concesionarios> autoCompleteConcesionarios(String query) {
        LOGGER.info("evento autoCompleteConcesionarios");
        List<Concesionarios> suggestions = new ArrayList<>();
        this.concesionarios.stream().filter((concesionario) -> (concesionario.getRazonSocial().toLowerCase().startsWith(query.toLowerCase()))).forEachOrdered((concesionario) -> {
            suggestions.add(concesionario);
        });
        return suggestions;
    }
    
    public List<TiposLocales> autoCompleteTiposLocales(String query) {
        LOGGER.info("evento autoCompleteTiposLocales");
        List<TiposLocales> suggestions = new ArrayList<>();
        this.tiposLocales.stream().filter((tipoLocal) -> (tipoLocal.getDescripcion().toLowerCase().startsWith(query.toLowerCase()))).forEachOrdered((tipoLocal) -> {
            suggestions.add(tipoLocal);
        });
        return suggestions;
    }
    public List<TiposNegocios> autoCompleteTiposNegocios(String query) {
        LOGGER.info("evento autoCompleteTiposLocales");
        List<TiposNegocios> suggestions = new ArrayList<>();
        this.tiposNegocios.stream().filter((tipoNegocio) -> (tipoNegocio.getDescripcion().toLowerCase().startsWith(query.toLowerCase()))).forEachOrdered((tipoNegocio) -> {
            suggestions.add(tipoNegocio);
        });
        return suggestions;
    }
    
    public List<Ubicaciones> autoCompleteUbicaciones(String query) {
        LOGGER.info("evento autoCompleteUbicaciones");
        List<Ubicaciones> suggestions = new ArrayList<>();
        this.ubicaciones.stream().filter((ubicacion) -> (ubicacion.getDescripcion().toLowerCase().startsWith(query.toLowerCase()))).forEachOrdered((ubicacion) -> {
            suggestions.add(ubicacion);
        });
        return suggestions;
    }
    
    private void validateLocalesComerciales(LocalesComerciales localComercial) {
        LOGGER.info("evento validateLocalesComerciales");
        localComercial.setNombreComercial(localComercial.getNombreComercial().trim().toUpperCase());
        if (isNotBlank(localComercial.getNumeroLocal()))
            localComercial.setNumeroLocal(localComercial.getNumeroLocal().trim().toUpperCase());
        if (isNotBlank(this.localComercial.getTelefono()))
            localComercial.setTelefono(localComercial.getTelefono());
        if (isNotBlank(localComercial.getEmail()))
            localComercial.setEmail(localComercial.getEmail().trim().toLowerCase());
    }
    
    public void save() {
        LOGGER.info("evento save");
        this.localComercial.setNombreComercial(this.localComercial.getNombreComercial().trim().toUpperCase());
         if (this.localesComerciales.contains(this.localComercial)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El local %s ya fue ingresado previamente", this.localComercial.getNombreComercial().toUpperCase()));
        } else {
            this.validateLocalesComerciales(this.localComercial);
            if (this.localesComercialesService.save(this.localComercial)) {
                this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El local %s ha sido grabado correctamente", this.localComercial.getNombreComercial()));
                this.localesComerciales.add(this.localComercial);
                LOGGER.info(format("El local %s ha sido grabado correctamente", this.localComercial));
                this.localComercial = new LocalesComercialesBuilder()
                        .status("A")
                        .build();
            } else {
                this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar el local %s", this.localComercial.getNombreComercial()));
            }
        }
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.editar = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.localComercial = new LocalesComercialesBuilder()
                .status("A")
                .build();
        this.editar = FALSE;
    }
    
    public void edit() {
        LOGGER.info("evento edit");
        this.validateLocalesComerciales(this.localComercial);
        if (this.localesComercialesService.edit(this.localComercial)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El local %s ha sido editado correctamente", this.localComercial.getNombreComercial()));
            LOGGER.info(format("El local %s ha sido editado correctamente", this.localComercial));
            this.localComercial = null;
            this.localComercial = new LocalesComercialesBuilder()
                    .status("A")
                    .build();
            this.editar = FALSE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al editar el local %s", this.localComercial.getNombreComercial()));
        }
    }
    
}
