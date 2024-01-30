/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.PromocionesDetalles;
import fttg.commons.model.entities.fidelizacion.PromocionesDetallesBuilder;
import fttg.commons.model.entities.fidelizacion.Ventas;
import fttg.commons.model.entities.fidelizacion.VentasDetalles;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import fttg.fidelizacion.services.PromocionesDetallesService;
import fttg.fidelizacion.services.VentasDetallesService;
import fttg.fidelizacion.services.VentasService;
import static fttg.fidelizacion.util.Util.formatCelularPhoneFromSmart;
import static fttg.fidelizacion.util.Util.validateFechaBetweenRanges;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class CanjesCuponesBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(CanjesCuponesBean.class);
    
    private PromocionesDetalles promocionDetalle;
    private Boolean canjeaCodigo;
    private List<Ventas> ventas;
    
    @Autowired
    private PromocionesDetallesService promocionesDetallesService;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private VentasDetallesService ventasDetallesService;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.promocionDetalle = new PromocionesDetallesBuilder().build();
        this.canjeaCodigo = FALSE;
    }

    public PromocionesDetalles getPromocionDetalle() {
        return promocionDetalle;
    }

    public void setPromocionDetalle(PromocionesDetalles promocionDetalle) {
        this.promocionDetalle = promocionDetalle;
    }

    public Boolean getCanjeaCodigo() {
        return canjeaCodigo;
    }

    public void setCanjeaCodigo(Boolean canjeaCodigo) {
        this.canjeaCodigo = canjeaCodigo;
    }

    public List<Ventas> getVentas() {
        return ventas;
    }

    public void setVentas(List<Ventas> ventas) {
        this.ventas = ventas;
    }
    
    public void searchByCodigo() {
        LOGGER.info("evento searchByCodigo");
        var codigo = this.promocionDetalle.getCodigo().toUpperCase();
        this.promocionDetalle = this.promocionesDetallesService.findByCodigo(codigo);
        if (isEmpty(this.promocionDetalle)) {
            this.promocionDetalle = new PromocionesDetallesBuilder().build();
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El código digitado %s no existe", codigo));
            this.ventas = new ArrayList<>();
            this.canjeaCodigo = FALSE;
        } else {
            if (validateFechaBetweenRanges(this.promocionDetalle.getPromocion().getCampania().getFechaInicio(), this.promocionDetalle.getPromocion().getCampania().getFechaFin(), LocalDate.now())) {
                if (this.promocionDetalle.getPromocion().getCampania().getStatus().equalsIgnoreCase("D")) {
                    this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("El código digitado %s pertenece a una campaña inactiva", codigo));
                    this.promocionDetalle = new PromocionesDetallesBuilder().build();
                    this.ventas = new ArrayList<>();
                    this.canjeaCodigo = FALSE;
                }
                if (this.promocionDetalle.getCanjeado().equalsIgnoreCase("S")) {
                    this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("El código digitado %s ya fue canjeado el %s", codigo, this.promocionDetalle.getFechaHoraCanje()));
                    this.promocionDetalle = new PromocionesDetallesBuilder().build();
                    this.ventas = new ArrayList<>();
                    this.canjeaCodigo = FALSE;
                } else {
                    this.ventas = this.ventasService.findAllByPromocionDetalle(this.promocionDetalle.getId());
                    if (this.ventas.stream().count() > 0) {
                        this.ventas.forEach(venta -> {
                            var detalles = this.ventasDetallesService.findAllByVenta(venta.getId());
                            var precioTotal = detalles.stream().map(VentasDetalles::getPrecioTotal).reduce(ZERO, BigDecimal::add);
                            venta.setPrecioTotal(precioTotal);
                        });
                        this.canjeaCodigo = TRUE;
                    }
                    if (isNotBlank(this.promocionDetalle.getCliente().getTelefono())) {
                        this.promocionDetalle.getCliente().setTelefono(formatCelularPhoneFromSmart(this.promocionDetalle.getCliente().getTelefono()));
                    }
                }
            } else {
               this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("El código digitado %s no pertenece a una campaña vigente", codigo));
               this.canjeaCodigo = FALSE;
            }
        }
    }
    
    public void canjearCupon() {
        LOGGER.info("evento canjearCupon");
        this.promocionDetalle.setFechaHoraCanje(LocalDateTime.now());
        this.promocionDetalle.setCanjeado("S");
        var codigo = this.promocionDetalle.getCodigo().toUpperCase();
        if (this.promocionesDetallesService.edit(this.promocionDetalle)) {
            LOGGER.info(format("El cupón con el código %s fue canejado correctamente", codigo));
        } else {
            LOGGER.info(format("Ocurrió un error al cajear el cupón con el código %s", codigo));
        }
    }
    
    public void closePopup() {
        LOGGER.info("evento closePopup");
        this.promocionDetalle = new PromocionesDetallesBuilder().build();
        this.ventas = new ArrayList<>();
        this.canjeaCodigo = FALSE;
    }
    
}