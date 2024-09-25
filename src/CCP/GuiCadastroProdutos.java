package CCP;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
public class GuiCadastroProdutos extends JFrame {
    JLabel label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfCodigo, tfDescricao,tfValor;
    private ProdutosDAO produto;

    public static void main(String[] args) {
        JFrame janela = new GuiCadastroProdutos();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    public GuiCadastroProdutos() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBounds(200, 200, 500, 200);
        setTitle("Cadastramento de Filmes");
        label1 = new JLabel("Código");
        label2 = new JLabel("Descrição");
        label3 = new JLabel("Valor");
        tfCodigo = new JTextField(8);
        tfDescricao = new JTextField(30);
        tfValor = new JTextField(10);
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
        add(tfCodigo);
        add(label2);
        add(tfDescricao) ;
        add(label3);
        add(tfValor);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);
        setResizable(false);
        setBotoes(true, true, false, false, false,false);
        produto = new ProdutosDAO();
        if (!produto.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos () {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produto.bd.close();
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
                if (tfCodigo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo código não pode ser vazio!");
                    tfCodigo.requestFocus();
                    return;
                }
                if (tfDescricao.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Descricao não pode ser vazio!");
                    tfDescricao.requestFocus();
                    return;
                }
                if (tfValor.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Valor não pode ser vazio!");
                    tfValor.requestFocus();
                    return;
                }
                produto.produto.setCodigo(tfCodigo.getText());
                produto.produto.setDescricao(tfDescricao.getText());
                produto.produto.setValor(tfValor.getText());
                JOptionPane.showMessageDialog(null, produto.atualizar(ProdutosDAO.INCLUSAO));
                limparCampos();
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produto.produto.setCodigo(tfCodigo.getText());
                produto.produto.setDescricao(tfDescricao.getText());
                produto.produto.setValor(tfValor.getText());
                JOptionPane.showMessageDialog(null, produto.atualizar(ProdutosDAO.ALTERACAO));
                limparCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produto.produto.setCodigo(tfCodigo.getText());
                produto.localizar();
                int n = JOptionPane.showConfirmDialog(null, produto.produto.getDescricao(), " Excluir o Filme?",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, produto.atualizar(ProdutosDAO.EXCLUSAO));
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
        tfCodigo.setText("");
        tfDescricao.setText("");
        tfValor.setText("");
        tfCodigo.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        produto.produto.setCodigo(tfCodigo.getText());
        if (produto.localizar()) {
            tfCodigo.setText(produto.produto.getCodigo());
            tfDescricao.setText(produto.produto.getDescricao());
            tfValor.setText(produto.produto.getValor());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado!");
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