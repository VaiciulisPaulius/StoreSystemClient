package lt.viko.eif.pvaiciulis.StoreSystemClient;

public class Main {
    public static void main(String[] args) {
        Client.init("WTS2", 10000, 50, 1);
        Client.listen();
    }
}