package ifsc.factory;

import java.util.Arrays;
import java.util.List;

import ifsc.model.AtividadeComplementar;
import ifsc.model.Modalidade;
import ifsc.strategy.ValidacaoHoraDeclaradaComLimite;
import ifsc.strategy.ValidacaoPorMultiplicador;
import ifsc.strategy.ValidacaoPadrao;
import ifsc.strategy.ValidacaoHoraFixa;

public class ModalidadeFactory {
    public static Modalidade criarModalidade(int tipo) {
        switch (tipo) {
            case 1:
                return criarModalidadeEnsino();
            case 2:
                return criarModalidadePesquisa();
            case 3:
                return criarModalidadeExtensao();
            default:
                return null;
        }
    }

    private static Modalidade criarModalidadeEnsino() {
        List<AtividadeComplementar> atividades = Arrays.asList(
            // Atividade com limite de horas
            new AtividadeComplementar("Visita técnica", 
            new ValidacaoHoraDeclaradaComLimite(8), 
            "Declaração de participação"),
            
            // Atividade com valor padrão
            new AtividadeComplementar("Participação em palestra (sem horas no certificado)", 
            new ValidacaoPadrao(4), 
            "Certificado de participação")
        );
        return new Modalidade("Ensino", atividades, 0.40);
    }
    
    private static Modalidade criarModalidadePesquisa() {
        List<AtividadeComplementar> atividades = Arrays.asList(
            // Atividade com valor fixo
            new AtividadeComplementar("Publicação de artigo em periódico", 
            new ValidacaoHoraFixa(15), 
            "Cópia do artigo"),
            new AtividadeComplementar("Apresentação de trabalho em evento", 
            new ValidacaoHoraFixa(10), 
            "Certificado de apresentação")
        );
        return new Modalidade("Pesquisa e Inovação", atividades, 0.40);
    }
    
    private static Modalidade criarModalidadeExtensao() {
        List<AtividadeComplementar> atividades = Arrays.asList(
            new AtividadeComplementar("Estágio não obrigatório na área", 
            new ValidacaoPorMultiplicador(25, "mês"), 
            "Contrato de estágio")
        );
        return new Modalidade("Extensão", atividades, 0.40);
    }
}