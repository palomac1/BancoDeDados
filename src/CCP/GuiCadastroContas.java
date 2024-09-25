package CCP;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
public class GuiCadastroContas extends JFrame {
    JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfNumeroConta, tfTitular,tfSaldo, tfTipoConta;
    private ContasDAO contas;

    public static void main(String[] args) {
        JFrame janela = new GuiCadastroContas();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    public GuiCadastroContas() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBounds(200, 200, 500, 200);
        setTitle("Cadastramento de Contas");
        label1 = new JLabel("Numero da Conta");
        label2 = new JLabel("Titular");
        label3 = new JLabel("Saldo");
        label4 = new JLabel("Tipo de Conta");
        tfNumeroConta = new JTextField(8);
        tfTitular = new JTextField(30);
        tfSaldo = new JTextField(10);
        tfTipoConta = new JTextField(10);
        //btGravar = new JButton(null, new Imagelcon("gravar.gif"));
        btGravar = new JButton("Gravar");
        btGravar.setToolTipText("Gravar");
        //btAlterar = new JButton(null, new Imagelcon("alterar.gif"));
        btAlterar = new JButton ("Alterar");
        btAlterar.setToolTipText("Alterar");
        //btExcluir = new JButton(null, new Imagelcon("excluir.gif"));
        btExcluir = new JButton("Excluir");
        btExcluir.setToolTipText("Excluir");
        //btLocalizar = new JButton(null, new Imagelcon("localizar.png"));
        btLocalizar = new JButton("Localizar");
        btLocalizar.setToolTipText("Localizar");
        //btNovo = new JButton(null, new Imagelcon("novo.gif"));
        btNovo = new JButton("Novo");
        btNovo.setToolTipText("Novo");
        //btCancelar = new JButton(null, new Imagelcon("cancelar.gif"));
        btCancelar = new JButton("Cancelar");
        btCancelar.setToolTipText("Cancelar");
        //btSair = new JButton(null, new Imagelcon("sair.png"));
        btSair = new JButton("Sair");
        btSair.setToolTipText("Sair");
        add(label1);
        add(tfNumeroConta);
        add(label2);
        add(tfTitular) ;
        add(label3);
        add(tfSaldo);
        add(label4);
        add(tfTipoConta);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);
        setResizable(false);
        setBotoes(true, true, false, false, false,false);
        contas = new ContasDAO();
        if (!contas.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos () {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contas.bd.close();
                System.exit(0);
            }
        });

        btNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                setBotoes(false, false, true, false, false, true);
            }
        });

        btCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btGravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tfNumeroConta.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O Numero da Conta não pode ser vazio!");
                    tfNumeroConta.requestFocus();
                    return;
                }
                if (tfTitular.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O Titular não pode ser vazio!");
                    tfTitular.requestFocus();
                    return;
                }
                if (tfSaldo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O Saldo não pode ser vazio!");
                    tfSaldo.requestFocus();
                    return;
                }
                if (tfTipoConta.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O Tipo de Conta não pode ser vazio!");
                    tfTipoConta.requestFocus();
                    return;
                }

                contas.contas.setNumeroConta(tfNumeroConta.getText());
                contas.contas.setTitular(tfTitular.getText());
                contas.contas.setSaldo(tfSaldo.getText());
                contas.contas.setTipoConta(tfTipoConta.getText());

                JOptionPane.showMessageDialog(null, contas.atualizar(ContasDAO.INCLUSAO));
                limparCampos();
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contas.contas.setNumeroConta(tfNumeroConta.getText());
                contas.contas.setTitular(tfTitular.getText());
                contas.contas.setSaldo(tfSaldo.getText());
                contas.contas.setTipoConta(tfTipoConta.getText());

                JOptionPane.showMessageDialog(null, contas.atualizar(ContasDAO.ALTERACAO));
                limparCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contas.contas.setNumeroConta(tfNumeroConta.getText());
                contas.localizar();
                int n = JOptionPane.showConfirmDialog(null, contas.contas.getTitular(), " Excluir conta?",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, contas.atualizar(ContasDAO.EXCLUSAO));
                    limparCampos();
                }
            }
        });

        btLocalizar.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                atualizarCampos();
            }
            
        });
    }

    public void limparCampos() {
        tfNumeroConta.setText("");
        tfTitular.setText("");
        tfSaldo.setText("");
        tfTipoConta.setText("");
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        contas.contas.setNumeroConta(tfNumeroConta.getText());
        if (contas.localizar()) {
            tfNumeroConta.setText(contas.contas.getNumeroConta());
            tfTitular.setText(contas.contas.getTitular());
            tfSaldo.setText(contas.contas.getSaldo());
            tfTipoConta.setText(contas.contas.getTipoConta());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Conta não encontrada!");
            limparCampos();
        }
    }
    public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar, boolean bAlterar,
                          boolean bExcluir, boolean bCancelar) {
        btNovo.setEnabled(bNovo);
        btLocalizar.setEnabled(bLocalizar);
        btGravar.setEnabled(bGravar);
        btAlterar.setEnabled(bAlterar);
        btExcluir.setEnabled(bExcluir);
        btCancelar.setEnabled(bCancelar);
    }

}