/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.TiposLocales;
import fttg.fidelizacion.repositories.TiposLocalesDao;
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
public class TiposLocalesService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(TiposLocalesService.class);
    
    @Autowired
    private TiposLocalesDao tiposLocalesDao;
    
    @Transactional(readOnly = true)
    public TiposLocales get(Integer id) {
        LOGGER.info("evento get");
        return tiposLocalesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<TiposLocales> findAll() {
        LOGGER.info("evento findAll");
        return tiposLocalesDao.findAll();
    }
    
    public Boolean save(TiposLocales tipoLocal) {
        LOGGER.info("evento save");
        return tiposLocalesDao.save(tipoLocal);
    }
    
    public Boolean edit(TiposLocales tipoLocal) {
        LOGGER.info("evento edit");
        return tiposLocalesDao.edit(tipoLocal);
    }
    
}
