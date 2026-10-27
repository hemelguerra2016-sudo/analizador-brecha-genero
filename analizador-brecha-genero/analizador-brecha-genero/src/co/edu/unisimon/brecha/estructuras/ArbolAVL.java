package co.edu.unisimon.brecha.estructuras;

import co.edu.unisimon.brecha.modelo.Empleado;

/**
 * Arbol AVL propio. Puede ordenarse por codigo (busqueda O(log n)) o por
 * salario (consulta por rango O(log n + k)); en este caso desempata por codigo.
 */
public class ArbolAVL {
    public static final int POR_CODIGO = 1;
    public static final int POR_SALARIO = 2;

    private NodoAVL raiz;
    private int tamanio;
    private final int criterio;
    private boolean insertado;

    public ArbolAVL(int criterio) {
        if (criterio != POR_CODIGO && criterio != POR_SALARIO) {
            throw new IllegalArgumentException("Criterio invalido");
        }
        this.criterio = criterio;
    }

    public boolean estaVacio() { return raiz == null; }
    public int getTamanio()    { return tamanio; }
    public int getAltura()     { return altura(raiz); }

    private int comparar(Empleado a, Empleado b) {
        if (criterio == POR_SALARIO) {
            int c = Double.compare(a.getSalario(), b.getSalario());
            if (c != 0) {
                return c;
            }
        }
        return a.getCodigo().compareTo(b.getCodigo());
    }

    private int altura(NodoAVL n) { return n == null ? 0 : n.getAltura(); }

    private int balance(NodoAVL n) {
        return n == null ? 0 : altura(n.getIzquierdo()) - altura(n.getDerecho());
    }

    private void actualizarAltura(NodoAVL n) {
        n.setAltura(1 + Math.max(altura(n.getIzquierdo()), altura(n.getDerecho())));
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierdo();
        y.setIzquierdo(x.getDerecho());
        x.setDerecho(y);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecho();
        x.setDerecho(y.getIzquierdo());
        y.setIzquierdo(x);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    /** Inserta en O(log n). Devuelve false si la clave ya existe. */
    public boolean insertar(Empleado e) {
        if (e == null) {
            throw new IllegalArgumentException("No se puede insertar un empleado nulo");
        }
        insertado = false;
        raiz = insertar(raiz, e);
        return insertado;
    }

    private NodoAVL insertar(NodoAVL nodo, Empleado e) {
        if (nodo == null) {
            insertado = true;
            tamanio++;
            return new NodoAVL(e);
        }
        int c = comparar(e, nodo.getDato());
        if (c < 0) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), e));
        } else if (c > 0) {
            nodo.setDerecho(insertar(nodo.getDerecho(), e));
        } else {
            return nodo;                                      // duplicado
        }
        actualizarAltura(nodo);
        int b = balance(nodo);
        if (b > 1 && comparar(e, nodo.getIzquierdo().getDato()) < 0) {   // LL
            return rotarDerecha(nodo);
        }
        if (b < -1 && comparar(e, nodo.getDerecho().getDato()) > 0) {    // RR
            return rotarIzquierda(nodo);
        }
        if (b > 1 && comparar(e, nodo.getIzquierdo().getDato()) > 0) {   // LR
            nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
            return rotarDerecha(nodo);
        }
        if (b < -1 && comparar(e, nodo.getDerecho().getDato()) < 0) {    // RL
            nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    /** Busqueda por codigo: O(log n) si el arbol esta ordenado por codigo. */
    public Empleado buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        if (criterio == POR_CODIGO) {
            NodoAVL actual = raiz;
            while (actual != null) {
                int c = codigo.compareTo(actual.getDato().getCodigo());
                if (c == 0) {
                    return actual.getDato();
                }
                actual = (c < 0) ? actual.getIzquierdo() : actual.getDerecho();
            }
            return null;
        }
        return buscarLineal(raiz, codigo);
    }

    private Empleado buscarLineal(NodoAVL n, String codigo) {
        if (n == null) {
            return null;
        }
        if (n.getDato().getCodigo().equals(codigo)) {
            return n.getDato();
        }
        Empleado r = buscarLineal(n.getIzquierdo(), codigo);
        return (r != null) ? r : buscarLineal(n.getDerecho(), codigo);
    }

    /** Recorrido inorden (claves en orden ascendente). O(n). */
    public void inorden() {
        if (estaVacio()) {
            System.out.println("  (arbol vacio)");
            return;
        }
        inorden(raiz);
    }

    private void inorden(NodoAVL n) {
        if (n == null) {
            return;
        }
        inorden(n.getIzquierdo());
        System.out.println("  " + n.getDato());
        inorden(n.getDerecho());
    }

    /** Empleados con salario en [min, max]. O(log n + k) si se ordena por salario. */
    public ListaDobleEmpleados consultarRangoSalarial(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("El minimo no puede ser mayor que el maximo");
        }
        ListaDobleEmpleados res = new ListaDobleEmpleados();
        rango(raiz, min, max, res);
        return res;
    }

    private void rango(NodoAVL n, double min, double max, ListaDobleEmpleados res) {
        if (n == null) {
            return;
        }
        double s = n.getDato().getSalario();
        boolean podar = (criterio == POR_SALARIO);
        if (!podar || s >= min) {
            rango(n.getIzquierdo(), min, max, res);
        }
        if (s >= min && s <= max) {
            res.insertarAlFinal(n.getDato());
        }
        if (!podar || s <= max) {
            rango(n.getDerecho(), min, max, res);
        }
    }
}
