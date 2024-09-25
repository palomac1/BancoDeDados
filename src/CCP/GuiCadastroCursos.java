package CCP;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
public class GuiCadastroCursos extends JFrame {
    JLabel label1, label2, label3, label4, label5;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfCodigo, tfNome,tfCarga,tfTipo,tfModalidade;
    private CursosDAO cursos;

    public static void main(String[] args) {
        JFrame janela = new GuiCadastroCursos();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }

    public GuiCadastroCursos() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBounds(200, 200, 700, 150);
        setTitle("Cadastramento de Cursos");
        label1 = new JLabel("Código");
        label2 = new JLabel("Nome");
        label3 = new JLabel("Carga");
        label4 = new JLabel("Tipo");
        label5 = new JLabel("Modalidade");
        tfCodigo = new JTextField(8);
        tfNome = new JTextField(50);
        tfCarga = new JTextField(8);
        tfTipo = new JTextField(20);
        tfModalidade = new JTextField(20);
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
        add(tfNome) ;
        add(label3);
        add(tfCarga);
        add(label4);
        add(tfTipo);
        add(label5);
        add(tfModalidade);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);
        setResizable(false);
        setBotoes(true, true, false, false, false,false);
        cursos = new CursosDAO();
        if (!cursos.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos () {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cursos.bd.close();
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
                if (tfNome.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Nome não pode ser vazio!");
                    tfNome.requestFocus();
                    return;
                }
                if (tfCarga.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Carga não pode ser vazio!");
                    tfNome.requestFocus();
                    return;
                }
                if (tfTipo.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Tipo não pode ser vazio!");
                    tfTipo.requestFocus();
                    return;
                }
                if (tfModalidade.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Modalidade não pode ser vazio!");
                    tfModalidade.requestFocus();
                    return;
                }
                cursos.curso.setCodigo(tfCodigo.getText());
                cursos.curso.setNome(tfNome.getText());
                cursos.curso.setCarga(tfCarga.getText());
                cursos.curso.setTipo(tfTipo.getText());
                cursos.curso.setModalidade(tfModalidade.getText());
                JOptionPane.showMessageDialog(null, cursos.atualizar(CursosDAO.INCLUSAO));
                limparCampos();
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cursos.curso.setCodigo(tfCodigo.getText());
                cursos.curso.setNome(tfNome.getText());
                cursos.curso.setCarga(tfCarga.getText());
                cursos.curso.setTipo(tfTipo.getText());
                cursos.curso.setModalidade(tfModalidade.getText());
                JOptionPane.showMessageDialog(null, cursos.atualizar(CursosDAO.ALTERACAO));
                limparCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cursos.curso.setCodigo(tfCodigo.getText());
                cursos.localizar();
                int n = JOptionPane.showConfirmDialog(null, cursos.curso.getNome(), " Excluir o curso?",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, cursos.atualizar(CursosDAO.EXCLUSAO));
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
        tfNome.setText("");
        tfCarga.setText("");
        tfTipo.setText("");
        tfModalidade.setText("");
        tfCodigo.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        cursos.curso.setCodigo(tfCodigo.getText());
        if (cursos.localizar()) {
            tfCodigo.setText(cursos.curso.getCodigo());
            tfNome.setText(cursos.curso.getNome());
            tfCarga.setText(cursos.curso.getCarga());
            tfTipo.setText(cursos.curso.getTipo());
            tfModalidade.setText(cursos.curso.getModalidade());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Curso não encontrado!");
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