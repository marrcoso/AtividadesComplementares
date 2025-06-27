package ifsc.model;

import ifsc.strategy.Validacao;

public class AtividadeComplementar{
    private String descricao;
    private Validacao estrategiaValidacao; // Padrão Strategy para validação!
    private String documentacaoExigida;


    public AtividadeComplementar(String descricao, Validacao estrategiaValidacao, String documentacaoExigida) {
        this.descricao = descricao;
        this.estrategiaValidacao = estrategiaValidacao;
        this.documentacaoExigida = documentacaoExigida;
    }


    public String getDescricao() {
        return descricao;
    }


    public Validacao getEstrategiaValidacao() {
        return estrategiaValidacao;
    }


    public String getDocumentacaoExigida() {
        return documentacaoExigida;
    }

    

}