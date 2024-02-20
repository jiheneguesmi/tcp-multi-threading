import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 9000; // Port sur lequel le serveur écoute
        
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            ExecutorService executorService = Executors.newFixedThreadPool(2); // Création d'un pool de threads avec 10 threads

            System.out.println("Serveur démarré. Attente de clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Attente de connexion d'un client
                System.out.println("Client connecté: " + clientSocket);

                // Création d'un nouveau thread pour gérer le client
                executorService.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e);
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Simuler un traitement en bloquant le thread pendant 2 secondes
                try {
                    Thread.sleep(200000);
                } catch (InterruptedException e) {
                    System.err.println("Erreur lors de la pause du thread : " + e);
                }

                // Inverser la chaîne de caractères
                String reversedString = new StringBuilder(inputLine).reverse().toString();

                // Envoyer la chaîne inversée au client
                out.println(reversedString);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la communication avec le client : " + e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du socket client : " + e);
            }
        }
    }
}
