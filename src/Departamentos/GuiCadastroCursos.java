package Departamentos;

import javax.swing.*;
import java.awt.event.*;

public class GuiCadastroCursos extends JPanel {
    JLabel label1, label2, label3, label4, label5, label6;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfIdCurso, tfNome, tfCarga, tfTipo, tfModalidade, tfCodigo;
    private CursoDAO cursos;

    public GuiCadastroCursos() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(null);
        
        // Labels
        label1 = new JLabel("Id do Curso:");
        label1.setBounds(10, 10, 100, 20);
        label2 = new JLabel("Nome:");
        label2.setBounds(10, 40, 100, 20);
        label3 = new JLabel("Carga Horária:");
        label3.setBounds(10, 70, 100, 20);
        label4 = new JLabel("Tipo:");
        label4.setBounds(10, 100, 100, 20);
        label5 = new JLabel("Modalidade:");
        label5.setBounds(10, 130, 100, 20);
        label6 = new JLabel("Código:");
        label6.setBounds(10, 160, 100, 20);

        // TextFields
        tfIdCurso = new JTextField(8);
        tfIdCurso.setBounds(120, 10, 100, 20);
        tfNome = new JTextField(50);
        tfNome.setBounds(120, 40, 300, 20);
        tfCarga = new JTextField(10);
        tfCarga.setBounds(120, 70, 100, 20);
        tfTipo = new JTextField(50);
        tfTipo.setBounds(120, 100, 300, 20);
        tfModalidade = new JTextField(50);
        tfModalidade.setBounds(120, 130, 300, 20);
        tfCodigo = new JTextField(50);
        tfCodigo.setBounds(120, 160, 300, 20);

        // Botões
        btGravar = new JButton("Gravar");
        btGravar.setToolTipText("Gravar");
        btGravar.setBounds(10, 200, 100, 20);

        btAlterar = new JButton("Alterar");
        btAlterar.setToolTipText("Alterar");
        btAlterar.setBounds(120, 200, 100, 20);

        btExcluir = new JButton("Excluir");
        btExcluir.setToolTipText("Excluir");
        btExcluir.setBounds(230, 200, 100, 20);

        btLocalizar = new JButton("Localizar");
        btLocalizar.setToolTipText("Localizar");
        btLocalizar.setBounds(340, 200, 100, 20);

        btNovo = new JButton("Novo");
        btNovo.setToolTipText("Novo");
        btNovo.setBounds(450, 200, 100, 20);

        btCancelar = new JButton("Cancelar");
        btCancelar.setToolTipText("Cancelar");
        btCancelar.setBounds(560, 200, 100, 20);

        btSair = new JButton("Sair");
        btSair.setToolTipText("Sair");
        btSair.setBounds(670, 200, 100, 20);

        // Adicionando componentes
        add(label1);
        add(tfIdCurso);
        add(label2);
        add(tfNome);
        add(label3);
        add(tfCarga);
        add(label4);
        add(tfTipo);
        add(label5);
        add(tfModalidade);
        add(label6);
        add(tfCodigo);
        add(btNovo);
        add(btLocalizar);
        add(btGravar);
        add(btAlterar);
        add(btExcluir);
        add(btCancelar);
        add(btSair);

        setBotoes(true, true, false, false, false, false);
        cursos = new CursoDAO();
        if (!cursos.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos() {
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
                if (validarCampos()) {
                    try {
                        int idCurso = Integer.parseInt(tfIdCurso.getText());
                        cursos.curso.setIdCurso(idCurso);
                        cursos.curso.setNomeCurso(tfNome.getText());
                        cursos.curso.setCargaHoraria(Integer.parseInt(tfCarga.getText()));
                        cursos.curso.setTipoCurso(tfTipo.getText());
                        cursos.curso.setModalidadeCurso(tfModalidade.getText());
                        cursos.curso.setCodigoCurso(Integer.parseInt(tfCodigo.getText()));

                        JOptionPane.showMessageDialog(null, cursos.atualizar(CursoDAO.INCLUSAO));
                        limparCampos();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao converter campos numéricos!");
                    }
                }
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    try {
                        int idCurso = Integer.parseInt(tfIdCurso.getText());
                        cursos.curso.setIdCurso(idCurso);
                        cursos.curso.setNomeCurso(tfNome.getText());
                        cursos.curso.setCargaHoraria(Integer.parseInt(tfCarga.getText()));
                        cursos.curso.setTipoCurso(tfTipo.getText());
                        cursos.curso.setModalidadeCurso(tfModalidade.getText());

                        JOptionPane.showMessageDialog(null, cursos.atualizar(CursoDAO.ALTERACAO));
                        limparCampos();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao converter campos numéricos!");
                    }
                }
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tfIdCurso.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo Id do curso não pode ser vazio!");
                    tfIdCurso.requestFocus();
                    return;
                }

                try {
                    int idCurso = Integer.parseInt(tfIdCurso.getText());
                    cursos.curso.setIdCurso(idCurso);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "O campo Id do curso deve ser número!");
                    tfIdCurso.requestFocus();
                    return;
                }

                cursos.localizar();
                int n = JOptionPane.showConfirmDialog(null, cursos.curso.getNomeCurso(), "Excluir o curso?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, cursos.atualizar(CursoDAO.EXCLUSAO));
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

    public boolean validarCampos() {
        if (tfIdCurso.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Id do curso não pode ser vazio!");
            tfIdCurso.requestFocus();
            return false;
        }
        if (tfNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Nome não pode ser vazio!");
            tfNome.requestFocus();
            return false;
        }
        if (tfCarga.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Carga Horária não pode ser vazio!");
            tfCarga.requestFocus();
            return false;
        }
        if (tfModalidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo Modalidade não pode ser vazio!");
            tfModalidade.requestFocus();
            return false;
        }
        return true;
    }

    public void limparCampos() {
        tfIdCurso.setText("");
        tfNome.setText("");
        tfCarga.setText("");
        tfTipo.setText("");
        tfModalidade.setText("");
        tfCodigo.setText("");
        tfIdCurso.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        try {
            int idCurso = Integer.parseInt(tfIdCurso.getText());
            cursos.curso.setIdCurso(idCurso);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "O campo Id do curso deve ser número!");
            tfIdCurso.requestFocus();
            return;
        }

        if (cursos.localizar()) {
            tfNome.setText(cursos.curso.getNomeCurso());
            tfCarga.setText(String.valueOf(cursos.curso.getCargaHoraria()));
            tfTipo.setText(cursos.curso.getTipoCurso());
            tfModalidade.setText(cursos.curso.getModalidadeCurso());
            tfCodigo.setText(String.valueOf(cursos.curso.getCodigoCurso()));
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Curso não encontrado!");
            limparCampos();
        }
    }

    public void setBotoes(boolean bNovo, boolean bLocalizar, boolean bGravar, boolean bAlterar, boolean bExcluir, boolean bCancelar) {
        btNovo.setEnabled(bNovo);
        btLocalizar.setEnabled(bLocalizar);
        btGravar.setEnabled(bGravar);
        btAlterar.setEnabled(bAlterar);
        btExcluir.setEnabled(bExcluir);
        btCancelar.setEnabled(bCancelar);
    }
}
