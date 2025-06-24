package ifsc.model;

import java.util.Map;
public record RelatorioHoras(
        Map<Modalidade, Integer> horasPorModalidade,
        int totalHorasValidadas)
{}