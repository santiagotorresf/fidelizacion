/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.Ubicaciones;
import fttg.fidelizacion.repositories.UbicacionesDao;
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
@Transactional(readOnly = false)
public class UbicacionesService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(UbicacionesService.class);
    
    @Autowired
    private UbicacionesDao ubicacionesDao;
    
    @Transactional(readOnly = true)
    public Ubicaciones get(Integer id) {
        LOGGER.info("evento get");
        return ubicacionesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<Ubicaciones> findAll() {
        LOGGER.info("evento findAll");
        return ubicacionesDao.findAll();
    }
    
    public Boolean save(Ubicaciones ubicacion) {
        LOGGER.info("evento save");
        return ubicacionesDao.save(ubicacion);
    }
    
    public Boolean edit(Ubicaciones ubicacion) {
        LOGGER.info("evento edit");
        return ubicacionesDao.edit(ubicacion);
    }
    
}
