import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccessoryList")

public class AccessoryList extends HttpServlet {

	/*
	 * Accessory Page Displays all the Accessories and their Information in Game
	 * Speed
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/*
		 * Checks the Console maker whether it is microsft or sony or nintendo Add the
		 * respective product value to hashmap
		 */

		String CategoryName = request.getParameter("maker");

		// String ConsoleName = request.getParameter("console");
		HashMap<String, Accessory> allAccessories = new HashMap<String, Accessory>();
		HashMap<String, Laptop> allLaptop = new HashMap<String, Laptop>();

		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		try {
			allLaptop = MySqlDataStoreUtilities.getLaptops();
		} catch (Exception e) {

		}

		try {
			allAccessories = MySqlDataStoreUtilities.getAccessories();
		} catch (Exception e) {

		}
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
		if (CategoryName == null) {
			hm.putAll(allLaptop);
			CategoryName = "";
		} else {
			if (CategoryName.equals("apple")) {
				for (Map.Entry<String, Laptop> entry : allLaptop.entrySet()) {
					if (entry.getValue().getRetailer().equals("Apple")) {
						hm.put(entry.getValue().getId(), entry.getValue());

					}
				}

			} else if (CategoryName.equals("dell")) {
				for (Map.Entry<String, Laptop> entry : allLaptop.entrySet()) {
					if (entry.getValue().getRetailer().equals("Dell")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
			} else if (CategoryName.equals("acer")) {
				for (Map.Entry<String, Laptop> entry : allLaptop.entrySet()) {
					if (entry.getValue().getRetailer().equals("Acer")) {
						hm.put(entry.getValue().getId(), entry.getValue());
					}
				}
			}
		}

		// Console console = hm.get(ConsoleName);

		/*
		 * Header, Left Navigation Bar are Printed.
		 * 
		 * All the Accessories and Accessories information are dispalyed in the Content
		 * Section
		 * 
		 * and then Footer is Printed
		 */

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + CategoryName + " Accessories</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = 2;
		for (Map.Entry<String, Laptop> entry : hm.entrySet()) {
			Laptop laptop = entry.getValue();

			for (Map.Entry<String, String> acc : laptop.getAccessories().entrySet()) {

				Accessory accessory = allAccessories.get(acc.getValue());

				if (i % 2 == 1)
					pw.print("<tr>");
				System.out.print(size);
				pw.print("<td><div id='shop_item'>");
				pw.print("<h3>" + accessory.getName() + "</h3>");
				pw.print("<strong>" + accessory.getPrice() + "$</strong><ul>");
				if (accessory.getDiscount() > 0) {
					pw.print("<h3>Discount: " + accessory.getDiscount() + "</h3>");
				}
				pw.print("<li id='item'><img src='images/accessories/" + accessory.getImage() + "' alt='' /></li>");
				pw.print("<li><form method='post' action='Cart'>" + "<input type='hidden' name='name' value='"
						+ acc.getValue() + "'>" + "<input type='hidden' name='type' value='accessories'>"
						+ "<input type='hidden' name='maker' value='" + CategoryName + "'>"
						+ "<input type='hidden' name='access' value='" + laptop.getName() + "'>"
						+ "<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
				pw.print("<li><form method='post' action='WriteReview'>" + "<input type='hidden' name='name' value='"
						+ accessory.getName() + "'>" + "<input type='hidden' name='type' value='accessories'>"
						+ "<input type='hidden' name='maker' value='" + CategoryName + "'>"
						+ "<input type='hidden' name='access' value='" + accessory.getName() + "'>"
						+ "<input type='hidden' name='price' value='" + accessory.getPrice() + "'>"
						+ "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
				pw.print("<li><form method='post' action='ViewReview'>" + "<input type='hidden' name='name' value='"
						+ accessory.getName() + "'>" + "<input type='hidden' name='type' value='accessories'>"
						+ "<input type='hidden' name='maker' value='" + CategoryName + "'>"
						+ "<input type='hidden' name='access' value='" + laptop.getName() + "'>"
						+ "<input type='submit' value='ViewReview' class='btnreview'></form></li>");

				pw.print("</ul></div></td>");
				if (i % 2 == 0 || i == size)
					pw.print("</tr>");
				i++;

			}
		}
		pw.print("</table></div></div></div><div class='clear'></div>");
		utility.printHtml("Footer.html");
	}
}
