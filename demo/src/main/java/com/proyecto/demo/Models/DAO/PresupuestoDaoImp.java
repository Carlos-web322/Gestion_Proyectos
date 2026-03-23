package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.Presupuesto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PresupuestoDaoImp implements IPresupuestoDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Presupuesto> findAll() {
        return em.createQuery("from Presupuesto").getResultList();
    }

    @Transactional
    @Override
    public void save(Presupuesto presupuesto) {
        if (presupuesto.getId() != null && presupuesto.getId() > 0) {
            em.merge(presupuesto);
        } else {
            em.persist(presupuesto);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Presupuesto findOne(Long id) {
        return em.find(Presupuesto.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
    Presupuesto presupuesto = findOne(id);
    presupuesto.setProyecto(null);
    em.merge(presupuesto);
    em.remove(presupuesto);
    }
}