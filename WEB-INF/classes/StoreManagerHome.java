import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@WebServlet("/StoreManagerHome")
public class StoreManagerHome extends HttpServlet {

        private String error_msg;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
                PrintWriter pw = response.getWriter();

                displayStoreManagerHome(request, response, pw, "");
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
                response.setContentType("text/html");
                PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request, pw);

        }

        private void displayStoreManagerHome(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
                        String flag) {

                Utilities utility = new Utilities(request, pw);
                utility.printHtml("Header.html");
                utility.printHtml("LeftNavigationBar.html");

                pw.print("<div id='content'>");
                pw.print("<div class='post'>");
                pw.print("<h3 class='title'>");
                pw.print("Create New product");
                pw.print("</h3>");
                pw.print("<div class='entry'>");

                if (flag.equals("newProduct"))
                        pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
                //////////////////////////////////////
                pw.print("<form action='ProductCrud' method='get'>");
                pw.print("<table class='gridtable'");

                pw.print("<tr><td>Product ID:</td>");

                pw.print("<td><input type='text' name='productId' value=''></td>");
                pw.print("</tr>");
                // pw.print("<input type='hidden' name='catalog' value='" + catalog + "'>");
                // pw.print("<input type='hidden' name='image' value='" + image + "'>");

                pw.print("<tr><td>Product Name</td><td><input type='text' name='productName' value='' class='input' required></td></tr>");

                pw.print("<tr><td>Product Catalog</td><td><select id='catalog' name='productCatalog' class='input'>"
                                + "<option value='tvs' name='tv'selected>TV</option>"
                                + "<option value='soundsystems' name='soundsystem'>Sound system</option>"
                                + "<option value='phones' name='phone'>Phone</option>"
                                + "<option value='laptops' name='laptop'>Laptop</option>"
                                + "<option value='voiceassistants' name='voiceassistant'>voiceassistant</option>"
                                + "<option value='fitnesswatchs' name='fitnesswatch'>fitnesswatch</option>"
                                + "<option value='smartwatchs' name='smartwatch'>smartwatch</option>"
                                + "<option value='headphones' name='headphone'>headphone</option>"
                                + "<option value='wirelessplans' name='wirelessplan'>wirelessplan</option>"
                                + "<option value='accessories' name='accessorie'>Accessory</option>");
                pw.print("</select></td></tr>");

                pw.print("<tr><td>Product Accessory to:</td><td><input type='text' name='prod' value='' class='input'></td></tr>");

                pw.print("<tr><td>Price</td><td><input type='text' name='price' value='' class='input' required></td></tr>");
                pw.print("<tr><td>Manufacturer</td><td><input type='text' name='manufacturer' value='' class='input' required></td></tr>");

                pw.print("<tr><td>Condition</td><td><select name='condition' class='input'>"
                                + "<option value='New' selected>New</option>" + "<option value='Used'>Used</option>"
                                + "<option value='Refurbished'>Refurbished</option></select></td></tr>");

                pw.print("<tr><td>Image</td><td><img id=\"preview\" /><input type='file' name='image' class='input' required></td></tr>");

                pw.print("<tr><td>Discount</td><td><input type='text' name='discount' value='' class='input' required></td></tr>");

                pw.print("<tr><td></td><td><input type='submit' name='button' class='btnbuy' value='add' style='float: left;height: 20px margin: 20px; margin-right: 10px;'></td></tr>");

                pw.print("</table></form></div></div>");

                pw.print("<div class='post'>");
                // pw.print("<form method='post' action='RemoveUpdateProduct'>");
                pw.print("<h2 class='title meta'>");
                pw.print("<a style='font-size: 24px;'>View Products</a></h2>");
                pw.print("<div class='entry'>");
                pw.print("<table class='gridtable'>");

                if (flag.equals("RemoveUpdateProduct"))
                        pw.print("<h4 style='color:red'>" + error_msg + "</h4>");

                pw.print("<tr>");
                pw.print("<td>Product Name</td>");
                pw.print("<td>Price</td>");
                // pw.print("<td>Manufacturer</td>");
                pw.print("<td>Condition</td>");
                pw.print("<td>Discount</td>");
                pw.print("<td>Catalog</td>");
                pw.print("</tr>");
                for (Map.Entry<String, Tv> entry : MySqlDataStoreUtilities.getTvs().entrySet()) {
                        Tv tv = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + tv.getName() + "</td>" + "<td>" + tv.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + tv.getCondition() + "</td>" + "<td>" + tv.getDiscount() + "</td>"
                                        + "<td>Tv</td>");
                        pw.print("<input type='hidden' name='productId' value='" + tv.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + tv.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + tv.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + tv.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + tv.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + tv.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='tvs'>");
                        pw.print("<input type='hidden' name='image' value='" + tv.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }

                for (Map.Entry<String, SoundSystem> entry : MySqlDataStoreUtilities.getSoundSystem().entrySet()) {
                        SoundSystem soundsystem = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + soundsystem.getName() + "</td>" + "<td>" + soundsystem.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + soundsystem.getCondition() + "</td>" + "<td>"
                                        + soundsystem.getDiscount() + "</td>" + "<td>soundsystem</td>");
                        pw.print("<input type='hidden' name='productId' value='" + soundsystem.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + soundsystem.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + soundsystem.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + soundsystem.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + soundsystem.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + soundsystem.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='soundsystems'>");
                        pw.print("<input type='hidden' name='image' value='" + soundsystem.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, Phone> entry : MySqlDataStoreUtilities.getPhones().entrySet()) {
                        Phone phone = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + phone.getName() + "</td>" + "<td>" + phone.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + phone.getCondition() + "</td>" + "<td>" + phone.getDiscount() + "</td>"
                                        + "<td>phone</td>");
                        pw.print("<input type='hidden' name='productId' value='" + phone.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + phone.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + phone.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + phone.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + phone.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + phone.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='phones'>");
                        pw.print("<input type='hidden' name='image' value='" + phone.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, Laptop> entry : MySqlDataStoreUtilities.getLaptops().entrySet()) {
                        Laptop laptop = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + laptop.getName() + "</td>" + "<td>" + laptop.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + laptop.getCondition() + "</td>" + "<td>" + laptop.getDiscount()
                                        + "</td>" + "<td>Laptop</td>");
                        pw.print("<input type='hidden' name='productId' value='" + laptop.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + laptop.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + laptop.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + laptop.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + laptop.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + laptop.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='laptops'>");
                        pw.print("<input type='hidden' name='image' value='" + laptop.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, VoiceAssistant> entry : MySqlDataStoreUtilities.getVoiceAssistant().entrySet()) {
                        VoiceAssistant voiceassistant = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + voiceassistant.getName() + "</td>" + "<td>" + voiceassistant.getPrice()
                                        + "</td>" +
                                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + voiceassistant.getCondition() + "</td>" + "<td>"
                                        + voiceassistant.getDiscount() + "</td>" + "<td>voiceassistants</td>");
                        pw.print("<input type='hidden' name='productId' value='" + voiceassistant.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + voiceassistant.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + voiceassistant.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + voiceassistant.getRetailer()
                                        + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + voiceassistant.getCondition()
                                        + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + voiceassistant.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='voiceassistant'>");
                        pw.print("<input type='hidden' name='image' value='" + voiceassistant.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, FitnessWatch> entry : MySqlDataStoreUtilities.getFitnessWatchs().entrySet()) {
                        FitnessWatch fitnesswatch = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + fitnesswatch.getName() + "</td>" + "<td>" + fitnesswatch.getPrice() + "</td>"
                                        +
                                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + fitnesswatch.getCondition() + "</td>" + "<td>"
                                        + fitnesswatch.getDiscount() + "</td>" + "<td>fitnesswatch</td>");
                        pw.print("<input type='hidden' name='productId' value='" + fitnesswatch.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + fitnesswatch.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + fitnesswatch.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + fitnesswatch.getRetailer()
                                        + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + fitnesswatch.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + fitnesswatch.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='fitnesswatchs'>");
                        pw.print("<input type='hidden' name='image' value='" + fitnesswatch.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, SmartWatch> entry : MySqlDataStoreUtilities.getSmartWatchs().entrySet()) {
                        SmartWatch smartwatch = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + smartwatch.getName() + "</td>" + "<td>" + smartwatch.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + smartwatch.getCondition() + "</td>" + "<td>" + smartwatch.getDiscount()
                                        + "</td>" + "<td>smartwatch</td>");
                        pw.print("<input type='hidden' name='productId' value='" + smartwatch.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + smartwatch.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + smartwatch.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + smartwatch.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + smartwatch.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + smartwatch.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='smartwatchs'>");
                        pw.print("<input type='hidden' name='image' value='" + smartwatch.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, Headphone> entry : MySqlDataStoreUtilities.getHeadphones().entrySet()) {
                        Headphone headphone = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + headphone.getName() + "</td>" + "<td>" + headphone.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + headphone.getCondition() + "</td>" + "<td>" + headphone.getDiscount()
                                        + "</td>" + "<td>headphone</td>");
                        pw.print("<input type='hidden' name='productId' value='" + headphone.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + headphone.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + headphone.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + headphone.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + headphone.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + headphone.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='headphones'>");
                        pw.print("<input type='hidden' name='image' value='" + headphone.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, WirelessPlan> entry : MySqlDataStoreUtilities.getWirelessPlans().entrySet()) {
                        WirelessPlan wirelessplan = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + wirelessplan.getName() + "</td>" + "<td>" + wirelessplan.getPrice() + "</td>"
                                        +
                                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + wirelessplan.getCondition() + "</td>" + "<td>"
                                        + wirelessplan.getDiscount() + "</td>" + "<td>wirelessplan</td>");
                        pw.print("<input type='hidden' name='productId' value='" + wirelessplan.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + wirelessplan.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + wirelessplan.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + wirelessplan.getRetailer()
                                        + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + wirelessplan.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + wirelessplan.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='wirelessplans'>");
                        pw.print("<input type='hidden' name='image' value='" + wirelessplan.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                for (Map.Entry<String, Accessory> entry : MySqlDataStoreUtilities.getAccessories().entrySet()) {
                        Accessory accessorie = entry.getValue();
                        pw.print("<form method='post' action='RemoveUpdateProduct'>");
                        pw.print("<tr>");
                        // pw.print("<td><input type='radio' name='productId' value='" + laptop.getId()
                        // + "'></td>");

                        pw.print("<td>" + accessorie.getName() + "</td>" + "<td>" + accessorie.getPrice() + "</td>" +
                        // "<td>" + laptop.getRetailer() + "</td>" +
                                        "<td>" + accessorie.getCondition() + "</td>" + "<td>" + accessorie.getDiscount()
                                        + "</td>" + "<td>accessorie</td>");
                        pw.print("<input type='hidden' name='productId' value='" + accessorie.getId() + "'>");
                        pw.print("<input type='hidden' name='productName' value='" + accessorie.getName() + "'>");
                        pw.print("<input type='hidden' name='price' value='" + accessorie.getPrice() + "'>");
                        pw.print("<input type='hidden' name='manufacturer' value='" + accessorie.getRetailer() + "'>");
                        pw.print("<input type='hidden' name='condition' value='" + accessorie.getCondition() + "'>");
                        pw.print("<input type='hidden' name='discount' value='" + accessorie.getDiscount() + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='accessories'>");
                        pw.print("<input type='hidden' name='image' value='" + accessorie.getImage() + "'>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td><div align=\"left\" style=\"float:left\"><input type='submit' name='Product' value='Update' class='btnbuy'></div></td>");
                        pw.print("<td>");
                        pw.print("<div align=\"right\"><input type='submit' name='Product' value='Remove' class='btnbuy'></div></td>");
                        pw.print("</tr>");
                        pw.print("</form>");
                }
                pw.print("</table>");
                pw.print("</div></div></div><div class='clear'></div>");
                utility.printHtml("Footer.html");

        }
}