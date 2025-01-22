package com.mballem.demo_park_api.service;


import com.mballem.demo_park_api.entity.Usuario;
import com.mballem.demo_park_api.exception.EntityNotFoundException;
import com.mballem.demo_park_api.exception.UsernameUniqueViolationException;
import com.mballem.demo_park_api.repository.UsuarioRepostory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepostory usuarioRepostory;

    @Transactional
    public Usuario salvar(Usuario usuario){
        try{
            return usuarioRepostory.save(usuario);
        }  catch (org.springframework.dao.DataIntegrityViolationException ex) {
           throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }
   }
    @Transactional
    public Usuario buscarPorId(Long id){
        return usuarioRepostory.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado.", id))
        );
    }
    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)){
            throw new RuntimeException("A nova senha não confere com a confirmação de senha.");
        }
        Usuario user = buscarPorId(id);
        if(!user.getPassword().equals(senhaAtual)){
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(novaSenha);
        return user;
    }

    @Transactional
    public List<Usuario> buscarTodos() {
        return usuarioRepostory.findAll();
    }
}
