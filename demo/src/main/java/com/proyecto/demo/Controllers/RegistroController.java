package com.proyecto.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.proyecto.demo.Models.DAO.IUsuarioDao;
import com.proyecto.demo.Models.Entity.Usuario;

@Controller
public class RegistroController {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public String registrar(@RequestParam String username,
                            @RequestParam String password) {

        // Verificar si el usuario ya existe
        if (usuarioDao.findByUsername(username) != null) {
            return "redirect:/login?errorRegistro";
        }

        // Crear y guardar el usuario con contraseña encriptada
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRol("ADMIN");
        usuarioDao.save(usuario);

        return "redirect:/login?registroOk";
    }
}