package eje3;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Cliente del juego "Adivina el numero".
 * Un hilo (GeneradorNumeros) genera numeros aleatorios y los envia al
 * servidor por medio de un socket cada cierto tiempo. El hilo principal
 * queda escuchando el teclado para poder finalizar la aplicacion cuando
 * el usuario escriba la palabra "terminar".
 */
public class Cliente {

    private static final String HOST = "localhost";
    private static final int PUERTO = 6000;

    // bandera compartida entre el hilo principal y el hilo generador
    private static volatile boolean activo = true;

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST, PUERTO)) {
            System.out.println("Conectado al servidor " + HOST + ":" + PUERTO);

            BufferedReader entradaServidor = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));

            // Se crea y arranca el hilo que genera los numeros aleatorios
            Thread hiloGenerador = new Thread(new GeneradorNumeros(salida, entradaServidor));
            hiloGenerador.start();

            System.out.println("El cliente esta generando numeros automaticamente.");
            System.out.println("Escriba 'terminar' en cualquier momento para finalizar la partida.\n");

            String texto;
            while (activo) {
                texto = entradaTeclado.readLine();
                if (texto != null && texto.equalsIgnoreCase("terminar")) {
                    activo = false;
                    salida.println("terminar");
                    break;
                }
            }

            hiloGenerador.join();
            entradaServidor.close();
            salida.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Cliente finalizado.");
    }

    static boolean isActivo() {
        return activo;
    }
}

/**
 * Hilo encargado de generar numeros aleatorios y enviarlos al servidor
 * a traves del socket, esperando la respuesta antes de generar el siguiente.
 */
class GeneradorNumeros implements Runnable {

    private final PrintWriter salida;
    private final BufferedReader entradaServidor;
    private final Random random = new Random();

    public GeneradorNumeros(PrintWriter salida, BufferedReader entradaServidor) {
        this.salida = salida;
        this.entradaServidor = entradaServidor;
    }

    @Override
    public void run() {
        try {
            while (Cliente.isActivo()) {
                int numero = random.nextInt(101); // numero entre 0 y 100
                System.out.println("[Hilo] Numero generado y enviado: " + numero);
                salida.println(numero);

                String respuesta = entradaServidor.readLine();
                if (respuesta == null || respuesta.equals("FIN")) {
                    break;
                }
                System.out.println("Respuesta del servidor: " + respuesta + "\n");

                Thread.sleep(2000); // espera 2 segundos antes de generar el siguiente numero
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Hilo generador finalizado.");
        }
    }
}