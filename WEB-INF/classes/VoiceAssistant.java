import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VoiceAssistant")

public class VoiceAssistant extends HttpServlet {
    private String id;
    private String name;
    private double price;
    private String image;
    private String retailer;
    private String condition;
    private double discount;
    private int quantity;
    private String sale;
    private String rebate;
    HashMap<String, String> accessories;

    public VoiceAssistant(String name, double price, String image, String retailer, String condition, double discount,
            int quantity, String sale, String rebate) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.retailer = retailer;
        this.condition = condition;
        this.discount = discount;
        this.quantity = quantity;
        this.sale = sale;
        this.rebate = rebate;
        this.accessories = new HashMap<String, String>();
    }

    HashMap<String, String> getAccessories() {
        return accessories;
    }

    public VoiceAssistant() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public void setAccessories(HashMap<String, String> accessories) {
        this.accessories = accessories;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }
}