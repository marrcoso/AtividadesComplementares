package ifsc.dao;
import ifsc.model.Aluno;
import ifsc.model.AtividadeRealizada;

import java.util.List;

public interface AtividadeRealizadaDAO {
    List<ifsc.model.AtividadeRealizada> buscarPorAluno(ifsc.model.Aluno aluno);
}