package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.Material;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class MaterialDaoImp implements IMaterialDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Material> findAll() {
        return em.createQuery("from Material").getResultList();
    }

    @Transactional
    @Override
    public void save(Material material) {
        if (material.getId() != null && material.getId() > 0) {
            em.merge(material);
        } else {
            em.persist(material);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Material findOne(Long id) {
        return em.find(Material.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        em.remove(findOne(id));
    }
}