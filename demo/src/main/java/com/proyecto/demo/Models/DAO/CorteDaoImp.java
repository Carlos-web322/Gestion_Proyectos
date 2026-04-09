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
        em.remove(findOne(id));
    }
}