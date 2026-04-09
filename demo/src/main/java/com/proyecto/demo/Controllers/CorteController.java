package com.proyecto.demo.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.ICorteDao;
import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.Entity.Corte;

@Controller
@RequestMapping("/cortes")
public class CorteController {

    @Autowired
    private ICorteDao corteDao;

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Cortes");
        model.addAttribute("corte", corteDao.findAll());
        return "cortes/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Nuevo Corte");
        model.addAttribute("corte", new Corte());
        model.addAttribute("presupuestos", presupuestoDao.findAll());
        return "cortes/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Corte corte, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", corte.getId() != null ? "Editar Corte" : "Nuevo Corte");
            model.addAttribute("presupuestos", presupuestoDao.findAll());
            return "cortes/form";
        }
        if (corte.getPresupuesto() != null && corte.getPresupuesto().getId() != null) {
            corte.setPresupuesto(presupuestoDao.findOne(corte.getPresupuesto().getId()));
        }
        corteDao.save(corte);
        return "redirect:/cortes/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Corte");
        model.addAttribute("corte", corteDao.findOne(id));
        model.addAttribute("presupuestos", presupuestoDao.findAll());
        return "cortes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        corteDao.delete(id);
        return "redirect:/cortes/listar";
    }
}