package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.DetallePresupuesto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DetallePresupuestoDaoImp implements IDetallePresupuestoDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<DetallePresupuesto> findAll() {
        return em.createQuery("from DetallePresupuesto").getResultList();
    }

    @Transactional
    @Override
    public void save(DetallePresupuesto detalle) {
        if (detalle.getId() != null && detalle.getId() > 0) {
            em.merge(detalle);
        } else {
            em.persist(detalle);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DetallePresupuesto findOne(Long id) {
        return em.find(DetallePresupuesto.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        em.remove(findOne(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Double sumSubtotalByProyecto(Long idProyecto) {

        String query = "select sum(d.subtotal) from DetallePresupuesto d " +
                "where d.presupuesto.proyecto.id = :idProyecto";
        Double resultado = (Double) em.createQuery(query)
                .setParameter("idProyecto", idProyecto)
                .getSingleResult();
        return resultado != null ? resultado : 0.0;
    }

    @Transactional(readOnly = true)
    @Override
    public Double sumSubtotalByPresupuesto(Long idPresupuesto) {
        String query = "select sum(d.subtotal) from DetallePresupuesto d " +
                "where d.presupuesto.id = :idPresupuesto";
        Double resultado = (Double) em.createQuery(query)
                .setParameter("idPresupuesto", idPresupuesto)
                .getSingleResult();
        return resultado != null ? resultado : 0.0;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DetallePresupuesto> findByPresupuesto(Long idPresupuesto) {
        return em
                .createQuery("from DetallePresupuesto d where d.presupuesto.id = :idPresupuesto",
                        DetallePresupuesto.class)
                .setParameter("idPresupuesto", idPresupuesto)
                .getResultList();
    }
}
