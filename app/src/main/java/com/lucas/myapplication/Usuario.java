package com.lucas.myapplication;

public class Usuario {
    private String nome;
    private int idade;
    private boolean diabetico;
    private Objetivo objetivo;
    private Sexo sexo;

    public Usuario(String nome, int idade, boolean diabetico, Objetivo objetivo, Sexo sexo) {
        this.nome = nome;
        this.idade = idade;
        this.diabetico = diabetico;
        this.objetivo = objetivo;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isDiabetico() {
        return diabetico;
    }

    public void setDiabetico(boolean diabetico) {
        this.diabetico = diabetico;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nome + "\n" +
                idade + "\n" +
                diabetico + "\n" +
                objetivo + "\n" +
                sexo;
    }
}
