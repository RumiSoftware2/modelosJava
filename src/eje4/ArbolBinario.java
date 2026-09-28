package eje4;

/**
 * Implementacion de un Arbol Binario de Busqueda (ABB).
 * Permite insertar valores enteros, recorrer el arbol
 * (InOrden, PreOrden, PostOrden) e imprimir su estructura
 * en consola de forma jerarquica.
 */
public class ArbolBinario {

    private Nodo raiz;
    private int cantidadNodos;

    public ArbolBinario() {
        this.raiz = null;
        this.cantidadNodos = 0;
    }

    /**
     * Inserta un nuevo valor en el arbol siguiendo la regla:
     * menor -> subarbol izquierdo, mayor o igual -> subarbol derecho.
     */
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
        cantidadNodos++;
    }

    private Nodo insertarRecursivo(Nodo actual, int valor) {
        if (actual == null) {
            return new Nodo(valor);
        }
        if (valor < actual.valor) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, valor);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, valor);
        }
        return actual;
    }

    public int getCantidadNodos() {
        return cantidadNodos;
    }

    // ---------------- Recorridos ----------------

    public void recorridoInOrden() {
        recorridoInOrden(raiz);
    }

    private void recorridoInOrden(Nodo nodo) {
        if (nodo != null) {
            recorridoInOrden(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            recorridoInOrden(nodo.derecho);
        }
    }

    public void recorridoPreOrden() {
        recorridoPreOrden(raiz);
    }

    private void recorridoPreOrden(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.valor + " ");
            recorridoPreOrden(nodo.izquierdo);
            recorridoPreOrden(nodo.derecho);
        }
    }

    public void recorridoPostOrden() {
        recorridoPostOrden(raiz);
    }

    private void recorridoPostOrden(Nodo nodo) {
        if (nodo != null) {
            recorridoPostOrden(nodo.izquierdo);
            recorridoPostOrden(nodo.derecho);
            System.out.print(nodo.valor + " ");
        }
    }

    // ---------------- Impresion grafica ----------------

    /**
     * Imprime el arbol "acostado", con la raiz a la izquierda
     * y los niveles hacia la derecha, para poder visualizar
     * su forma en la consola.
     */
    public void imprimirArbol() {
        imprimirArbol(raiz, 0);
    }

    private void imprimirArbol(Nodo nodo, int nivel) {
        if (nodo != null) {
            imprimirArbol(nodo.derecho, nivel + 1);
            StringBuilder sangria = new StringBuilder();
            for (int i = 0; i < nivel; i++) {
                sangria.append("        ");
            }
            System.out.println(sangria + String.valueOf(nodo.valor));
            imprimirArbol(nodo.izquierdo, nivel + 1);
        }
    }
}
