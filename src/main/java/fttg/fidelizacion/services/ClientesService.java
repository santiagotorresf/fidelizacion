/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fttg.fidelizacion.services;

import fttg.commons.model.entities.fidelizacion.Clientes;
import fttg.fidelizacion.repositories.ClientesDao;
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
public class ClientesService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final static Logger LOGGER = getLogger(ClientesService.class);
    
    @Autowired
    private ClientesDao clientesDao;
    
    @Transactional(readOnly = true)
    public Clientes get(Integer id) {
        LOGGER.info("evento get");
        return clientesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public Clientes findByIdentificacion(String identificacion) {
        LOGGER.info("evento findByIdentificacion");
        return clientesDao.findByIdentificacion(identificacion);
    }
    
    @Transactional(readOnly = true)
    public Clientes findById(Integer id) {
        LOGGER.info("evento findById");
        return clientesDao.get(id);
    }
    
    @Transactional(readOnly = true)
    public List<Clientes> findAll() {
        LOGGER.info("evento findAll");
        return clientesDao.findAll();
    }
    
    public Boolean save(Clientes cliente) {
        LOGGER.info("evento save");
        return clientesDao.save(cliente);
    }
    
    public Boolean edit(Clientes cliente) {
        LOGGER.info("evento edit");
        return clientesDao.edit(cliente);
    }
    
}
