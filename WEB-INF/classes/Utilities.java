import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/Utilities")

/*
 * Utilities class contains class variables of type HttpServletRequest,
 * PrintWriter,String and HttpSession.
 * 
 * Utilities class has a constructor with HttpServletRequest, PrintWriter
 * variables.
 * 
 */

public class Utilities extends HttpServlet {
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;

	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	/*
	 * Printhtml Function gets the html file name as function Argument, If the html
	 * file name is Header.html then It gets Username from session variables.
	 * Account ,Cart Information ang Logout Options are Displayed
	 */

	public void printHtml(String file) {
		String result = HtmlToString(file);
		// to print the right navigation in header of username cart and logout etc
		if (file == "Header.html") {
			result = result + "<div id='menu' style='float: right;'><ul>";
			if (session.getAttribute("username") != null) {
				String username = session.getAttribute("username").toString();
				username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
				String userType = session.getAttribute("usertype").toString();
				switch (userType) {
				case "customer":
					result = result + "<li><a href='ViewOrder'><span class='glyphicon'>ViewOrder</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
							+ "<li><a href='Account'><span class='glyphicon'>Account</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
					break;
				case "manager":
					result = result
							+ "<li><a href='StoreManagerHome'><span class='glyphicon'>ViewProduct</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
							+ "<li><a href='DataVisualization'><span class='glyphicon'>Trending</span></a></li>"
							+ "<li><a href='Inventory'><span class='glyphicon'>Inventory</span></a></li>"
							+ "<li><a href='SalesReport'><span class='glyphicon'>SalesReport</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
					break;
				case "retailer":
					result = result + "<li><a href='SalesmanHome'><span class='glyphicon'>AddCustomer</span></a></li>"
							+ "<li><a><span class='glyphicon'>Hello, " + username + "</span></a></li>"
							+ "<li><a href='Logout'><span class='glyphicon'>Logout</span></a></li>";
					break;
				}
			} else {
				result = result + "<li><a href='ViewOrder'><span class='glyphicon'>View Order</span></a></li>"
						+ "<li><a href='Login'><span class='glyphicon'>Login</span></a></li>";
			}
			result = result + "<li><a href='Cart'><span class='glyphicon'>Cart(" + CartCount()
					+ ")</span></a></li></ul></div></div><div id='page'>" + "</nav>";
			pw.print(result);
		} else
			pw.print(result);
	}

	// + "<li><a href='DataVisualization'><span
	// class='glyphicon'>Trending</span></a></li>"
	// + "<li><a href='DataAnalytics'><span
	// class='glyphicon'>DataAnalytics</span></a></li>"

	/* getFullURL Function - Reconstructs the URL user request */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*
	 * HtmlToString - Gets the Html file and Converts into String and returns the
	 * String.
	 */
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		} catch (Exception e) {
		}
		return result;
	}

	/*
	 * logout Function removes the username , usertype attributes from the session
	 * variable
	 */

	public void logout() {
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	/* logout Function checks whether the user is loggedIn or Not */

	public boolean isLoggedin() {
		if (session.getAttribute("username") == null)
			return false;
		return true;
	}

	/* username Function returns the username from the session variable. */

	public String username() {
		if (session.getAttribute("username") != null)
			return session.getAttribute("username").toString();
		return null;
	}

	/* usertype Function returns the usertype from the session variable. */
	public String usertype() {
		if (session.getAttribute("usertype") != null)
			return session.getAttribute("usertype").toString();
		return null;
	}

	/*
	 * getUser Function checks the user is a customer or retailer or manager and
	 * returns the user class variable.
	 */
	public User getUser() {
		String usertype = usertype();
		HashMap<String, User> hm = new HashMap<String, User>();
		try {
			hm = MySqlDataStoreUtilities.selectUser();
		} catch (Exception e) {
		}
		User user = hm.get(username());
		return user;
	}

	/* getCustomerOrders Function gets the Orders for the user */
	public ArrayList<OrderItem> getCustomerOrders() {
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if (OrdersHashMap.orders.containsKey(username()))
			order = OrdersHashMap.orders.get(username());
		return order;
	}

	/* getOrdersPaymentSize Function gets the size of OrderPayment */
	public int getOrderPaymentSize() {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		int size = 0;
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();

		} catch (Exception e) {

		}
		for (Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
			size = entry.getKey();
		}

		return size;
	}

	/* CartCount Function gets the size of User Orders */
	public int CartCount() {
		if (isLoggedin())
			return getCustomerOrders().size();
		return 0;
	}

	/*
	 * StoreProduct Function stores the Purchased product in Orders HashMap
	 * according to the User Names.
	 */

	public void storeProduct(String name, String type, String maker, String acc) {
		if (!OrdersHashMap.orders.containsKey(username())) {
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		if (type.equals("tvs")) {
			Tv tv;
			tv = SaxParserDataStore.tvs.get(name);
			OrderItem orderitem = new OrderItem(tv.getName(), tv.getPrice(), tv.getImage(), tv.getRetailer(),
					tv.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("soundsystems")) {
			SoundSystem soundsystem;
			soundsystem = SaxParserDataStore.soundsystems.get(name);
			OrderItem orderitem = new OrderItem(soundsystem.getName(), soundsystem.getPrice(), soundsystem.getImage(),
					soundsystem.getRetailer(), soundsystem.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("phones")) {
			Phone phone;
			phone = SaxParserDataStore.phones.get(name);
			OrderItem orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(),
					phone.getRetailer(), phone.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("laptops")) {
			Laptop laptop;
			laptop = SaxParserDataStore.laptops.get(name);
			OrderItem orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(),
					laptop.getRetailer(), laptop.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("voiceassistants")) {
			VoiceAssistant voiceassistant;
			voiceassistant = SaxParserDataStore.voiceassistants.get(name);
			OrderItem orderitem = new OrderItem(voiceassistant.getName(), voiceassistant.getPrice(),
					voiceassistant.getImage(), voiceassistant.getRetailer(), voiceassistant.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("fitnesswatchs")) {
			FitnessWatch fitnesswatch;
			fitnesswatch = SaxParserDataStore.fitnesswatchs.get(name);
			OrderItem orderitem = new OrderItem(fitnesswatch.getName(), fitnesswatch.getPrice(),
					fitnesswatch.getImage(), fitnesswatch.getRetailer(), fitnesswatch.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("smartwatchs")) {
			SmartWatch smartwatch;
			smartwatch = SaxParserDataStore.smartwatchs.get(name);
			OrderItem orderitem = new OrderItem(smartwatch.getName(), smartwatch.getPrice(), smartwatch.getImage(),
					smartwatch.getRetailer(), smartwatch.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("headphones")) {
			Headphone headphone;
			headphone = SaxParserDataStore.headphones.get(name);
			OrderItem orderitem = new OrderItem(headphone.getName(), headphone.getPrice(), headphone.getImage(),
					headphone.getRetailer(), headphone.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("wirelessplans")) {
			WirelessPlan wirelessplan;
			wirelessplan = SaxParserDataStore.wirelessplans.get(name);
			OrderItem orderitem = new OrderItem(wirelessplan.getName(), wirelessplan.getPrice(),
					wirelessplan.getImage(), wirelessplan.getRetailer(), wirelessplan.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("consoles")) {
			Console console;
			console = SaxParserDataStore.consoles.get(name);
			OrderItem orderitem = new OrderItem(console.getName(), console.getPrice(), console.getImage(),
					console.getRetailer(), console.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("games")) {
			Game game = null;
			game = SaxParserDataStore.games.get(name);
			OrderItem orderitem = new OrderItem(game.getName(), game.getPrice(), game.getImage(), game.getRetailer(),
					game.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("tablets")) {
			Tablet tablet = null;
			tablet = SaxParserDataStore.tablets.get(name);
			OrderItem orderitem = new OrderItem(tablet.getName(), tablet.getPrice(), tablet.getImage(),
					tablet.getRetailer(), tablet.getDiscount());
			orderItems.add(orderitem);
		}
		if (type.equals("accessories")) {
			Accessory accessory = SaxParserDataStore.accessories.get(name);
			OrderItem orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(),
					accessory.getRetailer(), accessory.getDiscount());
			orderItems.add(orderitem);
		}

	}

	// delete product
	public void removeItemFromCart(String itemName) {
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		int index = 0;

		for (OrderItem oi : orderItems) {
			if (oi.getName().equals(itemName)) {
				break;
			} else
				index++;
		}
		orderItems.remove(index);
	}

	// store the payment details for orders
	public void storePayment(int orderId, String orderName, double orderPrice, String userAddress,
			String creditCardNo) {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();

		// get the payment details file
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		} catch (Exception e) {

		}
		if (orderPayments == null) {
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
		// if there exist order id already add it into same list for order id or create
		// a new record with order id

		if (!orderPayments.containsKey(orderId)) {
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
		OrderPayment orderpayment = new OrderPayment(orderId, username(), orderName, orderPrice, userAddress,
				creditCardNo);
		listOrderPayment.add(orderpayment);

		// add order details into file

		try {
			MySqlDataStoreUtilities.insertOrder(orderId, username(), orderName, orderPrice, userAddress, creditCardNo);
		} catch (Exception e) {
			System.out.println("inside exception file not written properly");
		}
	}

	public boolean isContainsStr(String string) {
		String regex = ".*[a-zA-Z]+.*";
		Matcher m = Pattern.compile(regex).matcher(string);
		return m.matches();
	}

	// public boolean isItemExist(String itemCatalog, String itemName) {

	// HashMap<String, Object> hm = new HashMap<String, Object>();
	// HashMap<String, Laptop> allLaptops = new HashMap<String, Laptop>();
	// allLaptops = MySqlDataStoreUtilities.getLaptops();
	// switch (itemCatalog) {

	// case "laptop":
	// hm.putAll(SaxParserDataStore.laptops);
	// break;

	// }
	// return true;
	// }

	public void storeNewOrder(int orderId, String orderName, String customerName, double orderPrice, String userAddress,
			String creditCardNo) {
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();

		// get the payment details file
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();

		} catch (Exception e) {

		}
		if (orderPayments == null) {
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
		// if there exist order id already add it into same list for order id or create
		// a new record with order id

		if (!orderPayments.containsKey(orderId)) {
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);

		OrderPayment orderpayment = new OrderPayment(orderId, customerName, orderName, orderPrice, userAddress,
				creditCardNo);
		listOrderPayment.add(orderpayment);

		// add order details into file
		try {
			MySqlDataStoreUtilities.insertOrder(orderId, customerName, orderName, orderPrice, userAddress,
					creditCardNo);
		} catch (Exception e) {
			System.out.println("inside exception file not written properly");
		}

	}

	public void removeOldOrder(int orderId, String orderName, String customerName) {

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		ArrayList<OrderPayment> ListOrderPayment = new ArrayList<OrderPayment>();
		// get the order from file
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();

		} catch (Exception e) {

		}
		// get the exact order with same ordername and add it into cancel list to remove
		// it later
		for (OrderPayment oi : orderPayments.get(orderId)) {
			if (oi.getOrderName().equals(orderName) && oi.getUserName().equals(customerName)) {

				MySqlDataStoreUtilities.deleteOrder(orderId, orderName);
				ListOrderPayment.add(oi);

			}
		}
		// remove all the orders from hashmap that exist in cancel list
		orderPayments.get(orderId).removeAll(ListOrderPayment);
		if (orderPayments.get(orderId).size() == 0) {
			orderPayments.remove(orderId);
		}

		// save the updated hashmap with removed order to the file
		// updateOrderFile(orderPayments);
	}

	public void updateOldOrder(int orderId, String username, String productName, Double price, String address,
			String creditCard) {

		try {
			MySqlDataStoreUtilities.updateOrder(orderId, username, productName, price, address, creditCard);

		} catch (Exception e) {
			System.out.println("failed update");
		}

	}
	/* getConsoles Functions returns the Hashmap with all consoles in the store. */

	public String storeReview(String productModelName, String productCategory, String productPrice, String retailerName,
			String retailerZip, String retailerCity, String retailerState, String productOnSale,
			String manufacturerName, String manufacturerRebate, String userID, String userAge, String userGender,
			String userOccupation, String reviewRating, String reviewDate, String reviewText) {

		String message = MongoDBDataStoreUtilities.insertReview(productModelName, productCategory, productPrice,
				retailerName, retailerZip, retailerCity, retailerState, productOnSale, manufacturerName,
				manufacturerRebate, username(), userAge, userGender, userOccupation, reviewRating, reviewDate,
				reviewText);
		if (!message.equals("Successfull")) {
			return "UnSuccessfull";
		} else {
			HashMap<String, ArrayList<Review>> reviews = new HashMap<String, ArrayList<Review>>();
			try {
				reviews = MongoDBDataStoreUtilities.selectReview();
			} catch (Exception e) {

			}
			if (reviews == null) {
				reviews = new HashMap<String, ArrayList<Review>>();
			}
			// if there exist product review already add it into same list for productname
			// or create a new record with product name

			if (!reviews.containsKey(productModelName)) {
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(productModelName, arr);
			}
			ArrayList<Review> listReview = reviews.get(productModelName);
			Review review = new Review(productModelName, productCategory, productPrice, retailerName, retailerZip,
					retailerCity, retailerState, productOnSale, manufacturerName, manufacturerRebate, username(),
					userAge, userGender, userOccupation, reviewRating, reviewDate, reviewText);
			listReview.add(review);

			// add Reviews into database

			return "Successfull";
		}
	}

	public HashMap<String, Console> getConsoles() {
		HashMap<String, Console> hm = new HashMap<String, Console>();
		hm.putAll(SaxParserDataStore.consoles);
		return hm;
	}

	/* getConsoles Functions returns the Hashmap with all consoles in the store. */

	public HashMap<String, Laptop> getLaptops() {
		HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
		hm.putAll(SaxParserDataStore.laptops);
		return hm;
	}

	/* getGames Functions returns the Hashmap with all Games in the store. */

	public HashMap<String, Game> getGames() {
		HashMap<String, Game> hm = new HashMap<String, Game>();
		hm.putAll(SaxParserDataStore.games);
		return hm;
	}

	/* getTablets Functions returns the Hashmap with all Tablet in the store. */

	public HashMap<String, Tablet> getTablets() {
		HashMap<String, Tablet> hm = new HashMap<String, Tablet>();
		hm.putAll(SaxParserDataStore.tablets);
		return hm;
	}

	/* getProducts Functions returns the Arraylist of consoles in the store. */

	public ArrayList<String> getProducts() {
		ArrayList<String> ar = new ArrayList<String>();
		for (Map.Entry<String, Laptop> entry : getLaptops().entrySet()) {
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of games in the store. */

	public ArrayList<String> getProductsGame() {
		ArrayList<String> ar = new ArrayList<String>();
		for (Map.Entry<String, Game> entry : getGames().entrySet()) {
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProducts Functions returns the Arraylist of Tablets in the store. */

	public ArrayList<String> getProductsTablets() {
		ArrayList<String> ar = new ArrayList<String>();
		for (Map.Entry<String, Tablet> entry : getTablets().entrySet()) {
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

}
