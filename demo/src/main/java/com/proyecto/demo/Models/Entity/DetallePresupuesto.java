package com.proyecto.demo.Models.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_presupuesto")
public class DetallePresupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @ManyToOne
   @JoinColumn(name = "id_presupuesto")
   private Presupuesto presupuesto;

   @ManyToOne
   @JoinColumn(name = "id_material")
   private Material material;
 

    private Integer stock;

    private Double subtotal;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;

    }

    public Material getMaterial() {
        return material ;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}