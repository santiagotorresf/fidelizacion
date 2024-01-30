/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.Ventas;
import fttg.fidelizacion.repositories.VentasDao;
import java.io.Serializable;
import java.util.List;
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
public class VentasService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(VentasService.class);
    
    @Autowired
    private VentasDao ventasDao;
    
    public List<Ventas> findAllByPromocionDetalle(Integer promocionDetalle) {
        LOGGER.info("evento findAllByPromocionDetalle");
        return ventasDao.findAllByPromocionDetalle(promocionDetalle);
    }
    
}
