package eje3;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * Servidor del juego "Adivina el numero".
 * Recibe numeros aleatorios generados por el cliente (mediante un hilo)
 * y trata de adivinarlos generando su propio numero aleatorio.
 * Contabiliza aciertos y desaciertos. Si el servidor falla 3 veces
 * seguidas, se muestra el mensaje "Perdiste". El juego finaliza cuando
 * el cliente envia la palabra "terminar".
 */
public class Servidor {

    private static final int PUERTO = 6000;

    public static void main(String[] args) {
        System.out.println("=== SERVIDOR INICIADO ===");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Esperando conexion del cliente en el puerto " + PUERTO + "...");

            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());

            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            Random random = new Random();
            int aciertos = 0;
            int desaciertos = 0;
            int desaciertosConsecutivos = 0;

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {

                // Condicion de finalizacion
                if (mensaje.equalsIgnoreCase("terminar")) {
                    System.out.println("\nEl cliente solicito finalizar la partida.");
                    salida.println("FIN");
                    break;
                }

                try {
                    int numeroCliente = Integer.parseInt(mensaje.trim());
                    int intentoServidor = random.nextInt(101); // numero entre 0 y 100

                    System.out.println("Numero recibido del cliente: " + numeroCliente
                            + "  |  Intento del servidor: " + intentoServidor);

                    if (intentoServidor == numeroCliente) {
                        aciertos++;
                        desaciertosConsecutivos = 0;
                        String respuesta = "ACIERTO -> El servidor adivino el numero " + intentoServidor;
                        System.out.println(respuesta);
                        salida.println(respuesta);
                    } else {
                        desaciertos++;
                        desaciertosConsecutivos++;
                        String respuesta = "DESACIERTO -> El servidor intento " + intentoServidor
                                + " y el numero del cliente era " + numeroCliente;
                        System.out.println(respuesta);
                        salida.println(respuesta);

                        if (desaciertosConsecutivos == 3) {
                            System.out.println("Perdiste");
                            salida.println("Perdiste");
                            desaciertosConsecutivos = 0; // se reinicia el conteo para continuar jugando
                        }
                    }

                } catch (NumberFormatException e) {
                    salida.println("ERROR: dato invalido recibido");
                }
            }

            System.out.println("\n=== RESULTADOS FINALES ===");
            System.out.println("Total de aciertos:    " + aciertos);
            System.out.println("Total de desaciertos: " + desaciertos);

            entrada.close();
            salida.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Servidor finalizado.");
    }
}