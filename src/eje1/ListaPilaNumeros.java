package eje1;

import java.util.Scanner;

public class ListaPilaNumeros {

    // Nodo de la lista enlazada
    static class Nodo {
        int dato;
        Nodo siguiente;

        Nodo(int dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    static Nodo cabeza = null; // tope de la pila
    static int ultimoInsertado; // guarda el último dato que se ingresó

    // Insertar al inicio (comportamiento de pila)
    static void insertar(int valor) {
        Nodo nuevo = new Nodo(valor);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
        ultimoInsertado = valor;
    }

    // Mostrar los datos de la lista
    static void mostrarLista() {
        Nodo actual = cabeza;
        StringBuilder sb = new StringBuilder();
        while (actual != null) {
            sb.append(actual.dato);
            if (actual.siguiente != null) sb.append(", ");
            actual = actual.siguiente;
        }
        System.out.println("Los datos de la lista son: " + sb);
    }

    // Contar cantidad de números pares
    static int contarPares() {
        int contador = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato % 2 == 0) contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    // Calcular el promedio de la lista
    static double calcularPromedio() {
        int suma = 0, cantidad = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            suma += actual.dato;
            cantidad++;
            actual = actual.siguiente;
        }
        return cantidad == 0 ? 0 : (double) suma / cantidad;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de elementos (N): ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Ingrese el dato #" + (i + 1) + ": ");
            int valor = sc.nextInt();
            insertar(valor);
        }

        System.out.println();
        mostrarLista();
        System.out.println("La cantidad de números pares: " + contarPares());
        System.out.printf("El promedio es: %.2f%n", calcularPromedio());
        System.out.println("El último dato de la lista es: " + ultimoInsertado);

        sc.close();
    }
}