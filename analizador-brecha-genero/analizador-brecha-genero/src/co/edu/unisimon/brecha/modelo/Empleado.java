package co.edu.unisimon.brecha.modelo;

/**
 * Entidad Empleado. En memoria lleva el nombre del cargo, el area y el nivel
 * jerarquico para calcular indicadores sin consultar la base de datos.
 */
public class Empleado {

    public static final String[] NOMBRES_NIVEL =
        {"Todos", "Operativo", "Profesional/Tecnico", "Mando medio", "Alta direccion"};

    private final String codigo;
    private String nombre;
    private char sexo;        // 'F' o 'M'
    private String cargo;
    private String area;
    private int nivel;        // 1 a 4
    private double salario;   // pesos colombianos mensuales

    public Empleado(String codigo, String nombre, char sexo, String cargo,
                    String area, int nivel, double salario) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El codigo es obligatorio");
        }
        if (sexo != 'F' && sexo != 'M') {
            throw new IllegalArgumentException("Sexo invalido: " + sexo);
        }
        if (nivel < 1 || nivel > 4) {
            throw new IllegalArgumentException("Nivel fuera de rango: " + nivel);
        }
        if (salario <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor que cero");
        }
        this.codigo = codigo.trim();
        this.nombre = nombre;
        this.sexo = sexo;
        this.cargo = cargo;
        this.area = area;
        this.nivel = nivel;
        this.salario = salario;
    }

    public String getCodigo()  { return codigo; }
    public String getNombre()  { return nombre; }
    public char getSexo()      { return sexo; }
    public String getCargo()   { return cargo; }
    public String getArea()    { return area; }
    public int getNivel()      { return nivel; }
    public double getSalario() { return salario; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCargo(String cargo)   { this.cargo = cargo; }
    public void setArea(String area)     { this.area = area; }

    public void setSalario(double salario) {
        if (salario <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor que cero");
        }
        this.salario = salario;
    }

    @Override
    public String toString() {
        return String.format("%-5s %-17s %c  %-30s N%d  $%,13.0f",
                codigo, nombre, sexo, cargo, nivel, salario);
    }
}
