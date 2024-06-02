package lt.viko.eif.pvaiciulis.StoreSystemClient.utils;

import java.io.*;

public class FileUtil {
    public static void convertToXMLFile(String input, String path) {
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(input);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void readFile(String path) {
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
