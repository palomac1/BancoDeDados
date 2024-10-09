package Departamentos;

public class Disciplina {
    private String ID_disciplina;
    private String ID_curso;
    private String nome;
    private int carga_horaria;
    private String area_materia;

    // Getters e Setters
    public String getID_disciplina() {
        return ID_disciplina;
    }

    public void setID_disciplina(String ID_disciplina) {
        this.ID_disciplina = ID_disciplina;
    }

    public String getID_curso() {
        return ID_curso;
    }

    public void setID_curso(String ID_curso) {
        this.ID_curso = ID_curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getArea_materia() {
        return area_materia;
    }

    public void setArea_materia(String area_materia) {
        this.area_materia = area_materia;
    }
}