/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.TiposClientes;
import fttg.fidelizacion.repositories.TiposClientesDao;
import java.io.Serializable;
import java.util.List;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author storres
 */
@Service
@Transactional(readOnly = false)
public class TiposClientesService implements Serializable {
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(TiposClientesService.class);
    
    @Autowired
    private TiposClientesDao tiposClientesDao;
    
    @Transactional(readOnly = true)
    public TiposClientes get(Integer id) {
        LOGGER.info("evento get");
        return tiposClientesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<TiposClientes> findAll() {
        LOGGER.info("evento findAll");
        return tiposClientesDao.findAll();
    }
    
    public Boolean save(TiposClientes tipoCliente) {
        LOGGER.info("evento save");
        return tiposClientesDao.save(tipoCliente);
    }
    
    public Boolean edit(TiposClientes tipoCliente) {
        LOGGER.info("evento edit");
        return tiposClientesDao.edit(tipoCliente);
    }
    
}
