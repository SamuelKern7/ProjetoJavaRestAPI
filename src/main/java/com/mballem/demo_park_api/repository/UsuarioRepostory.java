package com.mballem.demo_park_api.repository;

import com.mballem.demo_park_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepostory extends JpaRepository<Usuario, Long> {
    
}
