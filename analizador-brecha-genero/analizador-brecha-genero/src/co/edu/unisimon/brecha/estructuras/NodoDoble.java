package co.edu.unisimon.brecha.estructuras;

import co.edu.unisimon.brecha.modelo.Empleado;

/** Nodo de la lista doblemente enlazada: dato + referencia al anterior y al siguiente. */
public class NodoDoble {
    private Empleado dato;
    private NodoDoble anterior;
    private NodoDoble siguiente;

    public NodoDoble(Empleado dato) {
        this.dato = dato;
    }

    public Empleado getDato()             { return dato; }
    public void setDato(Empleado dato)    { this.dato = dato; }
    public NodoDoble getAnterior()        { return anterior; }
    public void setAnterior(NodoDoble a)  { this.anterior = a; }
    public NodoDoble getSiguiente()       { return siguiente; }
    public void setSiguiente(NodoDoble s) { this.siguiente = s; }
}
