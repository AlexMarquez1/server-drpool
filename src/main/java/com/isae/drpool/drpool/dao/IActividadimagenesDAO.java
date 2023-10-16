package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Actividadimagenes;

@Repository
public interface IActividadimagenesDAO extends JpaRepository<Actividadimagenes, Integer>{

}
