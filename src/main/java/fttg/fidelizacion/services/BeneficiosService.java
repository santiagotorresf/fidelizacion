/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.Beneficios;
import fttg.fidelizacion.repositories.BeneficiosDao;
import fttg.fidelizacion.repositories.BeneficiosDetallesDao;
import java.io.Serializable;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
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
@Transactional(readOnly = false)
public class BeneficiosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(BeneficiosService.class);
    
    @Autowired
    private BeneficiosDao beneficiosDao;
    @Autowired
    private BeneficiosDetallesDao beneficiosDetallesDao;
    
    @Transactional(readOnly = true)
    public Beneficios get(Long id) {
        LOGGER.info("evento get");
        return beneficiosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<Beneficios> findAll() {
        LOGGER.info("evento findAll");
        return beneficiosDao.findAll();
    }
    
    @Transactional(readOnly = true)
    public Boolean findFacturasByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial(Integer local, String establecimiento, String puntoEmision, String secuencial) {
        LOGGER.info("evento findFacturasByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial");
        Boolean ret;
        if (beneficiosDetallesDao.findAllByLocalAndEstablecimientoAndPuntoEmisionAndSecuencial(local, establecimiento, puntoEmision, secuencial).stream().count() > 0) {
            ret = TRUE;
        } else {
            ret = FALSE;
        }
        return ret;
    }
    
    public Boolean save(Beneficios beneficio) {
        LOGGER.info("evento save");
        return beneficiosDao.save(beneficio);
    }
    
    public Boolean edit(Beneficios beneficio) {
        LOGGER.info("evento edit");
        return beneficiosDao.edit(beneficio);
    }
    
}
