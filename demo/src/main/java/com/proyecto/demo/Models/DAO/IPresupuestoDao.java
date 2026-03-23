package com.proyecto.demo.Models.DAO;

import java.util.List;
import com.proyecto.demo.Models.Entity.Presupuesto;

public interface IPresupuestoDao {

    public List<Presupuesto> findAll();
    public void save(Presupuesto presupuesto);
    public Presupuesto findOne(Long id);
    public void delete(Long id);
}