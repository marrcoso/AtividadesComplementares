package ifsc.service;

import ifsc.dao.AtividadeRealizadaDAO;
import ifsc.model.Aluno;
import ifsc.model.AtividadeRealizada;
import ifsc.model.Modalidade;
import ifsc.model.RelatorioHoras;
import ifsc.service.factory.CalculoStrategyFactory;
import ifsc.service.strategy.CalculoStrategy;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CalculadoraHorasService {

    private final AtividadeRealizadaDAO atividadeRealizadaDAO;

    public CalculadoraHorasService(AtividadeRealizadaDAO atividadeRealizadaDAO) {
        this.atividadeRealizadaDAO = atividadeRealizadaDAO;
    }

    public RelatorioHoras gerarRelatorio(Aluno aluno, int cargaHorariaTotalCurso) {
        List<AtividadeRealizada> atividades = atividadeRealizadaDAO.buscarPorAluno(aluno);

        // Agrupa as atividades por modalidade
        Map<Modalidade, List<AtividadeRealizada>> atividadesPorModalidade = atividades.stream()
                .collect(Collectors.groupingBy(ar -> ar.atividadeComplementar().modalidade()));

        Map<Modalidade, Integer> horasValidadasPorModalidade = new HashMap<>();

        // Para cada modalidade, aplica a estratégia de cálculo correspondente
        for (Map.Entry<Modalidade, List<AtividadeRealizada>> entry : atividadesPorModalidade.entrySet()) {
            Modalidade modalidade = entry.getKey();
            List<AtividadeRealizada> atividadesDaModalidade = entry.getValue();

            CalculoStrategy strategy = CalculoStrategyFactory.getStrategy(modalidade);
            int horasComputadas = strategy.calcularHorasValidadas(atividadesDaModalidade, cargaHorariaTotalCurso);

            horasValidadasPorModalidade.put(modalidade, horasComputadas);
        }

        int totalGeral = horasValidadasPorModalidade.values().stream().mapToInt(Integer::intValue).sum();
        return new RelatorioHoras(horasValidadasPorModalidade, totalGeral);
    }
}


