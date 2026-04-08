package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IMaterialDao;
import com.proyecto.demo.Models.Entity.Material;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/materiales")
public class MaterialController {

    @Autowired
    private IMaterialDao materialDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Materiales");
        model.addAttribute("material", materialDao.findAll());
        return "materiales/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Nuevo Material");
        model.addAttribute("material", new Material());
        return "materiales/form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Material material, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", material.getId() != null ? "Editar Material" : "Nuevo Material");
            return "materiales/form";
        }   
        materialDao.save(material);
        return "redirect:/materiales/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Material");
        model.addAttribute("material", materialDao.findOne(id));
        return "materiales/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        materialDao.delete(id);
        return "redirect:/materiales/listar";
    }
}