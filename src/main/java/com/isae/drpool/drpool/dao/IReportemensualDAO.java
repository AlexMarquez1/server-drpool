package com.isae.drpool.drpool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isae.drpool.drpool.entity.Reportemensual;

@Repository
public interface IReportemensualDAO extends JpaRepository<Reportemensual, Integer>{
	

}
