package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IDetallePresupuestoDao;
import com.proyecto.demo.Models.Entity.DetallePresupuesto;

@Controller
@RequestMapping("/detalles")
public class DetallePresupuestoController {

    @Autowired
    private IDetallePresupuestoDao detalleDao;

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
        return "detalles/form";
    }

    @PostMapping("/form")
    public String guardar(DetallePresupuesto detalle) {
        detalleDao.save(detalle);
        return "redirect:/detalles/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Detalle");
        model.addAttribute("detalle", detalleDao.findOne(id));
        return "detalles/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        detalleDao.delete(id);
        return "redirect:/detalles/listar";
    }
}