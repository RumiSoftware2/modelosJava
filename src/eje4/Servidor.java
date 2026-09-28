package eje4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor de sockets.
 * Recibe 30 datos numericos enteros de 2 cifras enviados
 * uno a uno por el cliente y construye un Arbol Binario
 * de Busqueda con ellos.
 */
public class Servidor {

    private static final int PUERTO = 5000;
    private static final int TOTAL_DATOS = 30;

    public static void main(String[] args) {

        ArbolBinario arbol = new ArbolBinario();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            System.out.println("=== SERVIDOR ===");
            System.out.println("Esperando conexion del cliente en el puerto " + PUERTO + " ...");

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());
            System.out.println();

            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                int contador = 0;
                String linea;

                while (contador < TOTAL_DATOS && (linea = entrada.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty()) {
                        continue;
                    }
                    int valor = Integer.parseInt(linea);
                    contador++;

                    arbol.insertar(valor);
                    System.out.println("Dato #" + contador + " recibido: " + valor + "  -> insertado en el arbol.");

                    salida.println("OK " + contador + " " + valor);
                }

                System.out.println();
                System.out.println("=== Se recibieron los " + contador + " datos y se construyo el arbol ===");
                System.out.println();

                System.out.println("Estructura del arbol (raiz a la izquierda, gira 90 grados):");
                System.out.println("---------------------------------------------------------");
                arbol.imprimirArbol();
                System.out.println("---------------------------------------------------------");
                System.out.println();

                System.out.print("Recorrido InOrden   : ");
                arbol.recorridoInOrden();
                System.out.println();

                System.out.print("Recorrido PreOrden  : ");
                arbol.recorridoPreOrden();
                System.out.println();

                System.out.print("Recorrido PostOrden : ");
                arbol.recorridoPostOrden();
                System.out.println();
                System.out.println();
                System.out.println("Total de nodos en el arbol: " + arbol.getCantidadNodos());
                System.out.println("Fin de la comunicacion con el cliente.");
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}

