package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.Corte;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CorteDaoImp implements ICorteDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Corte> findAll() {
        return em.createQuery("from Corte").getResultList();
    }

    @Transactional
    @Override
    public void save(Corte corte) {
        if (corte.getId() != null && corte.getId() > 0) {
            em.merge(corte);
        } else {
            em.persist(corte);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Corte findOne(Long id) {
        return em.find(Corte.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Corte corte = findOne(id);
        corte.setPresupuesto(null);
        em.merge(corte);
        em.remove(corte);
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Corte> findByPresupuesto(Long idPresupuesto) {
        return em.createQuery("from Corte c where c.presupuesto.id = :idPresupuesto")
                .setParameter("idPresupuesto", idPresupuesto)
                .getResultList();
    }

    @Transactional
    @Override
    public void deleteByPresupuesto(Long idPresupuesto) {
        em.createQuery("delete from Corte c where c.presupuesto.id = :idPresupuesto")
                .setParameter("idPresupuesto", idPresupuesto)
                .executeUpdate();
    }
}