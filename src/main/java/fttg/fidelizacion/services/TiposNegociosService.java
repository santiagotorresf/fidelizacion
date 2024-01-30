/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.TiposNegocios;
import fttg.fidelizacion.repositories.TiposNegociosDao;
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
public class TiposNegociosService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(TiposNegociosService.class);
    
    @Autowired
    private TiposNegociosDao tiposNegociosDao;
    
    @Transactional(readOnly = true)
    public TiposNegocios get(Integer id) {
        LOGGER.info("evento get");
        return tiposNegociosDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<TiposNegocios> findAll() {
        LOGGER.info("evento findAll");
        return tiposNegociosDao.findAll();
    }
    
    public Boolean save(TiposNegocios tipoNegocio) {
        LOGGER.info("evento save");
        return tiposNegociosDao.save(tipoNegocio);
    }
    
    public Boolean edit(TiposNegocios tipoNegocio) {
        LOGGER.info("evento edit");
        return tiposNegociosDao.edit(tipoNegocio);
    }
    
}
