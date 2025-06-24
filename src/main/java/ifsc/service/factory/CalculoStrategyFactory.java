package ifsc.service.factory;

import ifsc.model.Modalidade;
import ifsc.service.strategy.CalculoModalidadesStrategy;
import ifsc.service.CalculoOutrasStrategy;
import ifsc.service.strategy.CalculoStrategy;

public class CalculoStrategyFactory {
    // Implementação do padrão Factory Method para obter a estratégia correta
    public static CalculoStrategy getStrategy(Modalidade modalidade) {
        return switch (modalidade.nome()) {
            case "Ensino" -> new CalculoModalidadesStrategy();
            case "Pesquisa e Inovação" -> new CalculoPesquisaStrategy();
            case "Extensão" -> new CalculoExtensaoStrategy();
            case "Outras" -> new CalculoOutrasStrategy();
            default -> throw new IllegalArgumentException("Modalidade desconhecida: " + modalidade.nome());
        };
    }
}