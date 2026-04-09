package com.proyecto.demo.Models.DAO;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.demo.Models.Entity.DetalleCorte;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DetalleCorteDaoImp implements IDetalleCorteDao {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<DetalleCorte> findAll() {
        return em.createQuery("from DetalleCorte").getResultList();
    }

    @Transactional
    @Override
    public void save(DetalleCorte detalleCorte) {
        if (detalleCorte.getId() != null && detalleCorte.getId() > 0) {
            em.merge(detalleCorte);
        } else {
            em.persist(detalleCorte);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DetalleCorte findOne(Long id) {
        return em.find(DetalleCorte.class, id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        em.remove(findOne(id));
    }

    @Transactional(readOnly = true)
    @Override
    public Double sumSubtotalByCorte(Long idCorte) {
        String query = "select sum(d.subtotal) from DetalleCorte d where d.corte.id = :idCorte";
        Double resultado = (Double) em.createQuery(query)
                                      .setParameter("idCorte", idCorte)
                                      .getSingleResult();
        return resultado != null ? resultado : 0.0;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<DetalleCorte> findByCorte(Long idCorte) {
        return em.createQuery("from DetalleCorte d where d.corte.id = :idCorte")
                 .setParameter("idCorte", idCorte)
                 .getResultList();
    }

    @Transactional(readOnly = true)
@Override
public Double sumSubtotalByPresupuesto(Long idPresupuesto) {
    String query = "select coalesce(sum(dc.subtotal), 0) from DetalleCorte dc " +
                   "where dc.corte.presupuesto.id = :idPresupuesto";
    Number resultado = (Number) em.createQuery(query)
                                  .setParameter("idPresupuesto", idPresupuesto)
                                  .getSingleResult();
    return resultado.doubleValue();
}
}