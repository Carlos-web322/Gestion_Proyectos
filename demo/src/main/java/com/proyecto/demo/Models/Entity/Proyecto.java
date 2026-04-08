package com.proyecto.demo.Models.Entity;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s0-9]+$", message = "El nombre solo puede contener letras, números y espacios")
    private String nombre;
 
    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(name = "id_cliente")
    private Long idCliente;
    
    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;

    @OneToOne(mappedBy = "proyecto")
    private Presupuesto presupuesto;

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

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }
    
    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

}