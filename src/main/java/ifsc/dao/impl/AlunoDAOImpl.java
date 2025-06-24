package ifsc.dao.impl;

import br.ifsc.tubarao.dao.ConnectionFactory;
import ifsc.dao.AlunoDAO;
import ifsc.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AlunoDAOImpl implements AlunoDAO {

    @Override
    public Optional<Aluno> buscarPorId(int id) {
        String sql = "SELECT id, nome FROM aluno WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Aluno(rs.getInt("id"), rs.getString("nome")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por ID: " + e.getMessage());
        }
        return Optional.empty();
    }
}