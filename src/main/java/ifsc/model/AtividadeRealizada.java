package ifsc.model;

import java.time.LocalDate;
public record AtividadeRealizada(
        Integer id,
        Aluno aluno,
        AtividadeComplementar atividadeComplementar,
        int horasApresentadas,
        int horasValidadas,
        String documento,
        LocalDate dataRealizacao
) {}