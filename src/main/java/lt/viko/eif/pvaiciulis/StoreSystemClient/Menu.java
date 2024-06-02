package lt.viko.eif.pvaiciulis.StoreSystemClient;

import generated.Receipt;
import lt.viko.eif.pvaiciulis.StoreSystemClient.utils.FileUtil;
import lt.viko.eif.pvaiciulis.StoreSystemClient.utils.JaxbUtilGeneric;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class Menu {

    private static Receipt receipt;
    public static void displayMenu() {
        System.out.println("Make a selection from server");
        System.out.println("-------------");
        System.out.printf("| 1) %10s \n", "Display receipt properties from server");
        System.out.printf("| 2) %10s \n", "Download receipt");

        System.out.println("Make a selection from client");
        System.out.println("-------------");
        System.out.printf("| 3) %10s \n", "Display POJO receipt properties");
        System.out.printf("| 4) %10s \n", "Display receipt in XML");
        System.out.printf("| 5) %10s \n", "Reconvert POJO receipt to XML and display it");
        System.out.printf("| 6) %10s \n", "Validate receipt");
        System.out.printf("| 7) %10s \n", "Validate receipt with incorrect XSD");
    }

    public static void executeCommands(String userInput, BufferedReader in, PrintWriter out) {
        try {
            if(Integer.parseInt(userInput) == 1) {
                out.println(userInput);
                String output = Client.getWholeStreamOutput(in);

                System.out.println(output);
            }
            if(Integer.parseInt(userInput) == 2) {
                out.println(userInput);
                String output = Client.getWholeStreamOutput(in);

                FileUtil.convertToXMLFile(output, "src/main/resources/generated.xml");
                receipt = new Receipt();
                receipt = JaxbUtilGeneric.convertToPOJO(receipt.getClass(), "src/main/resources/generated.xml");
                System.out.println("Receipt downloaded.");
            }
            if(Integer.parseInt(userInput) == 3) {
                if(receipt == null) {
                    System.out.println("Receipt does not exist.");
                }
                else {
                    POJOReceiptPrinter.print(receipt);
                }
            }
            if(Integer.parseInt(userInput) == 4) {
                if(receipt == null) {
                    System.out.println("Receipt does not exist.");
                }
                else {
                    FileUtil.readFile("src/main/resources/generated.xml");
                }
            }
            if(Integer.parseInt(userInput) == 5) {
                if(receipt == null) {
                    System.out.println("Receipt does not exist.");
                }
                else {
                    JaxbUtilGeneric.convertToXML(receipt, "src/main/resources/convertedReceipt.xml");
                    FileUtil.readFile("src/main/resources/convertedReceipt.xml");
                }
            }
            if(Integer.parseInt(userInput) == 6) {
                if(receipt == null) {
                    System.out.println("Receipt does not exist.");
                }
                else {
                    boolean isValid = XMLValidator.validateXMLSchema("src/main/resources/generated.xsd", "src/main/resources/generated.xml");

                    if(isValid) System.out.println("XML file is valid!");
                    else System.out.println("XML file is not valid!");
                }
            }
            if(Integer.parseInt(userInput) == 7) {
                if(receipt == null) {
                    System.out.println("Receipt does not exist.");
                }
                else {
                    boolean isValid = XMLValidator.validateXMLSchema("src/main/resources/wrong.xsd", "src/main/resources/generated.xml");

                    if(isValid) System.out.println("XML file is valid!");
                    else System.out.println("XML file is not valid!");
                }
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        Menu.displayMenu();
    }
}
