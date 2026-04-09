package com.proyecto.demo.Models.DAO;

import java.util.List;
import com.proyecto.demo.Models.Entity.Corte;

public interface ICorteDao {
    public List<Corte> findAll();
    public void save(Corte corte);
    public Corte findOne(Long id);
    public void delete(Long id);
}

