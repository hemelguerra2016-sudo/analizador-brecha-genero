package co.edu.unisimon.brecha.app;

import co.edu.unisimon.brecha.estructuras.ArbolAVL;
import co.edu.unisimon.brecha.estructuras.ListaDobleEmpleados;
import co.edu.unisimon.brecha.modelo.Empleado;
import co.edu.unisimon.brecha.servicios.AnalizadorEquidad;

/** Programa de prueba de las primeras operaciones (informe de avance). */
public class MainPrueba {

    /** Salario minimo mensual legal vigente 2026. */
    public static final double SMLMV_2026 = 1_750_905.0;

    public static void main(String[] args) {
        ListaDobleEmpleados lista = new ListaDobleEmpleados();
        ArbolAVL avlCodigo = new ArbolAVL(ArbolAVL.POR_CODIGO);
        ArbolAVL avlSalario = new ArbolAVL(ArbolAVL.POR_SALARIO);

        titulo("1. CASOS BORDE CON ESTRUCTURAS VACIAS");
        System.out.println("Buscar E001 en lista vacia   -> " + lista.buscarPorCodigo("E001"));
        System.out.println("Buscar E001 en AVL vacio     -> " + avlCodigo.buscarPorCodigo("E001"));
        System.out.println("Eliminar E001 en lista vacia -> " + lista.eliminarPorCodigo("E001"));
        System.out.println("Recorrido de lista vacia:");
        lista.recorrerAdelante();

        // Datos simulados de una organizacion ficticia (salarios anclados al SMLMV 2026)
        Empleado[] datos = {
            new Empleado("E001", "Ana Perez",      'F', "Auxiliar administrativa",   "Administracion", 1, SMLMV_2026),
            new Empleado("E002", "Carlos Diaz",    'M', "Auxiliar de bodega",        "Logistica",      1, 1_850_000),
            new Empleado("E003", "Maria Gomez",    'F', "Recepcionista",             "Administracion", 1, SMLMV_2026),
            new Empleado("E004", "Luis Torres",    'M', "Operario de produccion",    "Produccion",     1, 1_900_000),
            new Empleado("E005", "Laura Ruiz",     'F', "Analista de datos",         "Tecnologia",     2, 4_200_000),
            new Empleado("E006", "Jorge Castro",   'M', "Analista de datos",         "Tecnologia",     2, 4_600_000),
            new Empleado("E007", "Sofia Herrera",  'F', "Contadora",                 "Finanzas",       2, 4_500_000),
            new Empleado("E008", "Andres Molina",  'M', "Ingeniero de procesos",     "Produccion",     2, 4_800_000),
            new Empleado("E009", "Paola Vargas",   'F', "Coordinadora talento hum.", "Talento Humano", 3, 7_000_000),
            new Empleado("E010", "Diego Rojas",    'M', "Jefe de produccion",        "Produccion",     3, 8_200_000),
            new Empleado("E011", "Ricardo Silva",  'M', "Jefe de tecnologia",        "Tecnologia",     3, 8_800_000),
            new Empleado("E012", "Claudia Mejia",  'F', "Directora financiera",      "Finanzas",       4, 15_000_000),
            new Empleado("E013", "Fernando Ortiz", 'M', "Gerente general",           "Gerencia",       4, 13 * SMLMV_2026),
            new Empleado("E014", "Javier Pineda",  'M', "Director comercial",        "Comercial",      4, 16_500_000)
        };

        titulo("2. INSERCION (el AVL por codigo rechaza duplicados)");
        for (Empleado e : datos) {
            if (avlCodigo.insertar(e)) {
                lista.insertarAlFinal(e);
                avlSalario.insertar(e);
            }
        }
        Empleado dup = new Empleado("E005", "Duplicado", 'F', "Analista de datos", "Tecnologia", 2, 4_200_000);
        System.out.println("Insertar codigo duplicado E005 -> " + avlCodigo.insertar(dup));
        System.out.println("Empleados en lista: " + lista.getTamanio()
                + " | en AVL: " + avlCodigo.getTamanio()
                + " | altura AVL: " + avlCodigo.getAltura()
                + " (14 claves ordenadas: un BST simple tendria altura 14)");

        titulo("3. RECORRIDO HACIA ADELANTE (lista doble)");
        lista.recorrerAdelante();
        titulo("4. RECORRIDO HACIA ATRAS (lista doble)");
        lista.recorrerAtras();
        titulo("5. RECORRIDO INORDEN DEL AVL POR SALARIO");
        avlSalario.inorden();

        titulo("6. BUSQUEDA POR CODIGO");
        System.out.println("AVL   E010 -> " + avlCodigo.buscarPorCodigo("E010"));
        System.out.println("Lista E010 -> " + lista.buscarPorCodigo("E010"));
        System.out.println("AVL   E999 (inexistente) -> " + avlCodigo.buscarPorCodigo("E999"));

        titulo("7. CONSULTA POR RANGO SALARIAL $4.000.000 - $9.000.000");
        ListaDobleEmpleados rango = avlSalario.consultarRangoSalarial(4_000_000, 9_000_000);
        rango.recorrerAdelante();
        System.out.println("Resultados: " + rango.getTamanio());

        titulo("8. INDICADORES DE EQUIDAD POR NIVEL");
        for (int nivel = 1; nivel <= 4; nivel++) {
            System.out.printf("%-20s brecha: %6.2f%%   mujeres: %5.1f%%%n",
                    Empleado.NOMBRES_NIVEL[nivel],
                    AnalizadorEquidad.brechaSalarial(lista, nivel),
                    AnalizadorEquidad.porcentajeMujeres(lista, nivel));
        }
        System.out.printf("%-20s brecha: %6.2f%%   mujeres: %5.1f%%%n", "TOTAL",
                AnalizadorEquidad.brechaSalarial(lista, 0),
                AnalizadorEquidad.porcentajeMujeres(lista, 0));
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("===== " + texto + " =====");
    }
}
