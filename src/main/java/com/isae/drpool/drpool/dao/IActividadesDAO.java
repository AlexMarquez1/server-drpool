package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Actividades;

@Repository
public interface IActividadesDAO extends JpaRepository<Actividades, Integer>{

}
