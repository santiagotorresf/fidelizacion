/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.Concesionarios;
import fttg.fidelizacion.repositories.ConcesionariosDao;
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
public class ConcesionariosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(ConcesionariosService.class);
    
    @Autowired
    private ConcesionariosDao concesionariosDao;
    
    
    @Transactional(readOnly = true)
    public Concesionarios get(Integer id) {
        LOGGER.info("evento get");
        return concesionariosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public Concesionarios findByIdentificacion(String identificacion) {
        LOGGER.info("evento findByIdentificacion");
        return concesionariosDao.findByIdentificacion(identificacion);
    }
    
    @Transactional(readOnly = true)
    public Concesionarios findById(Integer id) {
        LOGGER.info("evento findById");
        return concesionariosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<Concesionarios> findAll() {
        LOGGER.info("evento findAll");
        return concesionariosDao.findAll();
    }
    
    public Boolean save(Concesionarios concesionario) {
        LOGGER.info("evento save");
        return concesionariosDao.save(concesionario);
    }
    
    public Boolean edit(Concesionarios concesionario) {
        LOGGER.info("evento edit");
        return concesionariosDao.edit(concesionario);
    }
    
}
