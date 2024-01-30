/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.TiposConcesionarios;
import fttg.fidelizacion.repositories.TiposConcesionariosDao;
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
public class TiposConcesionariosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(TiposConcesionariosService.class);
    
    @Autowired
    private TiposConcesionariosDao tiposConcesionariosDao;
    
    @Transactional(readOnly = true)
    public TiposConcesionarios get(Integer id) {
        LOGGER.info("evento get");
        return tiposConcesionariosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<TiposConcesionarios> findAll() {
        LOGGER.info("evento findAll");
        return tiposConcesionariosDao.findAll();
    }
    
    public Boolean save(TiposConcesionarios tipoConcesionario) {
        LOGGER.info("evento save");
        return tiposConcesionariosDao.save(tipoConcesionario);
    }
    
    public Boolean edit(TiposConcesionarios tipoConcesionario) {
        LOGGER.info("evento edit");
        return tiposConcesionariosDao.edit(tipoConcesionario);
    }
    
}
