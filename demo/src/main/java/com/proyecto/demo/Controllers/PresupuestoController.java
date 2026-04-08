package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.Entity.Presupuesto;
import com.proyecto.demo.Models.Entity.Proyecto;
import com.proyecto.demo.Models.DAO.IProyectoDao;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @Autowired  
    private IProyectoDao proyectoDao;

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
        model.addAttribute("proyectos", proyectoDao.findAll());
        return "presupuestos/form";
    }

    @PostMapping("/form")
    public String guardar(Presupuesto presupuesto, Model model) {

        // Validar que haya seleccionado un proyecto
        if (presupuesto.getProyecto() == null || presupuesto.getProyecto().getId() == null) {
            model.addAttribute("error", "Debe seleccionar un proyecto para crear el presupuesto");
            model.addAttribute("presupuesto", presupuesto);
            model.addAttribute("proyectos", proyectoDao.findAll());
            return "presupuestos/form";
        }
        if (presupuesto.getProyecto() != null && presupuesto.getProyecto().getId() != null) {
            Proyecto proyecto = proyectoDao.findOne(presupuesto.getProyecto().getId());
            presupuesto.setProyecto(proyecto);

            // Validar que el proyecto no tenga ya un presupuesto (solo al crear)
            if (presupuesto.getId() == null || presupuesto.getId() == 0) {
                if (proyecto.getPresupuesto() != null) {
                    model.addAttribute("error", "Este proyecto ya tiene un presupuesto asignado");
                    model.addAttribute("presupuesto", presupuesto);
                    model.addAttribute("proyectos", proyectoDao.findAll());
                    return "presupuestos/form";
                }
            }
        }
        presupuestoDao.save(presupuesto);
        return "redirect:/presupuestos/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Presupuesto");
        model.addAttribute("presupuesto", presupuestoDao.findOne(id));
        model.addAttribute("proyectos", proyectoDao.findAll());
        return "presupuestos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        presupuestoDao.delete(id);
        return "redirect:/presupuestos/listar";
    }
    
    @GetMapping("/proyecto/{idProyecto}")
    public String listarPorProyecto(@PathVariable Long idProyecto, Model model) {
    model.addAttribute("titulo", "Presupuesto del Proyecto");
    model.addAttribute("presupuesto", presupuestoDao.findByProyecto(idProyecto));
    return "presupuestos/listar";
}
}