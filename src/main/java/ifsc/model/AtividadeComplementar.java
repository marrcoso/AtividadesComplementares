package ifsc.model;

public record AtividadeComplementar(
        Integer id,
        String descricao,
        Integer limiteMaximoHoras,
        Modalidade modalidade)
{}