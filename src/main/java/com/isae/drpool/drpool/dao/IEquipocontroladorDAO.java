package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Equipocontrolador;

@Repository
public interface IEquipocontroladorDAO extends JpaRepository<Equipocontrolador, Integer>{}