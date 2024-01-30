/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.VentasDetalles;
import fttg.fidelizacion.repositories.VentasDetallesDao;
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
public class VentasDetallesService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(VentasDetallesService.class);
    
    @Autowired
    private VentasDetallesDao ventasDetallesDao;
    
    public List<VentasDetalles> findAllByVenta(Long venta) {
        LOGGER.info("evento findAllByVenta");
        return ventasDetallesDao.findAllByVenta(venta);
    }
    
}
