package CCP;

public class Contas {
    private String NumeroConta, Titular, Saldo, TipoConta;
    public String getNumeroConta() {
        return NumeroConta;
    }
    public void setNumeroConta(String NumeroConta) {
        this.NumeroConta = NumeroConta;
    }

    public String getTitular() {
        return Titular;
    }
    public void setTitular(String Titular) {
        this.Titular = Titular;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String Saldo) {
        this.Saldo = Saldo;
    }

    public String getTipoConta() {
        return TipoConta;
    }

    public void setTipoConta(String TipoConta) {
        this.TipoConta = TipoConta;
    }
   
}