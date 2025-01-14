package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.mappers;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity.UsuarioEntity;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;

public class UsuarioMapper {

    public static UsuarioEntity converterParaUsuarioEntity(Usuario usuario) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        if(usuario.getId() != null){
            usuarioEntity.setId(usuario.getId());
        }

        usuarioEntity.setEmail(usuario.getEmail());
        usuarioEntity.setNome(usuario.getNome());
        usuarioEntity.setSenha(usuario.getSenha());
        usuarioEntity.setPapel(usuario.getPapel());
        usuarioEntity.setCpf(usuario.getCpf());

        return usuarioEntity;
    }

    public static Usuario converterParaUsuario(UsuarioEntity usuarioEntity){
        return new Usuario(usuarioEntity.getId(), usuarioEntity.getEmail(), usuarioEntity.getNome(), usuarioEntity.getSenha(), usuarioEntity.getCpf() ,usuarioEntity.getPapel());
    }
}
