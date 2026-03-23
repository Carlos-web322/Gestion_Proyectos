package com.proyecto.demo.Models.DAO;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.Proyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProyectoDaoImp implements IProyectoDao {

    @PersistenceContext
    private EntityManager em;

    // Método para listar todos los proyectos
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Proyecto> findAll() {
        return em.createQuery("from Proyecto").getResultList();
    }

    // Método Guardado y Actualización de Proyecto
    @Override
    @Transactional
    public void save(Proyecto proyecto) {
        if (proyecto.getId() != null && proyecto.getId() > 0) {
            em.merge(proyecto);   // Actualiza el proyecto existente
        } else {
            em.persist(proyecto); // Guarda un nuevo proyecto
        }
    }

    // Método Consultar Proyecto por ID
    @Override
    @Transactional(readOnly = true)
    public Proyecto findOne(Long id) {
        return em.find(Proyecto.class, id);
    }

    // Método Eliminar Proyecto por ID
    @Override
    @Transactional
    public void delete(Long id) {
        em.remove(findOne(id));
    }
}