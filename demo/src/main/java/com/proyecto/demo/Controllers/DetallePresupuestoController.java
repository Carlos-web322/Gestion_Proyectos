package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IDetallePresupuestoDao;
import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.Entity.DetallePresupuesto;
import com.proyecto.demo.Models.Entity.Material;
import com.proyecto.demo.Models.Entity.Presupuesto;
import com.proyecto.demo.Models.DAO.IMaterialDao;


@Controller
@RequestMapping("/detalles")
public class DetallePresupuestoController {

    @Autowired
    private IDetallePresupuestoDao detalleDao;

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @Autowired
    private IMaterialDao materialDao;
    
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Detalles");
        model.addAttribute("detalle", detalleDao.findAll());
        return "detalles/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Nuevo Detalle");
        model.addAttribute("detalle", new DetallePresupuesto());
        model.addAttribute("presupuestos", presupuestoDao.findAll());
        model.addAttribute("materiales", materialDao.findAll());
        return "detalles/form";
    }

    @PostMapping("/form")
    public String guardar(DetallePresupuesto detalle) {
        
         if (detalle.getPresupuesto() != null && detalle.getPresupuesto().getId() != null) {
        detalle.setPresupuesto(presupuestoDao.findOne(detalle.getPresupuesto().getId()));
        }

        if (detalle.getMaterial() != null && detalle.getMaterial().getId() != null) {
        Material material = materialDao.findOne(detalle.getMaterial().getId());
        detalle.setMaterial(material);
        detalle.setSubtotal(material.getValorUnitario() * detalle.getStock());
        }

        detalleDao.save(detalle);
    // Recalcular y actualizar el total del presupuesto
        Presupuesto presupuesto = detalle.getPresupuesto();
        Double nuevoTotal = detalleDao.sumSubtotalByPresupuesto(presupuesto.getId());
        presupuesto.setTotal(nuevoTotal);
        presupuestoDao.save(presupuesto);

        return "redirect:/detalles/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Detalle");
        model.addAttribute("detalle", detalleDao.findOne(id));
        model.addAttribute("presupuestos", presupuestoDao.findAll());
        model.addAttribute("materiales", materialDao.findAll());
        return "detalles/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        detalleDao.delete(id);
        return "redirect:/detalles/listar";
    }
}