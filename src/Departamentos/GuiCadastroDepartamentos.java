package Departamentos;
import javax.swing.*;

import java.awt.event.*;
public class GuiCadastroDepartamentos extends JPanel {
    JLabel label1, label2, label3, label4;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfCodigo, tfSigla,tfNome,tfDiretor;
    private DepartamentosDAO departamentos;

    public GuiCadastroDepartamentos() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(null);
        label1 = new JLabel("Código:");
        label1.setBounds(10, 10, 60, 20);
        label2 = new JLabel("Sigla:");
        label2.setBounds(10, 40, 100, 20);
        label3 = new JLabel("Nome:");
        label3.setBounds(10, 70, 100, 20);
        label4 = new JLabel("Diretor:");
        label4.setBounds(10, 100, 100, 20);
        tfCodigo = new JTextField(8);
        tfCodigo.setBounds(60, 10, 100, 20);
        tfSigla = new JTextField(10);
        tfSigla.setBounds(60, 40, 100, 20);
        tfNome = new JTextField(50);
        tfNome.setBounds(60, 70, 300, 20);
        tfDiretor = new JTextField(50);
        tfDiretor.setBounds(60, 100, 300, 20);
        //btGravar = new JButton(null, new Imagelcon("gravar.gif"));
        btGravar = new JButton("Gravar");
        btGravar.setToolTipText("Gravar");
        btGravar.setBounds(10, 150, 100, 20);
        //btAlterar = new JButton(null, new Imagelcon("alterar.gif"));
        btAlterar = new JButton ("Alterar");
        btAlterar.setToolTipText("Alterar");
        btAlterar.setBounds(120, 150, 100, 20);
        //btExcluir = new JButton(null, new Imagelcon("excluir.gif"));
        btExcluir = new JButton("Excluir");
        btExcluir.setToolTipText("Excluir");
        btExcluir.setBounds(230, 150, 100, 20);
        //btLocalizar = new JButton(null, new Imagelcon("localizar.png"));
        btLocalizar = new JButton("Localizar");
        btLocalizar.setToolTipText("Localizar");
        btLocalizar.setBounds(340, 150, 100, 20);
        //btNovo = new JButton(null, new Imagelcon("novo.gif"));
        btNovo = new JButton("Novo");
        btNovo.setToolTipText("Novo");
        btNovo.setBounds(450, 150, 100, 20);
        //btCancelar = new JButton(null, new Imagelcon("cancelar.gif"));
        btCancelar = new JButton("Cancelar");
        btCancelar.setToolTipText("Cancelar");
        btCancelar.setBounds(560, 150, 100, 20);
        //btSair = new JButton(null, new Imagelcon("sair.png"));
        btSair = new JButton("Sair");
        btSair.setToolTipText("Sair");
        btSair.setBounds(670, 150, 100, 20);

        add(label1);
        add(tfCodigo);
        add(label2);
        add(tfSigla) ;
        add(label3);
        add(tfNome);
        add(label4);
        add(tfDiretor);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);

        setBotoes(true, true, false, false, false,false);
        departamentos = new DepartamentosDAO();
        if (!departamentos.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos () {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                departamentos.bd.close();
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
                if (tfSigla.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo sigla não pode ser vazio!");
                    tfSigla.requestFocus();
                    return;
                }
                if (tfNome.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo nome do departamento não pode ser vazio!");
                    tfNome.requestFocus();
                    return;
                }
                if (tfDiretor.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo Ano não pode ser vazio!");
                    tfDiretor.requestFocus();
                    return;
                }
                try {
                    int codigo = Integer.parseInt(tfCodigo.getText());
                    departamentos.departamento.setDeptoCodigo(codigo);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "O campo código deve ser número!");
                    tfCodigo.requestFocus();
                    return;
                }
                departamentos.departamento.setDeptoSigla(tfSigla.getText());
                departamentos.departamento.setDeptoNome(tfNome.getText());
                departamentos.departamento.setDeptoDiretor(tfDiretor.getText());
                JOptionPane.showMessageDialog(null, departamentos.atualizar(DepartamentosDAO.INCLUSAO));
                limparCampos();
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(tfCodigo.getText());
                    departamentos.departamento.setDeptoCodigo(codigo);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "O campo código deve ser número!");
                    tfCodigo.requestFocus();
                    return;
                }

                departamentos.departamento.setDeptoSigla(tfSigla.getText());
                departamentos.departamento.setDeptoNome(tfNome.getText());
                departamentos.departamento.setDeptoDiretor(tfDiretor.getText());
                JOptionPane.showMessageDialog(null, departamentos.atualizar(DepartamentosDAO.ALTERACAO));
                limparCampos();
            }
        });
        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(tfCodigo.getText());
                    departamentos.departamento.setDeptoCodigo(codigo);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "O campo código deve ser número!");
                    tfCodigo.requestFocus();
                    return;
                }

                departamentos.localizar();
                int n = JOptionPane.showConfirmDialog(null, departamentos.departamento.getDeptoNome(), " Excluir o Departamento?",
                        JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, departamentos.atualizar(DepartamentosDAO.EXCLUSAO));
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
        tfSigla.setText("");
        tfNome.setText("");
        tfDiretor.setText("");
        tfCodigo.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        try {
            int cod1 = Integer.parseInt(tfCodigo.getText());
            departamentos.departamento.setDeptoCodigo(cod1);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O campo código deve ser número!");
            tfCodigo.requestFocus();
            return;
        }

        if (departamentos.localizar()) {
            try {
                int cod2 = Integer.parseInt(tfCodigo.getText());
                departamentos.departamento.setDeptoCodigo(cod2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "O campo código deve ser número!");
                tfCodigo.requestFocus();
                return;
            }

            tfSigla.setText(departamentos.departamento.getDeptoSigla());
            tfNome.setText(departamentos.departamento.getDeptoNome());
            tfDiretor.setText(departamentos.departamento.getDeptoDiretor());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Departamento não encontrado!");
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