package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.demo.Models.DAO.IProyectoDao;
import com.proyecto.demo.Models.Entity.Proyecto;

@Controller
@RequestMapping("/")
public class ProyectoController {

    @Autowired
    private IProyectoDao proyectoDao;

    // Método Listar Proyectos
    @GetMapping("/listar")
    public String Listar(Model model) {
        model.addAttribute("titulo", "Listado de Proyectos");
        model.addAttribute("proyecto", proyectoDao.findAll());
        return "listar";
    }

    // Método Formulario Crear Proyecto
    @GetMapping("/form")
    public String Crear(Model model) {
        Proyecto proyecto = new Proyecto();
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("titulo", "Formulario de Proyecto");
        return "form";
    }

    // Método Guardar Proyecto
    @PostMapping("/form")
    public String Guardar(Proyecto proyecto) {
        proyectoDao.save(proyecto);
        return "redirect:/listar";
    }

    // Método Editar Proyecto por ID
    @GetMapping("/form/{id}")
    public String Editar(@PathVariable(value = "id") long id, Model model) {
        Proyecto proyecto = proyectoDao.findOne(id);
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("titulo", "Editar Proyecto");
        return "form";
    }

    // Método Eliminar Proyecto por ID
    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable(value = "id") long id) {
        proyectoDao.delete(id);
        return "redirect:/listar";
    }
}