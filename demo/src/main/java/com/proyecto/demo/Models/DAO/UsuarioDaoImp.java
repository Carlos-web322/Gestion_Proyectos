package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuarioDaoImp implements IUsuarioDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public Usuario findByUsername(String username) {
        @SuppressWarnings("unchecked")
        List<Usuario> lista = em.createQuery("from Usuario u where u.username = :username")
                                .setParameter("username", username)
                                .getResultList();
        return lista.isEmpty() ? null : lista.get(0);
    }

    @Transactional
    @Override
    public void save(Usuario usuario) {
        if (usuario.getId() != null && usuario.getId() > 0) {
            em.merge(usuario);
        } else {
            em.persist(usuario);
        }
    }
}