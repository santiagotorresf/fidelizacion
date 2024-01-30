/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.PromocionesDetalles;
import fttg.fidelizacion.repositories.PromocionesDetallesDao;
import java.io.Serializable;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jvillanueva
 */
@Service
@Transactional(readOnly = true)
public class PromocionesDetallesService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(PromocionesDetallesService.class);
    
    @Autowired
    private PromocionesDetallesDao promocionesDetallesDao;
    
    public PromocionesDetalles get(Integer id) {
        LOGGER.info("evento get");
        return promocionesDetallesDao.get(id);
    }
    
    public PromocionesDetalles findByCodigo(String codigo) {
        LOGGER.info("evento findByCodigo");
        return promocionesDetallesDao.findByCodigo(codigo);
    }
    
    @Transactional(readOnly = false)
    public Boolean edit(PromocionesDetalles detallePromocion) {
        LOGGER.info("evento edit");
        if (promocionesDetallesDao.edit(detallePromocion)) {
            return TRUE;
        } else {
            return FALSE;
        }
    }
    
}
