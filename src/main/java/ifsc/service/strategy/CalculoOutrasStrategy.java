package ifsc.service.strategy;

import ifsc.model.AtividadeRealizada;
import java.util.List;

public class CalculoOutrasStrategy implements CalculoStrategy {
    private static final double PERCENTUAL_MAXIMO = 0.20; // 20%

    @Override
    public int calcularHorasValidadas(List<AtividadeRealizada> atividades, int cargaHorariaTotalCurso) {
        int somaHoras = atividades.stream().mapToInt(AtividadeRealizada::horasValidadas).sum();
        int limiteModalidade = (int) (cargaHorariaTotalCurso * PERCENTUAL_MAXIMO);
        return Math.min(somaHoras, limiteModalidade);
    }
}