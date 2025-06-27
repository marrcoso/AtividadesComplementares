package ifsc.model;

import java.util.List;

public class Modalidade {
    private String nome;
    private List<AtividadeComplementar> atividadesDisponiveis;
    private double percentualLimite; // Ex: 0.40 para 40%

    public Modalidade(String nome, List<AtividadeComplementar> atividadesDisponiveis, double percentualLimite) {
        this.nome = nome;
        this.atividadesDisponiveis = atividadesDisponiveis;
        this.percentualLimite = percentualLimite;
    }

    public String getNome() {
        return nome;
    }

    public List<AtividadeComplementar> getAtividadesDisponiveis() {
        return atividadesDisponiveis;
    }

    public double getPercentualLimite() {
        return percentualLimite;
    }
}