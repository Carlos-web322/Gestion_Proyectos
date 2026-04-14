package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IDetalleCorteDao;
import com.proyecto.demo.Models.DAO.ICorteDao;
import com.proyecto.demo.Models.DAO.IDetallePresupuestoDao;
import com.proyecto.demo.Models.Entity.Corte;
import com.proyecto.demo.Models.Entity.DetalleCorte;
import com.proyecto.demo.Models.Entity.DetallePresupuesto;

@Controller
@RequestMapping("/detalleCorte")
public class DetalleCorteController {

    @Autowired
    private IDetalleCorteDao detalleCortedao;

    @Autowired
    private ICorteDao corteDao;

    @Autowired
    private IDetallePresupuestoDao detallePresupuestoDao;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Detalle Corte");
        model.addAttribute("detalleCorte", detalleCortedao.findAll());
        return "detalleCorte/listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("titulo", "Nuevo Detalle Corte");
        model.addAttribute("detalleCorte", new DetalleCorte());
        model.addAttribute("cortes", corteDao.findAll());
        model.addAttribute("detallesPresupuesto", detallePresupuestoDao.findAll());
        return "detalleCorte/form";
    }

    @PostMapping("/form")
    public String guardar(DetalleCorte detalleCorte, Model model) {

        // Validar corte
        if (detalleCorte.getCorte() == null || detalleCorte.getCorte().getId() == null) {
            model.addAttribute("error", "Debe seleccionar un corte");
            model.addAttribute("detalleCorte", detalleCorte);
            model.addAttribute("cortes", corteDao.findAll());
            model.addAttribute("detallesPresupuesto", detallePresupuestoDao.findAll());
            return "detalleCorte/form";
        }

        // Validar detalle presupuesto
        if (detalleCorte.getDetallePresupuesto() == null || detalleCorte.getDetallePresupuesto().getId() == null) {
            model.addAttribute("error", "Debe seleccionar un material del presupuesto");
            model.addAttribute("detalleCorte", detalleCorte);
            model.addAttribute("cortes", corteDao.findAll());
            model.addAttribute("detallesPresupuesto", detallePresupuestoDao.findAll());
            return "detalleCorte/form";
        }

        // Validar cantidad
        if (detalleCorte.getCantidad() == null || detalleCorte.getCantidad() < 1) {
            model.addAttribute("error", "La cantidad debe ser mayor a 0");
            model.addAttribute("detalleCorte", detalleCorte);
            model.addAttribute("cortes", corteDao.findAll());
            model.addAttribute("detallesPresupuesto", detallePresupuestoDao.findAll());
            return "detalleCorte/form";
        }

        // Buscar objetos completos
        Corte corte = corteDao.findOne(detalleCorte.getCorte().getId());
        DetallePresupuesto dp = detallePresupuestoDao.findOne(detalleCorte.getDetallePresupuesto().getId());

        // Calcular subtotal
        Double subtotal = dp.getMaterial().getValorUnitario() * detalleCorte.getCantidad();
        detalleCorte.setSubtotal(subtotal);

        // Validar que no supere el total del presupuesto
        Double totalCortesPresupuesto = detalleCortedao.sumSubtotalByPresupuesto(dp.getPresupuesto().getId());
        Double totalPresupuesto = dp.getPresupuesto().getTotal();

        if ((totalCortesPresupuesto + subtotal) > totalPresupuesto) {
            Double disponible = totalPresupuesto - totalCortesPresupuesto;
            model.addAttribute("error", "El corte superaría el presupuesto. Solo quedan $ " +
                    String.format("%,.0f", disponible) + " disponibles de $ " +
                    String.format("%,.0f", totalPresupuesto));
            model.addAttribute("detalleCorte", detalleCorte);
            model.addAttribute("cortes", corteDao.findAll());
            model.addAttribute("detallesPresupuesto", detallePresupuestoDao.findAll());
            return "detalleCorte/form";
        }

        detalleCorte.setCorte(corte);
        detalleCorte.setDetallePresupuesto(dp);
        detalleCortedao.save(detalleCorte);

        // Actualizar total del corte
        Double nuevoTotal = detalleCortedao.sumSubtotalByCorte(corte.getId());
        corte.setTotalCorte(nuevoTotal);
        corteDao.save(corte);
        
        return "redirect:/detalleCorte/corte/" + corte.getId();
        //return "redirect:/detalleCorte/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("titulo", "Editar Detalle Corte");
        DetalleCorte dc = detalleCortedao.findOne(id);
        model.addAttribute("detalleCorte", dc);
        model.addAttribute("cortes", corteDao.findByPresupuesto(dc.getCorte().getPresupuesto().getId()));
        model.addAttribute("detallesPresupuesto",
                detallePresupuestoDao.findByPresupuesto(dc.getCorte().getPresupuesto().getId()));
        return "detalleCorte/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        detalleCortedao.delete(id);
        return "redirect:/detalleCorte/listar";
    }

    @GetMapping("/corte/{idCorte}")
    public String listarPorCorte(@PathVariable Long idCorte, Model model) {
        model.addAttribute("titulo", "Detalles del Corte");
        model.addAttribute("detalleCorte", detalleCortedao.findByCorte(idCorte));
        return "detalleCorte/listar";
    }

    // Filtro por corte con formulario prellenado
    @GetMapping("/form/corte/{idCorte}")
    public String crearParaCorte(@PathVariable Long idCorte, Model model) {
        model.addAttribute("titulo", "Nuevo Detalle Corte");

        Corte corte = corteDao.findOne(idCorte);
        DetalleCorte detalleCorte = new DetalleCorte();
        detalleCorte.setCorte(corte);

        model.addAttribute("detalleCorte", detalleCorte);
        model.addAttribute("cortes", corteDao.findByPresupuesto(corte.getPresupuesto().getId()));
        model.addAttribute("detallesPresupuesto",
                detallePresupuestoDao.findByPresupuesto(corte.getPresupuesto().getId()));
        model.addAttribute("idCorteFijo", idCorte); // ← nombre correcto
        return "detalleCorte/form";
    }
}