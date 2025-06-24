package ifsc.dao;
import ifsc.model.Aluno;

import java.util.Optional;

public interface AlunoDAO {
    Optional<ifsc.model.Aluno> buscarPorId(int id);
}