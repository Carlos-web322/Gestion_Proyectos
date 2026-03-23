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
}
