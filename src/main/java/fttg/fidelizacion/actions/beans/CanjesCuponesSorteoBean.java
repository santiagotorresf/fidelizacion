/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.actions.beans;

import fttg.commons.jsf.actions.beans.generic.GenericBean;
import fttg.commons.model.entities.fidelizacion.Beneficios;
import fttg.commons.model.entities.fidelizacion.BeneficiosBuilder;
import fttg.commons.model.entities.fidelizacion.BeneficiosDetalles;
import fttg.commons.model.entities.fidelizacion.BeneficiosDetallesBuilder;
import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.commons.model.entities.fidelizacion.ClientesBuilder;
import fttg.commons.model.entities.fidelizacion.CuponesSorteos;
import fttg.commons.model.entities.fidelizacion.CuponesSorteosBuilder;
import fttg.commons.model.entities.fidelizacion.LocalesComerciales;
import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.commons.model.entities.fidelizacion.TiposClientesBuilder;
import fttg.commons.spring.web.util.scope.annotation.SpringViewScoped;
import static fttg.commons.utils.CommonsUtils.validaIdentificacion;
import fttg.fidelizacion.services.BeneficiosService;
import fttg.fidelizacion.services.ClientesService;
import fttg.fidelizacion.services.LocalesComercialesService;
import fttg.fidelizacion.services.TiposClientesService;
import static fttg.fidelizacion.util.Util.removeBlankSpacesFromPhone;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.HashSet;
import java.util.List;
import static java.util.stream.Collectors.toList;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.application.FacesMessage.SEVERITY_WARN;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.leftPad;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import static org.primefaces.PrimeFaces.current;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 *
 * @author jvillanueva
 */
@Service
@SpringViewScoped
public class CanjesCuponesSorteoBean extends GenericBean {
    
    private final static Logger LOGGER = getLogger(CanjesCuponesSorteoBean.class);
    
    private List<TiposClientes> tiposClientes;
    private List<LocalesComerciales> localesComerciales;
    private LocalDate fechaMinimaFacturas, fechaMaximaFacturas;
    private Beneficios venta;
    private BeneficiosDetalles detalle, detalleSelected;
    private Boolean ingresarFacturas, activarSubmitAgregarDetalleFactura, grabarBeneficio, eliminarDetalle, imprimirCupones, deshabilitarEdicionClientes;
    private String identificacionClienteExistente;
    private Clientes clientePorEditar;
    private LocalesComerciales localComercial;
    
    @Autowired    
    private TiposClientesService tiposClientesService;
    @Autowired
    private ClientesService clientesService;
    @Autowired
    private LocalesComercialesService localesComercialesService;
    @Autowired
    private BeneficiosService beneficiosService;
    @Autowired
    private LoginBean loginBean;

    @Override
    public void init() {
        LOGGER.info("evento init");
        this.tiposClientes = this.tiposClientesService.findAll()
                .stream().filter(tipo -> tipo.getStatus().equalsIgnoreCase("A")).collect(toList());
        this.venta = new BeneficiosBuilder()
                .fecha(LocalDate.now())
                .cliente(new ClientesBuilder()
                        .tipoCliente(new TiposClientesBuilder()
                                .id(1)
                                .descripcion("PASAJEROS")
                                .build())
                        .status("A")
                        .build())
                .detalles(new HashSet<>())
                .status("A")
                .build();
        this.localesComerciales = this.localesComercialesService.findAll()
                .stream().filter(local -> local.getStatus().equalsIgnoreCase("A"))
                .sorted(comparing(LocalesComerciales::getNombreComercial))
                .collect(toList());
        this.ingresarFacturas = FALSE;
        this.detalle = new BeneficiosDetallesBuilder()
                .status("A")
        .build();
        this.activarSubmitAgregarDetalleFactura = FALSE;
        this.grabarBeneficio = FALSE;
        this.imprimirCupones = FALSE;
        this.deshabilitarEdicionClientes = TRUE;
        this.setRangoFechasRegistroFacturas();
        this.clientePorEditar = new ClientesBuilder()
                .status("A")
                .build();
        /*
        -- autor: storres
        -- fecha: 2023-10-10
        -- motivo: se comenta para no referenciar al local comercial sana por beneficio de triple cupón
        this.localComercial = this.localesComerciales
                .stream()
                .filter(loc -> loc.getNombreComercial().equalsIgnoreCase("SANA SANA"))
                .findFirst().orElse(null);
        */
    }

    public List<TiposClientes> getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(List<TiposClientes> tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public List<LocalesComerciales> getLocalesComerciales() {
        return localesComerciales;
    }

    public void setLocalesComerciales(List<LocalesComerciales> localesComerciales) {
        this.localesComerciales = localesComerciales;
    }

    public LocalDate getFechaMinimaFacturas() {
        return fechaMinimaFacturas;
    }

    public void setFechaMinimaFacturas(LocalDate fechaMinimaFacturas) {
        this.fechaMinimaFacturas = fechaMinimaFacturas;
    }

    public LocalDate getFechaMaximaFacturas() {
        return fechaMaximaFacturas;
    }

    public void setFechaMaximaFacturas(LocalDate fechaMaximaFacturas) {
        this.fechaMaximaFacturas = fechaMaximaFacturas;
    }

    public Beneficios getVenta() {
        return venta;
    }

    public void setVenta(Beneficios venta) {
        this.venta = venta;
    }

    public BeneficiosDetalles getDetalle() {
        return detalle;
    }

    public void setDetalle(BeneficiosDetalles detalle) {
        this.detalle = detalle;
    }

    public BeneficiosDetalles getDetalleSelected() {
        return detalleSelected;
    }

    public void setDetalleSelected(BeneficiosDetalles detalleSelected) {
        this.detalleSelected = detalleSelected;
    }

    public Boolean getIngresarFacturas() {
        return ingresarFacturas;
    }

    public void setIngresarFacturas(Boolean ingresarFacturas) {
        this.ingresarFacturas = ingresarFacturas;
    }

    public Boolean getActivarSubmitAgregarDetalleFactura() {
        return activarSubmitAgregarDetalleFactura;
    }

    public void setActivarSubmitAgregarDetalleFactura(Boolean activarSubmitAgregarDetalleFactura) {
        this.activarSubmitAgregarDetalleFactura = activarSubmitAgregarDetalleFactura;
    }

    public Boolean getGrabarBeneficio() {
        return grabarBeneficio;
    }

    public Boolean getDeshabilitarEdicionClientes() {
        return deshabilitarEdicionClientes;
    }

    public void setDeshabilitarEdicionClientes(Boolean deshabilitarEdicionClientes) {
        this.deshabilitarEdicionClientes = deshabilitarEdicionClientes;
    }

    public void setGrabarBeneficio(Boolean grabarBeneficio) {
        this.grabarBeneficio = grabarBeneficio;
    }

    public Boolean getEliminarDetalle() {
        return eliminarDetalle;
    }

    public void setEliminarDetalle(Boolean eliminarDetalle) {
        this.eliminarDetalle = eliminarDetalle;
    }

    public Boolean getImprimirCupones() {
        return imprimirCupones;
    }

    public void setImprimirCupones(Boolean imprimirCupones) {
        this.imprimirCupones = imprimirCupones;
    }

    public String getIdentificacionClienteExistente() {
        return identificacionClienteExistente;
    }

    public void setIdentificacionClienteExistente(String identificacionClienteExistente) {
        this.identificacionClienteExistente = identificacionClienteExistente;
    }

    public Clientes getClientePorEditar() {
        return clientePorEditar;
    }

    public void setClientePorEditar(Clientes clientePorEditar) {
        this.clientePorEditar = clientePorEditar;
    }

    public LocalesComerciales getLocalComercial() {
        return localComercial;
    }

    public void setLocalComercial(LocalesComerciales localComercial) {
        this.localComercial = localComercial;
    }
    
    private void setRangoFechasRegistroFacturas() {
        this.fechaMinimaFacturas = LocalDate.of(2023, 10, 11);
        var fechaActual = LocalDate.now();
        var fechaMaxima = LocalDate.of(2024, 1, 10);
        if (fechaActual.isBefore(fechaMaxima)) {
            this.fechaMaximaFacturas = fechaActual;
        } else if (fechaActual.isEqual(fechaMaxima)) {
            this.fechaMaximaFacturas = fechaMaxima;
        } else {
            this.fechaMaximaFacturas = fechaMaxima;
        }
    }
    
    public void searchClienteByCedula() {
        LOGGER.info("evento searchClienteByCedula");
        var identificacion = this.venta.getCliente().getIdentificacion();
        var cliente = this.clientesService.findByIdentificacion(this.venta.getCliente().getIdentificacion());
        if (isEmpty(cliente)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La identificación %s no corresponde a ningún cliente", identificacion));
            this.venta = new BeneficiosBuilder()
                .fecha(LocalDate.now())
                .cliente(new ClientesBuilder()
                        .tipoCliente(new TiposClientesBuilder()
                                .id(1)
                                .descripcion("PASAJEROS")
                                .build())
                        .status("A")
                        .build())
                .detalles(new HashSet<>())
                .status("A")
                .build();
        } else {
            if (cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("LOCALES") || cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("BOLETERIAS") || cliente.getTipoCliente().getDescripcion().equalsIgnoreCase("FUNCIONARIOS")) {
                this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El cliente no puede ser de tipo %s", cliente.getTipoCliente().getDescripcion()));
                this.venta = new BeneficiosBuilder()
                        .fecha(LocalDate.now())
                        .cliente(new ClientesBuilder()
                                .tipoCliente(new TiposClientesBuilder()
                                        .id(1)
                                        .descripcion("PASAJEROS")
                                        .build())
                                .status("A")
                                .build())
                        .detalles(new HashSet<>())
                        .status("A")
                        .build();
            } else {
                this.venta.setCliente(cliente);
                this.detalle.setBeneficio(this.venta);
                this.ingresarFacturas = TRUE;
                this.eliminarDetalle = FALSE;
            }
        }
    }
    
    public void searchClienteExistente() {
        LOGGER.info("evento searchClienteExistente");
        this.clientePorEditar = this.clientesService.findByIdentificacion(this.identificacionClienteExistente);
        if (isEmpty(this.clientePorEditar)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La identificación %s no corresponde a ningún cliente", this.identificacionClienteExistente));
            this.deshabilitarEdicionClientes = TRUE;
        } else {
            if (this.clientePorEditar.getTipoCliente().getDescripcion().equalsIgnoreCase("LOCALES") || this.clientePorEditar.getTipoCliente().getDescripcion().equalsIgnoreCase("BOLETERIAS") || this.clientePorEditar.getTipoCliente().getDescripcion().equalsIgnoreCase("FUNCIONARIOS")) {
                this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("El cliente no puede ser de tipo %s", this.clientePorEditar.getTipoCliente().getDescripcion()));
                this.deshabilitarEdicionClientes = TRUE;
            } else {
                this.deshabilitarEdicionClientes = FALSE;
            }
        }
    }
    
    public void actualizarClienteExistente() {
        LOGGER.info("evento actualizarClienteExistente");
        this.clientePorEditar.setRazonSocial(this.clientePorEditar.getRazonSocial().toUpperCase());
        this.clientePorEditar.setTelefono(removeBlankSpacesFromPhone(this.clientePorEditar.getTelefono()));
        this.clientePorEditar.setEmail(this.clientePorEditar.getEmail().toLowerCase());
        if (this.clientesService.edit(this.clientePorEditar)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido editado correctamente, favor realizar la consulta para continuar con el proceso", this.clientePorEditar.getRazonSocial()));
            this.clientePorEditar = new ClientesBuilder()
                    .status("A")
                    .build();
            this.identificacionClienteExistente = "";
            this.deshabilitarEdicionClientes = TRUE;
            current().executeScript("PF('clienteEditDialog').hide();");
        }
    }
    
    public void validateIdentificacion() {
        LOGGER.info("evento validateIdentificacion");
        if (validaIdentificacion(this.venta.getCliente().getIdentificacion()) == FALSE) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La identificación %s digitada es incorrecta", this.venta.getCliente().getIdentificacion()));
            this.venta.getCliente().setIdentificacion(null);
        } else if (isEmpty(this.clientesService.findByIdentificacion(this.venta.getCliente().getIdentificacion())) == FALSE) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La identificación %s digitada ya fue ingresada previamente", this.venta.getCliente().getIdentificacion()));
        }
    }
    
    private void validateCliente(Clientes cliente) {
        LOGGER.info("evento validateCliente");
        cliente.setRazonSocial(cliente.getRazonSocial().trim().toUpperCase());
        if (isNotBlank(cliente.getEmail())) {
            cliente.setEmail(cliente.getEmail().trim().toLowerCase());
            cliente.setTelefono(removeBlankSpacesFromPhone(cliente.getTelefono()));
        }
        if (isNotBlank(cliente.getTelefono()))
            cliente.setTelefono(cliente.getTelefono().trim().toLowerCase());
    }
    
    public void agregarCliente() {
        LOGGER.info("evento agregarCliente");
        this.validateCliente(this.venta.getCliente());
        if (this.clientesService.save(this.venta.getCliente())) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("El cliente %s ha sido creado correctamente, favor realizar la consulta para continuar con el proceso", this.venta.getCliente().getRazonSocial()));
            current().executeScript("PF('clienteDialog').hide();");
            LOGGER.info(format("El cliente %s ha sido grabado correctamente", this.venta.getCliente()));
        } else {
           this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al crear el cliente %s", this.venta.getCliente().getRazonSocial())); 
        }
    }
    
    public List<LocalesComerciales> autoCompleteLocalesComerciales(String query) {
        LOGGER.info("evento autoCompleteLocalesComerciales");
        List<LocalesComerciales> suggestions = new ArrayList<>();
        this.localesComerciales.stream().filter((local) -> (local.getNombreComercial().toLowerCase().startsWith(query.toLowerCase()))).forEachOrdered((local) -> {
            suggestions.add(local);
        });
        return suggestions;
    }
    
    public void blurEstablecimientoFactura() {
        LOGGER.info("evento blurEstablecimientoFactura");
        if (isNotEmpty(this.detalle.getEstablecimiento())) {
            this.detalle.setEstablecimiento(leftPad(this.detalle.getEstablecimiento(), 3, "0"));
        }
    }
    
    public void blurPuntoEmisionFactura() {
        LOGGER.info("evento blurPuntoEmisionFactura");
        if (isNotEmpty(this.detalle.getPuntoEmision())) {
            this.detalle.setPuntoEmision(leftPad(this.detalle.getPuntoEmision(), 3, "0"));
        }
    }
    
    public void blurSecuencialFactura() {
        LOGGER.info("evento blurSecuencialFactura");
        if (isNotEmpty(this.detalle.getSecuencial())) {
            this.detalle.setSecuencial(leftPad(this.detalle.getSecuencial(), 9, "0"));
        }
    }

    public void validateIngresoFactura() {
        LOGGER.info("evento validateIngresoFactura");
        if (this.venta.getDetalles().contains(this.detalle)) {
            this.addMessage(SEVERITY_WARN, "Aviso del Sistema", format("La factura %s para el local %s ha sido ingresada previamente en este formulario", this.detalle.getEstablecimiento()
                    .concat("-").concat(this.detalle.getPuntoEmision()).concat("-").concat(this.detalle.getSecuencial()),
                    this.detalle.getLocal().getNombreComercial()));
        } else if (this.beneficiosService.findFacturasByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial(this.detalle.getLocal().getId(), this.detalle.getEstablecimiento(), this.detalle.getPuntoEmision(), this.detalle.getSecuencial())) {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("La factura %s para el local %s ya ha sido ingresada anteriormete en el sistema", this.detalle.getEstablecimiento()
                    .concat("-").concat(this.detalle.getPuntoEmision()).concat("-").concat(this.detalle.getSecuencial()),
                    this.detalle.getLocal().getNombreComercial()));
        } else {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("La factura %s ha sido validada correctamente, puede proceder con el ingreso de la misma",this.detalle.getEstablecimiento()
                    .concat("-").concat(this.detalle.getPuntoEmision()).concat("-").concat(this.detalle.getSecuencial())));
            this.activarSubmitAgregarDetalleFactura = TRUE;
        }
    }
    
    public void ingresoFacturas() {
        LOGGER.info("evento ingresoFacturas");
        this.venta.getDetalles().add(this.detalle);
        this.detalle = new BeneficiosDetallesBuilder()
                .beneficio(this.venta)
                .status("A")
                .build();
        this.activarSubmitAgregarDetalleFactura = FALSE;
        var total = this.venta.getDetalles().stream().map(BeneficiosDetalles::getTotal).reduce(ZERO, BigDecimal::add);
        LOGGER.info(format("Total acumulado es %s", total));
        if (total.compareTo(new BigDecimal(15)) >= 0) 
            this.grabarBeneficio = TRUE;
    }
    
    public void onRowSelect(SelectEvent selectEvent) {
        LOGGER.info("evento onRowSelect");
        this.eliminarDetalle = TRUE;
    }

    public void onRowUnselect(UnselectEvent unselectEvent) {
        LOGGER.info("evento onRowUnselect");
        this.detalleSelected = null;
        this.eliminarDetalle = FALSE;
    }
    
    public void eliminarFacturas() {
        LOGGER.info("evento eliminarFacturas");
        this.venta.getDetalles().remove(this.detalleSelected);
        this.detalleSelected = null;
        var total = this.venta.getDetalles().stream().map(BeneficiosDetalles::getTotal).reduce(ZERO, BigDecimal::add);
        LOGGER.info(format("Total acumulado es %s", total));
        if (total.compareTo(ZERO) == 0) {
            this.eliminarDetalle = FALSE;
            this.grabarBeneficio = FALSE;
        }
        if (total.compareTo(new BigDecimal(15)) >= 0) {
            this.grabarBeneficio = TRUE;
        } else {
            this.grabarBeneficio = FALSE;
        }
    }
    
    private void generateCupones() {
        LOGGER.info("evento generateCupones");
        /*
        -- autor: storres
        -- fecha: 2023-10-10
        -- motivo: se comenta para no referenciar al local comercial sana por beneficio de triple cupón
        var cantidadCuponesSanaSana = this.venta.getDetalles()
                .stream().filter(beneficio -> beneficio.getLocal().equals(this.localComercial))
                .map(BeneficiosDetalles::getTotal)
                .reduce(ZERO, BigDecimal::add).divide(new BigDecimal(15), 2, HALF_UP).intValue();
        */
        var cantidadCuponesRegulares = this.venta.getDetalles()
                .stream().filter(beneficio -> beneficio.getLocal().equals(this.localComercial) == FALSE)
                .map(BeneficiosDetalles::getTotal)
                .reduce(ZERO, BigDecimal::add).divide(new BigDecimal(15), 2, HALF_UP).intValue();
        /*
        -- autor: storres
        -- fecha: 2023-10-10
        -- motivo: se comenta para no referenciar al local comercial sana por beneficio de triple cupón
        cantidadCuponesSanaSana = cantidadCuponesSanaSana * 3;
        var cantidadCupones =  cantidadCuponesSanaSana + cantidadCuponesRegulares;
        */
        var cantidadCupones =  cantidadCuponesRegulares;
        if (cantidadCupones == 0) {
            cantidadCupones = this.venta.getTotal().divide(new BigDecimal(15), 2, HALF_UP).intValue();
        }
        List<CuponesSorteos> cupones = new ArrayList<>();
        for (var i = 0; i < cantidadCupones; i++) {
            cupones.add(new CuponesSorteosBuilder()
                    .beneficio(this.venta)
                    .fechaHoraRegistro(LocalDateTime.now())
                    .status("A")
                    .build());
        }
        LOGGER.info(format("Se han generado un total de %s cupones", cantidadCupones));
        this.venta.setCupones(cupones);
    }
    
    public void grabarBeneficio() {
        LOGGER.info("evento grabarBeneficio");
        var total = this.venta.getDetalles().stream().map(BeneficiosDetalles::getTotal).reduce(ZERO, BigDecimal::add);
        this.venta.setTotal(total);
        this.venta.setFechaHoraRegistro(LocalDateTime.now());
        this.venta.setCreadoPor(this.loginBean.getUsuario().getNickname());
        this.venta.setIpOrigen(this.loginBean.getIpOrigen());
        this.generateCupones();
        if (this.beneficiosService.save(this.venta)) {
            this.addMessage(SEVERITY_INFO, "Aviso del Sistema", format("La grabación de facturas para el cliente %s ha culminado correctamente", this.venta.getCliente().getRazonSocial()));
            LOGGER.info(format("La grabación de facturas %s ha culminado correctamente", this.venta));
            this.activarSubmitAgregarDetalleFactura = FALSE;
            this.ingresarFacturas = FALSE;
            this.grabarBeneficio = FALSE;
            this.imprimirCupones = TRUE;
        } else {
            this.addMessage(SEVERITY_ERROR, "Aviso del Sistema", format("Ocurrio un error al grabar las facturas para el cliente %s", this.venta.getCliente().getRazonSocial()));
        }
    }

    public void closePopup() {
        LOGGER.info("evento closePopup");
        this.venta = new BeneficiosBuilder()
                .fecha(LocalDate.now())
                .cliente(new ClientesBuilder()
                        .tipoCliente(new TiposClientesBuilder()
                                .id(1)
                                .descripcion("PASAJEROS")
                                .build())
                        .status("A")
                        .build())
                .detalles(new HashSet<>())
                .status("A")
                .build();
        this.ingresarFacturas = FALSE;
        this.detalle = new BeneficiosDetallesBuilder()
                .status("A")
                .build();
        this.activarSubmitAgregarDetalleFactura = FALSE;
        this.grabarBeneficio = FALSE;
        this.imprimirCupones = FALSE;
        this.setRangoFechasRegistroFacturas();
    }
    
}