package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation;

public class ValidardorEmail {
    private String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public boolean validarEmail(String email){
        return !emailEhVazio(email) && emailEhValido(email);
    }

    private boolean emailEhVazio(String email){
        return email == null || email.isEmpty();
    }

    private boolean emailEhValido(String email){
        return email.matches(emailRegex);
    }
}
