package Departamentos;
import java.sql.*;
import Filme.BD;

public class CursoDAO {
    public Cursos curso;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;

    public CursoDAO() {
        bd = new BD();
        curso = new Cursos();
    }

    public boolean localizar() {
        sql = "SELECT * FROM cursos WHERE ID_curso = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setInt(1, curso.getIdCurso()); // Corrigido o índice para 1
            resultSet = statement.executeQuery();

            if (resultSet.next()) { // Adicionada verificação para evitar erro ao tentar acessar resultSet vazio
                curso.setIdCurso(resultSet.getInt(1));
                curso.setNomeCurso(resultSet.getString(2));
                curso.setCargaHoraria(resultSet.getInt(3));
                curso.setTipoCurso(resultSet.getString(4));
                curso.setModalidadeCurso(resultSet.getString(5));
                curso.setCodigoCurso(resultSet.getInt(6));
                return true;
            } else {
                men = "Curso não encontrado!";
                return false;
            }
        } catch (SQLException erro) {
            men = "Falha na operação: " + erro.toString();
            return false;
        }
    }

    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if (operacao == INCLUSAO) {
                sql = "INSERT INTO cursos (ID_curso, nome, carga_horaria, tipo, modalidade, codigo) VALUES (?, ?, ?, ?, ?, ?)";
                statement = bd.connection.prepareStatement(sql);
                statement.setInt(1, curso.getIdCurso());
                statement.setString(2, curso.getNomeCurso());
                statement.setInt(3, curso.getCargaHoraria());
                statement.setString(4, curso.getTipoCurso());
                statement.setString(5, curso.getModalidadeCurso());
                statement.setInt(6, curso.getCodigoCurso());

            } else if (operacao == ALTERACAO) {
                sql = "UPDATE cursos SET nome = ?, carga_horaria = ?, tipo = ?, modalidade = ?, codigo = ? WHERE ID_curso = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, curso.getNomeCurso());
                statement.setInt(2, curso.getCargaHoraria());
                statement.setString(3, curso.getTipoCurso());
                statement.setString(4, curso.getModalidadeCurso());
                statement.setInt(5, curso.getCodigoCurso());
                statement.setInt(6, curso.getIdCurso()); // Corrigido índice para ID_curso

            } else if (operacao == EXCLUSAO) {
                sql = "DELETE FROM cursos WHERE ID_curso = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setInt(1, curso.getIdCurso()); // Corrigido índice para 1

            }

            if (statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
        } catch (SQLException erro) {
            men = "Falha na operação: " + erro.toString();
        }
        return men;
    }
}
