package Departamentos;
import java.sql.*;

import Filme.BD;
public class DepartamentosDAO {
    public Departamentos departamento;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO =3;

    public DepartamentosDAO() {
        bd = new BD ();
        departamento = new Departamentos();
    }

    public boolean localizar() {
        sql = "select * from departamentos where depto_codigo = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setInt(1, departamento.getDeptoCodigo());
            resultSet = statement.executeQuery();
            resultSet.next();
            departamento.setDeptoCodigo(resultSet.getInt(1));
            departamento.setDeptoSigla(resultSet.getString(2));
            departamento.setDeptoNome(resultSet.getString(3));
            departamento.setDeptoDiretor(resultSet.getString(4));
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
                sql = "insert into contas values (?,?,?,?)";
                statement = bd.connection.prepareStatement(sql);
                statement.setInt(1,departamento.getDeptoCodigo());
                statement.setString(2,departamento.getDeptoSigla());
                statement.setString(3,departamento.getDeptoNome());
                statement.setString(4,departamento.getDeptoDiretor());

            } else if (operacao == ALTERACAO) {
                sql = "update departamentos set depto_codigo = ?, depto_sigla = ?, depto_nome = ?"
                        + "depto_diretor = ?";

                statement = bd.connection.prepareStatement(sql);
                statement.setInt(4,departamento.getDeptoCodigo());
                statement.setString(1, departamento.getDeptoSigla());
                statement.setString(2, departamento.getDeptoNome());
                statement.setString(3, departamento.getDeptoDiretor());


            } else if (operacao == EXCLUSAO) {
                sql = "delete from departamentos where depto_codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setInt(1, departamento.getDeptoCodigo());
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