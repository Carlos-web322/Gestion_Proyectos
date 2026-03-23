package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.Entity.Presupuesto;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Presupuestos");
        model.addAttribute("presupuesto", presupuestoDao.findAll());
        return "presupuestos/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Nuevo Presupuesto");
        model.addAttribute("presupuesto", new Presupuesto());
        return "presupuestos/form";
    }

    @PostMapping("/form")
    public String guardar(Presupuesto presupuesto) {
        presupuestoDao.save(presupuesto);
        return "redirect:/presupuestos/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Presupuesto");
        model.addAttribute("presupuesto", presupuestoDao.findOne(id));
        return "presupuestos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        presupuestoDao.delete(id);
        return "redirect:/presupuestos/listar";
    }
}