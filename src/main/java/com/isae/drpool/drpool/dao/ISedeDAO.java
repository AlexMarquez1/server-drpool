package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Sede;

@Repository
public interface ISedeDAO extends JpaRepository<Sede, Integer>{

}
