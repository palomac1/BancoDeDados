package CCP;
import java.sql.*;

import Filme.BD;
public class ProdutosDAO {
    public Produtos produto;
    public BD bd;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String men, sql;
    public static final byte INCLUSAO = 1;
    public static final byte ALTERACAO = 2;
    public static final byte EXCLUSAO =3;

    public ProdutosDAO() {
        bd = new BD ();
        produto = new Produtos();
    }

    public boolean localizar() {
        sql = "select * from produtos where codigo = ?";
        try {
            statement = bd.connection.prepareStatement(sql);
            statement.setString(1, produto.getCodigo());
            resultSet = statement.executeQuery();
            resultSet.next();
            produto.setCodigo(resultSet.getString(1));
            produto.setDescricao(resultSet.getString(2));
            produto.setValor(resultSet.getString(3));
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
                sql = "insert into produtos values (?,?,?)"; 								statement = bd.connection.prepareStatement(sql);
                statement.setString(1,produto.getCodigo());
                statement.setString(2,produto.getDescricao());
                statement.setString(3,produto.getValor());

            } else if (operacao == ALTERACAO) {
                sql = "update produtos set descricao = ?, valor = ? where codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(3,produto.getCodigo());
                statement.setString(1, produto.getDescricao());
                statement.setString(2, produto.getValor());

            } else if (operacao == EXCLUSAO) {
                sql = "delete from produtos where codigo = ?";
                statement = bd.connection.prepareStatement(sql);
                statement.setString(1, produto.getCodigo());
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
