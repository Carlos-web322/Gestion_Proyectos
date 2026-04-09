package com.proyecto.demo.Models.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
 
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proyecto", foreignKey = @ForeignKey(name = "fk_presupuesto_proyecto"))
    private Proyecto proyecto;


    private Double total;

    @OneToMany(mappedBy = "presupuesto")
    private List<DetallePresupuesto> detalles;

    @OneToMany(mappedBy = "presupuesto")
    private List<Corte> cortes;

    public List<Corte> getCortes() {
        return cortes;
    }

    public void setCortes(List<Corte> cortes) {
        this.cortes = cortes;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public List<DetallePresupuesto> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetallePresupuesto> detalles) {
        this.detalles = detalles;
    }

}