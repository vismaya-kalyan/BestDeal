import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//This servlet is only for removing product (function) and update product (html), not include function of updating product.
@WebServlet("/RemoveUpdateProduct")
public class RemoveUpdateProduct extends HttpServlet {

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

                response.setContentType("text/html");
                PrintWriter pw = response.getWriter();

                Utilities utility = new Utilities(request, pw);

                String productId = request.getParameter("productId");
                String name = request.getParameter("productName");
                String price = request.getParameter("price");
                String manufacturer = request.getParameter("manufacturer");
                String condition = request.getParameter("condition");
                String discount = request.getParameter("discount");
                String catalog = request.getParameter("productCatalog");
                String image = request.getParameter("image");

                if (request.getParameter("Product") != null && request.getParameter("Product").equals("Remove")) {
                        // // Remove Product
                        // System.out.println("before remove prod" + productId);
                        // if (utility.removeProduct(productId, catalog)) {
                        // response.sendRedirect("StoreManagerHome");
                        // }
                        utility.printHtml("Header.html");
                        utility.printHtml("LeftNavigationBar.html");

                        pw.print("<div id='content'>");
                        pw.print("<div class='post'>");
                        pw.print("<h3 class='title'>");
                        pw.print("Are sure you want to delete this product?");
                        pw.print("</h3>");
                        pw.print("<div class='entry'>");

                        pw.print("<form action='ProductCrud' method='get'");
                        pw.print("<table style='width:100%'><tr><td>");

                        pw.print("<h4>Product ID: " + productId + "</h4></td>");
                        pw.print("</tr><br><tr><td>");
                        pw.print("<input type='hidden'  name='productId' value='" + productId + "'>");
                        pw.print("<input type='hidden' name='productCatalog' value='" + catalog + "'>");
                        pw.print("<input type='hidden' name='image' value='" + image + "'>");

                        pw.print("<h4>Product Name</h4></td><td><input type='text' readonly='readonly' name='productName' value='"
                                        + name + "' class='input' ></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<h4>Price</h4></td><td><input type='text' readonly='readonly' name='price' value='"
                                        + price + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");
                        pw.print("<h4>Manufacturer</h4></td><td><input type='text' readonly='readonly' name='manufacturer' value='"
                                        + manufacturer + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<h4>Discount</h4></td><td><input type='text' readonly='readonly' name='discount' value='"
                                        + discount + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<input type='submit' class='btnbuy' name='button' value='delete' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>");
                        pw.print("</td></tr><tr><td></td><td>");
                        pw.print("</td></tr></table>");
                        pw.print("</form></div></div></div><div class='clear'></div>");
                        utility.printHtml("Footer.html");

                } else if (request.getParameter("Product") != null
                                && request.getParameter("Product").equals("Update")) {
                        // Update Product

                        utility.printHtml("Header.html");
                        utility.printHtml("LeftNavigationBar.html");

                        pw.print("<div id='content'>");
                        pw.print("<div class='post'>");
                        pw.print("<h3 class='title'>");
                        pw.print("Update product");
                        pw.print("</h3>");
                        pw.print("<div class='entry'>");

                        pw.print("<form action='ProductCrud' method='get'");
                        pw.print("<table style='width:100%'><tr><td>");

                        pw.print("<h4>Product ID: " + productId + "</h4></td>");
                        pw.print("</tr><br><tr><td>");
                        pw.print("<input type='hidden' name='productId' value='" + productId + "'>");
                        pw.print("<input type='hidden' name='catalog' value='" + catalog + "'>");
                        pw.print("<input type='hidden' name='image' value='" + image + "'>");

                        pw.print("<h4>Product Name</h4></td><td><input type='text' name='productName' value='" + name
                                        + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<h4>Product Catalog</h4><td><select id='catalog' name='productCatalog' class='input'>"
                                        + "<option value='tvs' name='tv'selected>TV</option>"
                                        + "<option value='soundsystems' name='soundsystem'>Sound system</option>"
                                        + "<option value='phones' name='phone'>Phone</option>"
                                        + "<option value='laptops' name='laptop'>Laptop</option>"
                                        + "<option value='voiceassistants' name='voiceassistant'>voiceassistant</option>"
                                        + "<option value='fitnesswatchs' name='fitnesswatch'>fitnesswatch</option>"
                                        + "<option value='smartwatchs' name='smartwatch'>smartwatch</option>"
                                        + "<option value='headphones' name='headphone'>headphone</option>"
                                        + "<option value='wirelessplans' name='wirelessplan'>wirelessplan</option>"
                                        + "<option value='laptops' name='accessorie'>Accessory</option>");
                        pw.print("</select></td></tr></td><tr><td>");

                        pw.print("<h4>Price</h4></td><td><input type='text' name='price' value='" + price
                                        + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");
                        pw.print("<h4>Manufacturer</h4></td><td><input type='text' name='manufacturer' value='"
                                        + manufacturer + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<h4>Condition</h4><td><select name='condition' class='input'>"
                                        + "<option value='New' selected>New</option>"
                                        + "<option value='Used'>Used</option>"
                                        + "<option value='Refurbished'>Refurbished</option></select>");
                        pw.print("</td></tr></td><tr><td>");

                        pw.print("<h4>Discount</h4></td><td><input type='text' name='discount' value='" + discount
                                        + "' class='input' required></input>");
                        pw.print("</td></tr><tr><td>");

                        pw.print("<input type='submit' class='btnbuy' name='button' value='update' style='float: right;height: 20px margin: 20px; margin-right: 10px;'></input>");
                        pw.print("</td></tr><tr><td></td><td>");
                        pw.print("</td></tr></table>");
                        pw.print("</form></div></div></div><div class='clear'></div>");
                        utility.printHtml("Footer.html");

                }
        }
}
