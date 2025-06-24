package ifsc.service.strategy;
import ifsc.model.AtividadeRealizada;

import java.util.List;

@FunctionalInterface
public interface CalculoStrategy {
    int calcularHorasValidadas(List<AtividadeRealizada> atividades, int cargaHorariaTotalCurso);
}