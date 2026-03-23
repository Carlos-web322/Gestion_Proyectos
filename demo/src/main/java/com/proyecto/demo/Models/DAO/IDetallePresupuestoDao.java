package com.proyecto.demo.Models.DAO;

import java.util.List;
import com.proyecto.demo.Models.Entity.DetallePresupuesto;

public interface IDetallePresupuestoDao {

    public List<DetallePresupuesto> findAll();
    public void save(DetallePresupuesto detalle);
    public DetallePresupuesto findOne(Long id);
    public void delete(Long id);
}
