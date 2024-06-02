package lt.viko.eif.pvaiciulis.StoreSystemClient;

import generated.Receipt;

import java.util.List;

public class POJOReceiptPrinter {
    public static void print(Receipt receipt) {
        List<Receipt.Products.Product> products = receipt.getProducts().getProduct();
        Receipt.DiscountCard discountCard = receipt.getDiscountCard();

        System.out.println("Receipt: ");

        System.out.println("\tId: " + receipt.getId());

        System.out.println("\tProducts: " + receipt.getId());

        for (Receipt.Products.Product product : products) {
            System.out.println("\t\tProduct: ");
            System.out.println("\t\t\tName: " + product.getName());
            System.out.println("\t\t\tBarcode: " + product.getBarCode());
            System.out.println("\t\t\tPrice: " + product.getPrice());
            System.out.println("\t\t\tAmountType: " + product.getAmountType());
            System.out.println("\t\t\tAmount: " + product.getAmount());

            Receipt.Products.Product.Discount discount = product.getDiscount();

            if(discount != null) {
                System.out.println("\t\t\tDiscount: ");
                System.out.println("\t\t\t\tDiscountPrice: " + discount.getDiscountPrice());
                System.out.println("\t\t\t\tPercentOff: " + discount.getPercentOff());
                System.out.println("\t\t\t\tCategory: " + discount.getCategory());
            }
        }

        if(discountCard != null) {
            System.out.println("\tDiscount card: ");
            System.out.println("\t\tName: " + discountCard.getName());
            System.out.println("\t\tBarCode: " + discountCard.getBarCode());
            System.out.println("\t\tCategory: " + discountCard.getCategory());

            Receipt.DiscountCard.Person person = discountCard.getPerson();

            System.out.println("\t\tPerson: ");
            System.out.println("\t\t\tFirstName: " + person.getFirstName());
            System.out.println("\t\t\tLastName: " + person.getLastName());
            System.out.println("\t\t\tBirthdate: " + person.getBirthDate());
            System.out.println("\t\t\tPhoneNumber: " + person.getPhoneNumber());
        }

        System.out.println("\tTimeOfPurchase: " + receipt.getTimeOfPurchase());
        System.out.println("\tsubtotal: " + receipt.getSubtotal());
        System.out.println("\ttotal: " + receipt.getTotal());
    }
}
