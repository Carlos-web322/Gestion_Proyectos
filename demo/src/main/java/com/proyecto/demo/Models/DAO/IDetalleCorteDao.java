package com.proyecto.demo.Models.DAO;

import java.util.List;
import com.proyecto.demo.Models.Entity.DetalleCorte;

public interface IDetalleCorteDao {
    public List<DetalleCorte> findAll();
    public void save(DetalleCorte detalleCorte);
    public DetalleCorte findOne(Long id);
    public void delete(Long id);
    public Double sumSubtotalByCorte(Long idCorte);
    public List<DetalleCorte> findByCorte(Long idCorte);
    public Double sumSubtotalByPresupuesto(Long idPresupuesto);
}