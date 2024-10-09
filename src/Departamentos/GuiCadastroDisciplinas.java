package Departamentos;

import javax.swing.*;
import java.awt.event.*;

public class GuiCadastroDisciplinas extends JPanel {
    JLabel label1, label2, label3, label4, label5;
    JButton btGravar, btAlterar, btExcluir, btNovo, btLocalizar, btCancelar, btSair;
    static JTextField tfIDDisciplina, tfIDCurso, tfNome, tfCargaHoraria, tfAreaMateria;
    private DisciplinasDAO disciplinaDAO;

    public GuiCadastroDisciplinas() {
        inicializarComponentes();
        definirEventos();
    }

    public void inicializarComponentes() {
        setLayout(null);
        label1 = new JLabel("ID Disciplina:");
        label1.setBounds(10, 10, 100, 20);
        label2 = new JLabel("ID Curso:");
        label2.setBounds(10, 40, 100, 20);
        label3 = new JLabel("Nome:");
        label3.setBounds(10, 70, 100, 20);
        label4 = new JLabel("Carga Horária:");
        label4.setBounds(10, 100, 100, 20);
        label5 = new JLabel("Área da Matéria:");
        label5.setBounds(10, 130, 100, 20);

        tfIDDisciplina = new JTextField(8);
        tfIDDisciplina.setBounds(120, 10, 100, 20);
        tfIDCurso = new JTextField(10);
        tfIDCurso.setBounds(120, 40, 100, 20);
        tfNome = new JTextField(50);
        tfNome.setBounds(120, 70, 300, 20);
        tfCargaHoraria = new JTextField(10);
        tfCargaHoraria.setBounds(120, 100, 100, 20);
        tfAreaMateria = new JTextField(30);
        tfAreaMateria.setBounds(120, 130, 100, 20);

        btGravar = new JButton("Gravar");
        btGravar.setBounds(10, 170, 100, 20);
        btAlterar = new JButton("Alterar");
        btAlterar.setBounds(120, 170, 100, 20);
        btExcluir = new JButton("Excluir");
        btExcluir.setBounds(230, 170, 100, 20);
        btLocalizar = new JButton("Localizar");
        btLocalizar.setBounds(340, 170, 100, 20);
        btNovo = new JButton("Novo");
        btNovo.setBounds(450, 170, 100, 20);
        btCancelar = new JButton("Cancelar");
        btCancelar.setBounds(560, 170, 100, 20);
        btSair = new JButton("Sair");
        btSair.setBounds(670, 170, 100, 20);

        add(label1); add(tfIDDisciplina);
        add(label2); add(tfIDCurso);
        add(label3); add(tfNome);
        add(label4); add(tfCargaHoraria);
        add(label5); add(tfAreaMateria);
        add(btNovo); add(btLocalizar); add(btGravar);
        add(btAlterar); add(btExcluir); add(btCancelar); add(btSair);

        setBotoes(true, true, false, false, false, false);
        disciplinaDAO = new DisciplinasDAO();
        if (!DisciplinasDAO.bd.getConnection()) {
            JOptionPane.showMessageDialog(null, "Falha na conexão, o sistema será fechado!");
            System.exit(0);
        }
    }

    public void definirEventos() {
        btSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DisciplinasDAO.bd.close();
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
                if (tfIDDisciplina.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "O campo ID da disciplina não pode ser vazio!");
                    tfIDDisciplina.requestFocus();
                    return;
                }
                disciplinaDAO.disciplina.setID_disciplina(tfIDDisciplina.getText());
                disciplinaDAO.disciplina.setID_curso(tfIDCurso.getText());
                disciplinaDAO.disciplina.setNome(tfNome.getText());
                disciplinaDAO.disciplina.setCarga_horaria(Integer.parseInt(tfCargaHoraria.getText()));
                disciplinaDAO.disciplina.setArea_materia(tfAreaMateria.getText());
                JOptionPane.showMessageDialog(null, disciplinaDAO.atualizar(DisciplinasDAO.INCLUSAO));
                limparCampos();
            }
        });

        btAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disciplinaDAO.disciplina.setID_disciplina(tfIDDisciplina.getText());
                disciplinaDAO.disciplina.setID_curso(tfIDCurso.getText());
                disciplinaDAO.disciplina.setNome(tfNome.getText());
                disciplinaDAO.disciplina.setCarga_horaria(Integer.parseInt(tfCargaHoraria.getText()));
                disciplinaDAO.disciplina.setArea_materia(tfAreaMateria.getText());
                JOptionPane.showMessageDialog(null, disciplinaDAO.atualizar(DisciplinasDAO.ALTERACAO));
                limparCampos();
            }
        });

        btExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                disciplinaDAO.disciplina.setID_disciplina(tfIDDisciplina.getText());
                int n = JOptionPane.showConfirmDialog(null, disciplinaDAO.disciplina.getNome(), "Excluir a Disciplina?", JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, disciplinaDAO.atualizar(DisciplinasDAO.EXCLUSAO));
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
        tfIDDisciplina.setText("");
        tfIDCurso.setText("");
        tfNome.setText("");
        tfCargaHoraria.setText("");
        tfAreaMateria.setText("");
        tfIDDisciplina.requestFocus();
        setBotoes(true, true, false, false, false, false);
    }

    public void atualizarCampos() {
        disciplinaDAO.disciplina.setID_disciplina(tfIDDisciplina.getText());
        if (disciplinaDAO.localizar()) {
            tfIDCurso.setText(disciplinaDAO.disciplina.getID_curso());
            tfNome.setText(disciplinaDAO.disciplina.getNome());
            tfCargaHoraria.setText(String.valueOf(disciplinaDAO.disciplina.getCarga_horaria()));
            tfAreaMateria.setText(disciplinaDAO.disciplina.getArea_materia());
            setBotoes(true, true, false, true, true, true);
        } else {
            JOptionPane.showMessageDialog(null, "Disciplina não encontrada!");
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
