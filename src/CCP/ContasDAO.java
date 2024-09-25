package CCP;
import java.sql.*;

import Filme.BD;

public class ContasDAO {
    public Contas contas;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO = 3;

    public ContasDAO() {
        bd = new BD();
        contas = new Contas();
    }

    public boolean localizar() {
        sql = "select * from contas where NumeroConta = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, contas.getNumeroConta());
            resultSet = statement.executeQuery();
            resultSet.next();
            contas.setNumeroConta(resultSet.getString(1));
            contas.setTitular(resultSet.getString(2));
            contas.setSaldo(resultSet.getString(3));
            contas.setTipoConta(resultSet.getString(4));
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
                sql = "insert into contas values (?, ?, ?, ?)";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, contas.getNumeroConta());
                statement.setString(2, contas.getTitular());
                statement.setString(3, contas.getSaldo());
                statement.setString(4, contas.getTipoConta());
            } else if (operacao == ALTERACAO) {
                sql = "update contas set Titular = ?, Saldo = ?, TipoConta = ? where NumeroConta = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, contas.getTitular());
                statement.setString(2, contas.getSaldo());
                statement.setString(3, contas.getTipoConta());
                statement.setString(4, contas.getNumeroConta());
            } else if (operacao == EXCLUSAO) {
                sql = "delete from contas where NumeroConta = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, contas.getNumeroConta());
            }
            if (statement.executeUpdate() == 0) {
                men = "Falha na operação!";
            }
        } catch (SQLException erro) {
            men = "Falha na operação " + erro.toString();
        }
        return men;
    }
}