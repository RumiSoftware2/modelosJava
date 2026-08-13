package eje1;

import java.util.Scanner;

public class ListaPilaPersonas {

    // Nodo de la lista enlazada
    static class Nodo {
        int codigo;
        String nombre;
        String telefono;
        int edad;
        Nodo siguiente;

        Nodo(int codigo, String nombre, String telefono, int edad) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.telefono = telefono;
            this.edad = edad;
            this.siguiente = null;
        }
    }

    static Nodo cabeza = null; // tope de la pila

    // Insertar al inicio (comportamiento de pila)
    static void insertar(int codigo, String nombre, String telefono, int edad) {
        Nodo nuevo = new Nodo(codigo, nombre, telefono, edad);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    // Eliminar el primer elemento (el tope de la pila)
    static void eliminarPrimero() {
        if (cabeza == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        cabeza = cabeza.siguiente;
    }

    // Mostrar los elementos de la lista
    static void mostrarLista() {
        Nodo actual = cabeza;
        StringBuilder sb = new StringBuilder();
        while (actual != null) {
            sb.append("(").append(actual.codigo).append(", ")
              .append(actual.nombre).append(", ")
              .append(actual.telefono).append(", ")
              .append(actual.edad).append(")");
            if (actual.siguiente != null) sb.append(", ");
            actual = actual.siguiente;
        }
        System.out.println(sb.length() == 0 ? "La lista está vacía." : sb.toString());
    }

    // Contar los elementos de la lista
    static int contarElementos() {
        int contador = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            contador++;
            actual = actual.siguiente;
        }
        return contador;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de personas (N): ");
        int n = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        for (int i = 0; i < n; i++) {
            System.out.println("Persona #" + (i + 1) + ":");
            System.out.print("  Código: ");
            int codigo = sc.nextInt();
            sc.nextLine();
            System.out.print("  Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("  Teléfono: ");
            String telefono = sc.nextLine();
            System.out.print("  Edad: ");
            int edad = sc.nextInt();
            sc.nextLine();

            insertar(codigo, nombre, telefono, edad);
        }

        System.out.println();
        System.out.println("Los elementos de la lista son:");
        mostrarLista();

        System.out.println();
        System.out.println("Retirando el primer elemento de la lista...");
        eliminarPrimero();
        System.out.println("Nuevos elementos de la lista:");
        mostrarLista();

        System.out.println();
        System.out.println("Cantidad de elementos en la lista: " + contarElementos());

        sc.close();
    }
}