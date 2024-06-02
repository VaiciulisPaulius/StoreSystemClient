package lt.viko.eif.pvaiciulis.StoreSystemClient;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static final String OUTPUT_ENDPOINT = "@123!^589";
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static void initializeStreams() throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void init(String hostname, int portNumber, int maxRetries, int delayBetweenRetries) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                System.out.println("Attempt " + (attempt + 1) + " to connect to " + hostname + ":" + portNumber);
                socket = new Socket(hostname, portNumber);
                socket.setSoTimeout(1000);
                initializeStreams();
                System.out.println("Connected to " + hostname + " on port " + portNumber);
                break; // Exit loop if connection is successful
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostname);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + hostname + ". Attempt " + (attempt + 1) + " failed.");
            }

            attempt++;
            if (attempt < maxRetries) {
                try {
                    System.out.println("Retrying in " + delayBetweenRetries + "ms...");
                    Thread.sleep(delayBetweenRetries);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    System.err.println("Retry sleep interrupted");
                }
            } else {
                System.err.println("Exceeded maximum retry attempts. Could not connect to " + hostname);
            }
        }
    }
    public static void listen() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            Menu.displayMenu();

            String userInput;
            while((userInput = stdIn.readLine()) != null) {
                Menu.executeCommands(userInput, in, out);
            }
        }
        catch(UnknownHostException e) {
            System.err.println("Dont know about host");
        } catch(IOException e) {
            System.err.println(e.getMessage());
            reconnect();
        }
    }
    private static void reconnect() {
        System.out.println("Attempting to reconnect to the server...");
        init("WTS2", 10000, 50, 1); // Replace with actual hostname and port number
    }
    public static String getWholeStreamOutput(BufferedReader in) {
        String output;
        String wholeOutput = "";

        try {
            while ((output = in.readLine()) != null) {
                if(output.equals(OUTPUT_ENDPOINT)) break;
                wholeOutput += output + "\n";
            }
            return wholeOutput;
        } catch(IOException e) {
            System.err.println(e.getMessage());
            reconnect();
            return null;
        }
    }
}
