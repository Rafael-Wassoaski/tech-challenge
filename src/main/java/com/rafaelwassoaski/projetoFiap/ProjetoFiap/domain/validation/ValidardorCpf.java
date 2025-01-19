package com.rafaelwassoaski.projetoFiap.ProjetoFiap.domain.validation;

public class ValidardorCpf {
    public boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf == null || cpf.isEmpty()) {
           return false;
        }

        if (cpf.length() != 11 || cpfEstaNoPadrao(cpf)) {
            return false;
        }

        int digito1 = calcularDigito(cpf, 10);
        int digito2 = calcularDigito(cpf, 11);

        return (digito1 == Character.getNumericValue(cpf.charAt(9)) &&
                digito2 == Character.getNumericValue(cpf.charAt(10)));

    }

    private int calcularDigito(String cpf, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < pesoInicial - 1; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (pesoInicial - i);
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    private boolean cpfEstaNoPadrao(String cpf) {
        return cpf.matches("(\\d)\\1{10}");
    }
}
