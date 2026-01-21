

package es.deusto.sd.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ProccesServerSocket {

    private static final int PORT = 5000;

    @PostConstruct
    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println(" Socket Server escuchando en puerto " + PORT);

                while (true) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> handleClient(socket)).start();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket socket) {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            String mensaje = in.readUTF();
            System.out.println("Recibido: " + mensaje);

            String[] partes = mensaje.split("#");
            String tipo = partes[0];
            Long idArchivo = Long.parseLong(partes[1]);

            // Simulación de tiempo de análisis (1–3 segundos)
            int tiempo = 1000 + (int) (Math.random() * 2000);
            Thread.sleep(tiempo);

            // Resultado aleatorio
            double[] opciones ={0,1};
            Random random = new Random();
            double resultado = opciones[random.nextInt(opciones.length)];
            out.writeUTF(String.valueOf(resultado));

            System.out.println("Resultado enviado: " + resultado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

