package eje2;

import java.util.Scanner;

/**
 * Actividad Evaluativa - Eje 2
 * Taller: Creacion de estructuras de tipo arbol.
 *
 * Implementa un Arbol Binario de Busqueda (ABB) construido a mano,
 * sin usar clases de la coleccion estandar de Java (List, TreeSet, etc.).
 * Toda la logica de insercion, recorridos y busqueda esta programada
 * desde cero usando la clase Nodo y recursividad.
 */
public class ArbolBinarioBusqueda {

    // ---------------------------------------------------------
    // Clase interna que representa un nodo del arbol
    // ---------------------------------------------------------
    static class Nodo {
        int valor;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(int valor) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    // Raiz del arbol
    private Nodo raiz;

    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    // ---------------------------------------------------------
    // 1) Insercion de un nodo en el arbol
    // ---------------------------------------------------------
    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo nodo, int valor) {
        if (nodo == null) {
            return new Nodo(valor);
        }
        if (valor < nodo.valor) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = insertarRecursivo(nodo.derecho, valor);
        }
        // Si el valor ya existe, no se inserta duplicado
        return nodo;
    }

    // ---------------------------------------------------------
    // 2) Recorrido en orden (in-order): izquierda, raiz, derecha
    // ---------------------------------------------------------
    public void visualizarEnOrden() {
        System.out.println("Recorrido en orden");
        enOrden(raiz);
        System.out.println();
    }

    private void enOrden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        enOrden(nodo.izquierdo);
        System.out.print(nodo.valor + " ");
        enOrden(nodo.derecho);
    }

    // ---------------------------------------------------------
    // 3) Nodos con dos hijos, recorridos en orden
    // ---------------------------------------------------------
    public void visualizarNodosConDosHijos() {
        System.out.println("Los que tiene 2 hijos:");
        dosHijosEnOrden(raiz);
        System.out.println();
    }

    private void dosHijosEnOrden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        dosHijosEnOrden(nodo.izquierdo);
        if (nodo.izquierdo != null && nodo.derecho != null) {
            System.out.print(nodo.valor + " ");
        }
        dosHijosEnOrden(nodo.derecho);
    }

    // ---------------------------------------------------------
    // 4) Nodos que tienen por lo menos un hijo con valor par,
    //    recorridos en preorden (raiz, izquierda, derecha)
    // ---------------------------------------------------------
    public void visualizarNodosConHijoPar() {
        System.out.println("Los nodos que tiene 1 hijo par");
        int cantidad = hijoParPreOrden(raiz);
        System.out.println();
        System.out.println("Cantidad de nodos con al menos un hijo par: " + cantidad);
    }

    // Recorre en preorden, imprime cada nodo que cumple la condicion
    // y devuelve la cantidad total encontrada.
    private int hijoParPreOrden(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        int contador = 0;
        boolean tieneHijoPar = false;

        if (nodo.izquierdo != null && nodo.izquierdo.valor % 2 == 0) {
            tieneHijoPar = true;
        }
        if (nodo.derecho != null && nodo.derecho.valor % 2 == 0) {
            tieneHijoPar = true;
        }

        if (tieneHijoPar) {
            System.out.print(nodo.valor + " ");
            contador = 1;
        }

        contador += hijoParPreOrden(nodo.izquierdo);
        contador += hijoParPreOrden(nodo.derecho);
        return contador;
    }

    // ---------------------------------------------------------
    // 5) Suma de los hijos de cada nodo, recorridos en preorden
    // ---------------------------------------------------------
    public void visualizarSumaDeHijos() {
        System.out.println("Suma de sus hijos");
        sumaHijosPreOrden(raiz);
        System.out.println();
    }

    private void sumaHijosPreOrden(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        int suma = 0;
        if (nodo.izquierdo != null) {
            suma += nodo.izquierdo.valor;
        }
        if (nodo.derecho != null) {
            suma += nodo.derecho.valor;
        }
        System.out.print(suma + " ");

        sumaHijosPreOrden(nodo.izquierdo);
        sumaHijosPreOrden(nodo.derecho);
    }

    // ---------------------------------------------------------
    // 6) Camino (desde la raiz) para llegar a un nodo X
    // ---------------------------------------------------------
    public void mostrarCamino(int objetivo) {
        // Se construye el camino manualmente con un arreglo,
        // ya que la profundidad maxima del arbol es a lo sumo N.
        int[] camino = new int[contarNodos(raiz)];
        int[] longitud = new int[1]; // truco para "pasar por referencia" un entero

        boolean encontrado = buscarCamino(raiz, objetivo, camino, longitud);

        if (!encontrado) {
            System.out.println("El nodo no existe");
            return;
        }

        System.out.print("El camino es: ");
        for (int i = 0; i < longitud[0]; i++) {
            System.out.print(camino[i] + " ");
        }
        System.out.println();
    }

    private boolean buscarCamino(Nodo nodo, int objetivo, int[] camino, int[] longitud) {
        if (nodo == null) {
            return false;
        }

        camino[longitud[0]] = nodo.valor;
        longitud[0]++;

        if (nodo.valor == objetivo) {
            return true;
        }

        boolean encontrado;
        if (objetivo < nodo.valor) {
            encontrado = buscarCamino(nodo.izquierdo, objetivo, camino, longitud);
        } else {
            encontrado = buscarCamino(nodo.derecho, objetivo, camino, longitud);
        }

        // Si no se encontro por ese camino, se retrocede (se "quita" el nodo)
        if (!encontrado) {
            longitud[0]--;
        }
        return encontrado;
    }

    // Cuenta la cantidad de nodos del arbol (usado para dimensionar el arreglo del camino)
    private int contarNodos(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    // ---------------------------------------------------------
    // Metodo principal
    // ---------------------------------------------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArbolBinarioBusqueda arbol = new ArbolBinarioBusqueda();

        System.out.println("Entrada de datos:");
        int n = Integer.parseInt(sc.nextLine().trim());

        System.out.println("Ingrese los " + n + " valores separados por espacio:");
        String[] datos = sc.nextLine().trim().split("\\s+");

        for (int i = 0; i < n; i++) {
            arbol.insertar(Integer.parseInt(datos[i]));
        }

        // 1. Recorrido en orden
        arbol.visualizarEnOrden();

        // 2. Nodos con dos hijos (en orden)
        arbol.visualizarNodosConDosHijos();

        // 3. Nodos con al menos un hijo par (en preorden)
        arbol.visualizarNodosConHijoPar();

        // 4. Suma de los hijos de cada nodo (en preorden)
        arbol.visualizarSumaDeHijos();

        // 5. Camino hacia un nodo X
        System.out.println("Escriba el nodo a buscar");
        int objetivo = Integer.parseInt(sc.nextLine().trim());
        arbol.mostrarCamino(objetivo);

        sc.close();
    }
}
