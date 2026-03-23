package com.proyecto.demo.Models.DAO;
import java.util.List;
import com.proyecto.demo.Models.Entity.Proyecto;


public interface IProyectoDao {

    public List<Proyecto> findAll();

    public void save(Proyecto proyecto);

    public Proyecto findOne(Long id);

    public void delete(Long id);

}
