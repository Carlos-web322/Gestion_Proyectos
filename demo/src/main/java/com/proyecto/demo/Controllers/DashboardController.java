package com.proyecto.demo.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyecto.demo.Models.DAO.IProyectoDao;
import com.proyecto.demo.Models.DAO.IMaterialDao;
import com.proyecto.demo.Models.DAO.IPresupuestoDao;
import com.proyecto.demo.Models.DAO.ICorteDao;
import com.proyecto.demo.Models.DAO.IDetalleCorteDao;
import com.proyecto.demo.Models.Entity.Proyecto;


@Controller
public class DashboardController {

    @Autowired private IProyectoDao proyectoDao;
    @Autowired private IMaterialDao materialDao;
    @Autowired private IPresupuestoDao presupuestoDao;
    @Autowired private ICorteDao corteDao;
    @Autowired private IDetalleCorteDao detalleCortedao;    

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalProyectos", proyectoDao.findAll().size());
        model.addAttribute("totalMateriales", materialDao.findAll().size());
        model.addAttribute("totalPresupuestos", presupuestoDao.findAll().size());
        model.addAttribute("totalCortes", corteDao.findAll().size());

        // Suma total de todos los presupuestos
        Double totalPresupuestado = presupuestoDao.findAll().stream()
            .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
            .sum();
        model.addAttribute("totalPresupuestado", totalPresupuestado);

        // Suma total ejecutada en cortes
        Double totalEjecutado = presupuestoDao.findAll().stream()
            .mapToDouble(p -> detalleCortedao.sumSubtotalByPresupuesto(p.getId()))
            .sum();
        model.addAttribute("totalEjecutado", totalEjecutado);

        // Últimos 3 proyectos
        List<Proyecto> proyectos = proyectoDao.findAll();
        int size = proyectos.size();
        model.addAttribute("proyectos", size > 3 ? proyectos.subList(size - 3, size) : proyectos);

        return "dashboard";
    }
}