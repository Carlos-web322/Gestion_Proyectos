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
import com.proyecto.demo.Models.DAO.IDetalleCorteDao;

@Controller
@RequestMapping("/cortes")
public class CorteController {

    @Autowired
    private ICorteDao corteDao;

    @Autowired
    private IPresupuestoDao presupuestoDao;

    @Autowired
    private IDetalleCorteDao detalleCortedao;

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
        // Validar presupuesto manualmente antes de revisar errores
        if (corte.getPresupuesto() == null || corte.getPresupuesto().getId() == null) {
            result.rejectValue("presupuesto", "error.corte", "Debe seleccionar un presupuesto");
        }

        if (result.hasErrors()) {
            model.addAttribute("titulo", corte.getId() != null ? "Editar Corte" : "Nuevo Corte");
            model.addAttribute("presupuestos", presupuestoDao.findAll());
            return "cortes/form";
        }

        corte.setPresupuesto(presupuestoDao.findOne(corte.getPresupuesto().getId()));

        // Si es edición recalcular el totalCorte
        if (corte.getId() != null && corte.getId() > 0) {
            Double totalCorte = detalleCortedao.sumSubtotalByCorte(corte.getId());
            corte.setTotalCorte(totalCorte);
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
        // Primero eliminar detalles corte asociados
        detalleCortedao.deleteByCorte(id);
        // Luego eliminar el corte
        corteDao.delete(id);
        return "redirect:/cortes/listar";
    }

    @GetMapping("/presupuesto/{idPresupuesto}")
    public String listarPorPresupuesto(@PathVariable Long idPresupuesto, Model model) {
        model.addAttribute("titulo", "Cortes del Presupuesto");
        model.addAttribute("corte", corteDao.findByPresupuesto(idPresupuesto));
        return "cortes/listar";
    }

    // Filtrar solo corte necesario
    @GetMapping("/form/presupuesto/{idPresupuesto}")
    public String crearParaPresupuesto(@PathVariable Long idPresupuesto, Model model) {
        model.addAttribute("titulo", "Nuevo Corte");
        Corte corte = new Corte();
        corte.setPresupuesto(presupuestoDao.findOne(idPresupuesto));
        model.addAttribute("corte", corte);
        model.addAttribute("presupuestos", presupuestoDao.findAll());
        model.addAttribute("idPresupuestoFijo", idPresupuesto);
        return "cortes/form";
    }
}