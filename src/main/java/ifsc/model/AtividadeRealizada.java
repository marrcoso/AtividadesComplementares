package ifsc.model;

public class AtividadeRealizada {
    private int id;
    private int requerimentoId;
    private int atividadeId;
    private int horasApresentadas;
    private String descricao;

    public AtividadeRealizada(int id, int requerimentoId, int atividadeId, int horasApresentadas, String descricao) {
        this.id = id;
        this.requerimentoId = requerimentoId;
        this.atividadeId = atividadeId;
        this.horasApresentadas = horasApresentadas;
        this.descricao = descricao;
    }
}