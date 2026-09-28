package eje4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Cliente de sockets.
 * Genera 30 datos numericos enteros de 2 cifras (10 a 99)
 * y los envia uno a uno al servidor, esperando la
 * confirmacion de cada envio.
 */
public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;
    private static final int TOTAL_DATOS = 30;

    public static void main(String[] args) {

        Random random = new Random();

        try (Socket socket = new Socket(HOST, PUERTO)) {

            System.out.println("=== CLIENTE ===");
            System.out.println("Conectado al servidor " + HOST + ":" + PUERTO);
            System.out.println();

            try (PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                for (int i = 1; i <= TOTAL_DATOS; i++) {
                    int valor = 10 + random.nextInt(90); // numero de 2 cifras: 10 - 99

                    salida.println(valor);
                    System.out.println("Dato #" + i + " enviado: " + valor);

                    String respuesta = entrada.readLine();
                    System.out.println("   Confirmacion del servidor: " + respuesta);

                    Thread.sleep(150); // pequena pausa para poder ver el envio dato a dato
                }

                System.out.println();
                System.out.println("Se enviaron los " + TOTAL_DATOS + " datos correctamente.");
            }

        } catch (IOException e) {
            System.out.println("Error en el cliente: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Cliente interrumpido: " + e.getMessage());
        }
    }
}
