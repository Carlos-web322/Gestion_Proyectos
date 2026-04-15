package com.proyecto.demo.Models.DAO;

import com.proyecto.demo.Models.Entity.Usuario;

public interface IUsuarioDao {
    public Usuario findByUsername(String username);
    public void save(Usuario usuario);
}