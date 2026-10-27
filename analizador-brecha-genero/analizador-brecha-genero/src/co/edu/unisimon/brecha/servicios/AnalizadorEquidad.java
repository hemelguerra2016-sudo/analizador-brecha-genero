package co.edu.unisimon.brecha.servicios;

import co.edu.unisimon.brecha.estructuras.ListaDobleEmpleados;
import co.edu.unisimon.brecha.estructuras.NodoDoble;
import co.edu.unisimon.brecha.modelo.Empleado;

/** Indicadores de equidad calculados recorriendo la lista. nivel = 0: todos. */
public final class AnalizadorEquidad {

    private AnalizadorEquidad() { }

    public static double promedioSalarial(ListaDobleEmpleados lista, char sexo, int nivel) {
        double suma = 0;
        int n = 0;
        for (NodoDoble a = lista.getCabeza(); a != null; a = a.getSiguiente()) {
            Empleado e = a.getDato();
            if (e.getSexo() == sexo && (nivel == 0 || e.getNivel() == nivel)) {
                suma += e.getSalario();
                n++;
            }
        }
        return (n == 0) ? Double.NaN : suma / n;
    }

    /** Brecha no ajustada (%) = (prom. hombres - prom. mujeres) / prom. hombres x 100. */
    public static double brechaSalarial(ListaDobleEmpleados lista, int nivel) {
        double h = promedioSalarial(lista, 'M', nivel);
        double m = promedioSalarial(lista, 'F', nivel);
        if (Double.isNaN(h) || Double.isNaN(m)) {
            return Double.NaN;
        }
        return (h - m) / h * 100.0;
    }

    public static double porcentajeMujeres(ListaDobleEmpleados lista, int nivel) {
        int total = 0, mujeres = 0;
        for (NodoDoble a = lista.getCabeza(); a != null; a = a.getSiguiente()) {
            Empleado e = a.getDato();
            if (nivel == 0 || e.getNivel() == nivel) {
                total++;
                if (e.getSexo() == 'F') {
                    mujeres++;
                }
            }
        }
        return (total == 0) ? Double.NaN : 100.0 * mujeres / total;
    }
}
