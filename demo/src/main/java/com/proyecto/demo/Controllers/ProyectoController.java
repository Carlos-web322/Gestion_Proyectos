package com.proyecto.demo.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyecto.demo.Models.DAO.IProyectoDao;
import com.proyecto.demo.Models.DAO.IDetallePresupuestoDao;
import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.DAO.ICorteDao;
import com.proyecto.demo.Models.DAO.IDetalleCorteDao;
import com.proyecto.demo.Models.Entity.Proyecto;
import com.proyecto.demo.Models.Entity.Corte;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class ProyectoController {

    @Autowired
    private IProyectoDao proyectoDao;

    @Autowired
    private IDetallePresupuestoDao detalleDao;

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @Autowired
    private ICorteDao corteDao;

    @Autowired
    private IDetalleCorteDao detalleCortedao;

    // Método Listar Proyectos
    @GetMapping("/listar")
    public String Listar(Model model) {
        List<Proyecto> proyectos = proyectoDao.findAll();

        Map<Long, Double> totales = new HashMap<>();
        for (Proyecto p : proyectos) {
            totales.put(p.getId(), detalleDao.sumSubtotalByProyecto(p.getId()));
        }

        model.addAttribute("titulo", "Listado de Proyectos");
        model.addAttribute("proyecto", proyectos);
        model.addAttribute("totales", totales);
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
    public String Guardar(@Valid Proyecto proyecto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("proyecto", proyecto);
            model.addAttribute("titulo", proyecto.getId() != null ? "Editar Proyecto" : "Nuevo Proyecto");
            return "form";
        }
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
        Proyecto proyecto = proyectoDao.findOne(id);

        if (proyecto.getPresupuesto() != null) {
            Long idPresupuesto = proyecto.getPresupuesto().getId();

            // 1. Eliminar detalles corte
            List<Corte> cortes = corteDao.findByPresupuesto(idPresupuesto);
            for (Corte corte : cortes) {
                detalleCortedao.deleteByCorte(corte.getId());
            }

            // 2. Romper relación corte-presupuesto con query directa
            corteDao.deleteByPresupuesto(idPresupuesto);

            // 3. Eliminar detalles presupuesto
            detalleDao.deleteByPresupuesto(idPresupuesto);

            // 4. Romper relación presupuesto-proyecto con query directa
            presupuestoDao.deleteByProyecto(id);
        }

    // 5. Eliminar proyecto
    proyectoDao.delete(id);
    return "redirect:/listar";
}
}