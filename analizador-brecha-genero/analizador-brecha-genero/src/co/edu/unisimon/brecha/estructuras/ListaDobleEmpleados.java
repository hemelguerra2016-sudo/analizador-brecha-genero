package co.edu.unisimon.brecha.estructuras;

import co.edu.unisimon.brecha.modelo.Empleado;

/**
 * Lista doblemente enlazada propia (sin java.util). Almacena la nomina en
 * memoria y permite recorrerla en ambos sentidos para calcular indicadores.
 */
public class ListaDobleEmpleados {
    private NodoDoble cabeza;
    private NodoDoble cola;
    private int tamanio;

    public boolean estaVacia()   { return cabeza == null; }
    public int getTamanio()      { return tamanio; }
    public NodoDoble getCabeza() { return cabeza; }
    public NodoDoble getCola()   { return cola; }

    /** Inserta al final. O(1). */
    public void insertarAlFinal(Empleado e) {
        if (e == null) {
            throw new IllegalArgumentException("No se puede insertar un empleado nulo");
        }
        NodoDoble nuevo = new NodoDoble(e);
        if (estaVacia()) {              // caso borde: lista vacia
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }
        tamanio++;
    }

    /** Inserta manteniendo orden ascendente por salario. O(n). */
    public void insertarOrdenadoPorSalario(Empleado e) {
        if (e == null) {
            throw new IllegalArgumentException("No se puede insertar un empleado nulo");
        }
        NodoDoble nuevo = new NodoDoble(e);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
            tamanio++;
            return;
        }
        NodoDoble actual = cabeza;
        while (actual != null && actual.getDato().getSalario() <= e.getSalario()) {
            actual = actual.getSiguiente();
        }
        if (actual == null) {                 // al final
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        } else if (actual == cabeza) {        // al inicio
            nuevo.setSiguiente(cabeza);
            cabeza.setAnterior(nuevo);
            cabeza = nuevo;
        } else {                              // en medio
            NodoDoble previo = actual.getAnterior();
            previo.setSiguiente(nuevo);
            nuevo.setAnterior(previo);
            nuevo.setSiguiente(actual);
            actual.setAnterior(nuevo);
        }
        tamanio++;
    }

    /** Busqueda secuencial por codigo. O(n). Devuelve null si no existe. */
    public Empleado buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        NodoDoble actual = cabeza;
        while (actual != null) {
            if (actual.getDato().getCodigo().equals(codigo)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /** Modifica el salario de un empleado. O(n). */
    public boolean modificarSalario(String codigo, double nuevoSalario) {
        Empleado e = buscarPorCodigo(codigo);
        if (e == null) {
            return false;
        }
        e.setSalario(nuevoSalario);
        return true;
    }

    /** Elimina por codigo: localizar O(n), desenlazar O(1). */
    public boolean eliminarPorCodigo(String codigo) {
        if (codigo == null) {
            return false;
        }
        NodoDoble actual = cabeza;
        while (actual != null && !actual.getDato().getCodigo().equals(codigo)) {
            actual = actual.getSiguiente();
        }
        if (actual == null) {
            return false;                     // inexistente o lista vacia
        }
        if (actual.getAnterior() != null) {
            actual.getAnterior().setSiguiente(actual.getSiguiente());
        } else {
            cabeza = actual.getSiguiente();   // era la cabeza
        }
        if (actual.getSiguiente() != null) {
            actual.getSiguiente().setAnterior(actual.getAnterior());
        } else {
            cola = actual.getAnterior();      // era la cola
        }
        tamanio--;
        return true;
    }

    /** Recorrido de cabeza a cola. O(n). */
    public void recorrerAdelante() {
        if (estaVacia()) {
            System.out.println("  (lista vacia)");
            return;
        }
        for (NodoDoble a = cabeza; a != null; a = a.getSiguiente()) {
            System.out.println("  " + a.getDato());
        }
    }

    /** Recorrido de cola a cabeza. O(n). */
    public void recorrerAtras() {
        if (estaVacia()) {
            System.out.println("  (lista vacia)");
            return;
        }
        for (NodoDoble a = cola; a != null; a = a.getAnterior()) {
            System.out.println("  " + a.getDato());
        }
    }
}
