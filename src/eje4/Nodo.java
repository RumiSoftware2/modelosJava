package eje4;

/**
 * Representa un nodo del arbol binario.
 * Cada nodo guarda un valor entero y una referencia
 * a su hijo izquierdo y a su hijo derecho.
 */
public class Nodo {

    int valor;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(int valor) {
        this.valor = valor;
        this.izquierdo = null;
        this.derecho = null;
    }
}
