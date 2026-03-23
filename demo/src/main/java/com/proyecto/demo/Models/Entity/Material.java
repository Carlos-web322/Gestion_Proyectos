package com.proyecto.demo.Models.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(name = "valor_unitario")
    private Double valorUnitario;
    @OneToMany(mappedBy = "material")
    private List<DetallePresupuesto> detalles;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public List<DetallePresupuesto> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetallePresupuesto> detalles) {
        this.detalles = detalles;
    }
}