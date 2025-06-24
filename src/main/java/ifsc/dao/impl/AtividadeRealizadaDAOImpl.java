package ifsc.dao.impl;

import br.ifsc.tubarao.dao.ConnectionFactory;
import ifsc.dao.AtividadeRealizadaDAO;
import ifsc.model.Aluno;
import ifsc.model.AtividadeComplementar;
import ifsc.model.AtividadeRealizada;
import ifsc.model.Modalidade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtividadeRealizadaDAOImpl implements AtividadeRealizadaDAO {

    @Override
    public List<AtividadeRealizada> buscarPorAluno(Aluno aluno) {
        List<AtividadeRealizada> atividades = new ArrayList<>();
        // Query complexa que junta as tabelas para montar o objeto completo
        String sql = """
            SELECT
                ar.id as ar_id, ar.horas_apresentadas, ar.horas_validadas, ar.documento, ar.data_realizacao,
                ac.id as ac_id, ac.descricao, ac.limite_maximo_horas,
                m.id as m_id, m.nome as m_nome
            FROM atividade_realizada ar
            JOIN atividade_complementar ac ON ar.atividade_complementar_id = ac.id
            JOIN modalidade m ON ac.modalidade_id = m.id
            WHERE ar.aluno_id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, aluno.id());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Modalidade modalidade = new Modalidade(rs.getInt("m_id"), rs.getString("m_nome"));
                AtividadeComplementar atividadeComplementar = new AtividadeComplementar(
                        rs.getInt("ac_id"),
                        rs.getString("descricao"),
                        rs.getInt("limite_maximo_horas"),
                        modalidade
                );
                AtividadeRealizada atividadeRealizada = new AtividadeRealizada(
                        rs.getInt("ar_id"),
                        aluno,
                        atividadeComplementar,
                        rs.getInt("horas_apresentadas"),
                        rs.getInt("horas_validadas"),
                        rs.getString("documento"),
                        rs.getDate("data_realizacao").toLocalDate()
                );
                atividades.add(atividadeRealizada);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar atividades realizadas: " + e.getMessage());
        }
        return atividades;
    }
}