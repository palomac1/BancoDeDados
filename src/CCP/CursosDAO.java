package CCP;
import java.sql.*;

import Filme.BD;
public class CursosDAO {
    public Cursos curso;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO =3;

    public CursosDAO() {
        bd = new BD ();
        curso = new Cursos();
    }

    public boolean localizar() {
        sql = "select * from cursos where codigo = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, curso.getCodigo());
            resultSet = statement.executeQuery();
            resultSet.next();
            curso.setCodigo(resultSet.getString(1));
            curso.setNome(resultSet.getString(2));
            curso.setCarga(resultSet.getString(3));
            curso.setTipo(resultSet.getString(4));
            curso.setModalidade(resultSet.getString(5));
            return true;
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
            return false;
        }
    }

    public String atualizar(int operacao) {
        men = "Operação realizada com sucesso!";
        try {
            if (operacao == INCLUSAO) {
                sql = "insert into filmes values (?,?,?,?,?)"; 								statement = bd.connection.prepareStatement(sql);
                statement.setString(1,curso.getCodigo());
                statement.setString(2,curso.getNome());
                statement.setString(3,curso.getCarga());
                statement.setString(4,curso.getTipo());
                statement.setString(5,curso.getModalidade());


            } else if (operacao == ALTERACAO) {
                sql = "update cursos set nome = ?, carga = ?, tipo = ?,"
                        + "modalidade = ? where codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(10,curso.getCodigo());
                statement.setString(1, curso.getNome());
                statement.setString(2, curso.getCarga());
                statement.setString(3, curso.getTipo());
                statement.setString(4, curso.getModalidade());

            } else if (operacao == EXCLUSAO) {
                sql = "delete from filmes where codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, curso.getCodigo());
            }
            if (statement.executeUpdate() == 0) {
                men = "Falha na operacao!";
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return men;
    }
}
