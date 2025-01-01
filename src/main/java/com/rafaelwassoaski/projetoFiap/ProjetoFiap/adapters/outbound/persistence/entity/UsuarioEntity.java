package com.rafaelwassoaski.projetoFiap.ProjetoFiap.adapters.outbound.persistence.entity;

import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.enums.Papel;
import com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String email;
    @Column
    private String senha;
    @Enumerated(EnumType.STRING)
    @Column
    private Papel papel;

    public UsuarioEntity(int id, String email, String senha, Papel papel) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public UsuarioEntity() {}

    public UsuarioEntity(Usuario usuario) {
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.papel = usuario.getPapel();
    }

    public Usuario converterParaUsuario(){
           return new Usuario(email, senha, papel);
    }
}
