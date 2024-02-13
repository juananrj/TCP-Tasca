import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Ingrese la dirección IP del servidor: ");
            String serverIP = scanner.nextLine();

            System.out.print("Ingrese el puerto del servidor: ");
            int serverPort = scanner.nextInt();
            scanner.nextLine(); // Limpiar el búfer de entrada

            System.out.print("Ingrese su nombre de usuario: ");
            String username = scanner.nextLine();

            System.out.println("Ingrese hasta 10 números separados por espacios. Ingrese -1 para terminar:");
            String input = scanner.nextLine();
            List<Integer> numbers = parseInput(input);

            Socket socket = new Socket(serverIP, serverPort);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            Llista listToSend = new Llista(username, numbers);
            outToServer.writeObject(listToSend);
            outToServer.flush();

            Llista receivedList = (Llista) inFromServer.readObject();
            System.out.println("Lista recibida del servidor:");
            System.out.println("Nombre: " + receivedList.getNom());
            System.out.println("Números ordenados y sin repetir: " + receivedList.getNumberList());

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> parseInput(String input) {
        String[] tokens = input.split("\\s+");
        List<Integer> numbers = new ArrayList<>();
        for (String token : tokens) {
            if (token.equals("-1")) {
                break;
            }
            numbers.add(Integer.parseInt(token));
            if (numbers.size() >= 10) {
                break;
            }
        }
        return numbers;
    }
}
