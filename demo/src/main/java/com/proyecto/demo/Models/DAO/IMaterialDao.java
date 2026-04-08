package com.proyecto.demo.Models.DAO;

import java.util.List;
import com.proyecto.demo.Models.Entity.Material;

public interface IMaterialDao {

    public List<Material> findAll();
    public void save(Material material);
    public Material findOne(Long id);
    public void delete(Long id);
    public Integer stockUsado(Long idMaterial);
}