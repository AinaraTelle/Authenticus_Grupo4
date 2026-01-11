package es.deusto.sd.authenticus.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorProcesamientoSocket {

    private static final int PUERTO = 6000;

    public ServidorProcesamientoSocket() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PUERTO);
        System.out.println("Servidor de procesamiento escuchando en puerto " + PUERTO);

        while (true) {
            Socket cliente = serverSocket.accept();
            System.out.println("Cliente de procesamiento conectado: " + cliente.getInetAddress());

            new Thread(() -> manejarCliente(cliente)).start();
        }
    }

    private void manejarCliente(Socket cliente) {
        try (DataInputStream in = new DataInputStream(cliente.getInputStream());
             DataOutputStream out = new DataOutputStream(cliente.getOutputStream())) {

            // siempre activo para múltiples archivos
            while (true) {
                String mensaje = in.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);

                String[] partes = mensaje.split("#");
                if (partes.length != 2) {
                    out.writeUTF("ERROR#Formato mensaje incorrecto");
                    continue;
                }

                String tipoAnalisis = partes[0];
                String nombreArchivo = partes[1];

                // recibir archivo
                long tamañoArchivo = in.readLong();
                byte[] buffer = new byte[(int) tamañoArchivo];
                in.readFully(buffer);

                // guardar temporalmente
                File archivoTemp = new File("temp_" + nombreArchivo);
                try (FileOutputStream fos = new FileOutputStream(archivoTemp)) {
                    fos.write(buffer);
                }

                // procesar archivo
                String resultado = procesarArchivo(archivoTemp, tipoAnalisis);

                // eliminar archivo temporal
                archivoTemp.delete();

                // enviar resultado
                out.writeUTF(resultado);
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado del servidor de procesamiento.");
        }
    }

    private String procesarArchivo(File archivo, String tipoAnalisis) {
        // Simulación de procesamiento
        System.out.println("Procesando archivo: " + archivo.getName() + " para análisis: " + tipoAnalisis);
        try {
            Thread.sleep(2000); // simula tiempo de procesamiento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "RESULTADO_OK#" + archivo.getName();
    }

    public static void main(String[] args) throws IOException {
        new ServidorProcesamientoSocket();
    }
}

