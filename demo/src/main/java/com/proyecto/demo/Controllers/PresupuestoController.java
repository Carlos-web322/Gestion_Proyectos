package com.proyecto.demo.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.DAO.IProyectoDao;
import com.proyecto.demo.Models.DAO.IDetalleCorteDao;
import com.proyecto.demo.Models.Entity.Presupuesto;
import com.proyecto.demo.Models.Entity.Proyecto;

@Controller
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @Autowired
    private IProyectoDao proyectoDao;

    @Autowired
    private IDetalleCorteDao detalleCortedao;

    @GetMapping("/listar")
    public String listar(Model model) {
        List<Presupuesto> presupuestos = presupuestoDao.findAll();

        Map<Long, Double> totalCorteObra = new HashMap<>();
        Map<Long, Double> presupuestoDisponible = new HashMap<>();

        for (Presupuesto p : presupuestos) {
            Double totalCorte = detalleCortedao.sumSubtotalByPresupuesto(p.getId());
            Double totalPresupuesto = p.getTotal() != null ? p.getTotal() : 0.0;
            totalCorteObra.put(p.getId(), totalCorte);
            presupuestoDisponible.put(p.getId(), totalPresupuesto - totalCorte);
        }

        model.addAttribute("titulo", "Listado de Presupuestos");
        model.addAttribute("presupuesto", presupuestos);
        model.addAttribute("totalCorteObra", totalCorteObra);
        model.addAttribute("presupuestoDisponible", presupuestoDisponible);
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

        if (presupuesto.getProyecto() == null || presupuesto.getProyecto().getId() == null) {
            model.addAttribute("error", "Debe seleccionar un proyecto para crear el presupuesto");
            model.addAttribute("presupuesto", presupuesto);
            model.addAttribute("proyectos", proyectoDao.findAll());
            return "presupuestos/form";
        }

        if (presupuesto.getProyecto() != null && presupuesto.getProyecto().getId() != null) {
            Proyecto proyecto = proyectoDao.findOne(presupuesto.getProyecto().getId());
            presupuesto.setProyecto(proyecto);

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