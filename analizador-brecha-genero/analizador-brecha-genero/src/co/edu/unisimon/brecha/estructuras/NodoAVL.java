package co.edu.unisimon.brecha.estructuras;

import co.edu.unisimon.brecha.modelo.Empleado;

/** Nodo del arbol AVL: dato, altura e hijos izquierdo y derecho. */
public class NodoAVL {
    private Empleado dato;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(Empleado dato) {
        this.dato = dato;
        this.altura = 1;   // una hoja tiene altura 1
    }

    public Empleado getDato()           { return dato; }
    public int getAltura()              { return altura; }
    public void setAltura(int altura)   { this.altura = altura; }
    public NodoAVL getIzquierdo()       { return izquierdo; }
    public void setIzquierdo(NodoAVL n) { this.izquierdo = n; }
    public NodoAVL getDerecho()         { return derecho; }
    public void setDerecho(NodoAVL n)   { this.derecho = n; }
}
