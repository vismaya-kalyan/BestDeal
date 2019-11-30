import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String action = request.getParameter("button");

		String msg = "good";
		String producttype = "", productId = "", productName = "", productImage = "", productManufacturer = "",
				productCondition = "", prod = "";
		double productPrice = 0.0, productDiscount = 0.0;
		HashMap<String, Console> allconsoles = new HashMap<String, Console>();
		HashMap<String, Tablet> alltablets = new HashMap<String, Tablet>();
		HashMap<String, Game> allgames = new HashMap<String, Game>();
		HashMap<String, Accessory> allaccessories = new HashMap<String, Accessory>();

		HashMap<String, Tv> allTvs = new HashMap<String, Tv>();
		HashMap<String, SoundSystem> allSoundSystems = new HashMap<String, SoundSystem>();
		HashMap<String, Phone> allPhones = new HashMap<String, Phone>();
		HashMap<String, Laptop> allLaptops = new HashMap<String, Laptop>();
		HashMap<String, VoiceAssistant> allVoiceAssistants = new HashMap<String, VoiceAssistant>();
		HashMap<String, FitnessWatch> allFitnessWatchs = new HashMap<String, FitnessWatch>();
		HashMap<String, SmartWatch> allSmartWatchs = new HashMap<String, SmartWatch>();
		HashMap<String, Headphone> allHeadphones = new HashMap<String, Headphone>();
		HashMap<String, WirelessPlan> allWirelessPlans = new HashMap<String, WirelessPlan>();

		if (action.equals("add") || action.equals("update")) {
			producttype = request.getParameter("productCatalog");
			productId = request.getParameter("productId");
			productName = request.getParameter("productName");
			productPrice = Double.parseDouble(request.getParameter("price"));
			// productImage = request.getParameter("productImage");
			productManufacturer = request.getParameter("manufacturer");
			productCondition = request.getParameter("condition");
			productDiscount = Double.parseDouble(request.getParameter("discount"));

			productImage = request.getParameter("image");
			prod = request.getParameter("prod");

		} else {
			productId = request.getParameter("productId");
			producttype = request.getParameter("productCatalog");
		}
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");

		if (action.equals("add")) {

			if (producttype.equals("consoles")) {
				allconsoles = MySqlDataStoreUtilities.getConsoles();
				if (allconsoles.containsKey(productId)) {
					msg = "Product already available";
				}

			} else if (producttype.equals("tvs")) {
				allTvs = MySqlDataStoreUtilities.getTvs();
				if (allTvs.containsKey(productId)) {
					msg = "Product already available";
				}
			} else if (producttype.equals("soundsystems")) {
				allSoundSystems = MySqlDataStoreUtilities.getSoundSystem();
				if (allSoundSystems.containsKey(productId)) {
					msg = "Product already available";
				}
			}

			else if (producttype.equals("phones")) {
				allPhones = MySqlDataStoreUtilities.getPhones();
				if (allPhones.containsKey(productId)) {
					msg = "Product already available";

				}
			} else if (producttype.equals("laptops")) {
				allLaptops = MySqlDataStoreUtilities.getLaptops();
				if (allLaptops.containsKey(productId)) {
					msg = "Product already available";

				}

			}

			else if (producttype.equals("voiceassistants")) {
				allVoiceAssistants = MySqlDataStoreUtilities.getVoiceAssistant();
				if (allVoiceAssistants.containsKey(productId)) {
					msg = "Product already available";

				}

			}

			else if (producttype.equals("fitnesswatchs")) {
				allFitnessWatchs = MySqlDataStoreUtilities.getFitnessWatchs();
				if (allFitnessWatchs.containsKey(productId)) {
					msg = "Product already available";

				}
			}

			else if (producttype.equals("smartwatchs")) {
				allSmartWatchs = MySqlDataStoreUtilities.getSmartWatchs();
				if (allSmartWatchs.containsKey(productId)) {
					msg = "Product already available";

				}
			}

			else if (producttype.equals("headphones")) {
				allHeadphones = MySqlDataStoreUtilities.getHeadphones();
				if (allHeadphones.containsKey(productId)) {
					msg = "Product already available";

				}
			}

			else if (producttype.equals("wirelessplans")) {
				allWirelessPlans = MySqlDataStoreUtilities.getWirelessPlans();
				if (allWirelessPlans.containsKey(productId)) {
					msg = "Product already available";

				}

			}

			else if (producttype.equals("games")) {
				allgames = MySqlDataStoreUtilities.getGames();
				if (allgames.containsKey(productId)) {
					msg = "Product already available";
				}
			} else if (producttype.equals("tablets")) {
				alltablets = MySqlDataStoreUtilities.getTablets();
				if (alltablets.containsKey(productId)) {
					msg = "Product already available";
				}
			} else if (producttype.equals("accessories")) {
				if (!request.getParameter("prod").isEmpty()) {
					prod = request.getParameter("prod");
					allLaptops = MySqlDataStoreUtilities.getLaptops();
					if (allLaptops.containsKey(prod)) {
						allaccessories = MySqlDataStoreUtilities.getAccessories();
						if (allaccessories.containsKey(productId)) {
							msg = "Product already available";
						}
					} else {
						msg = "The product related to accessories is not available";
					}

				} else {
					msg = "Please add the prodcut name";
				}

			}
			if (msg.equals("good")) {
				try {
					switch (producttype) {
					case "tvs":

						Tv tv = new Tv();
						tv.setId(productId);
						tv.setName(productName);
						tv.setPrice(productPrice);
						tv.setRetailer(productManufacturer);
						tv.setCondition(productCondition);
						tv.setDiscount(productDiscount);
						tv.setImage(productImage);
						SaxParserDataStore.tvs.remove(productId);
						SaxParserDataStore.tvs.put(productId, tv);
						System.out.println("tvs HashMap: " + SaxParserDataStore.tvs);
						break;

					case "soundsystems":

						SoundSystem soundsystem = new SoundSystem();
						soundsystem.setId(productId);
						soundsystem.setName(productName);
						soundsystem.setPrice(productPrice);
						soundsystem.setRetailer(productManufacturer);
						soundsystem.setCondition(productCondition);
						soundsystem.setDiscount(productDiscount);
						soundsystem.setImage(productImage);
						SaxParserDataStore.soundsystems.remove(productId);
						SaxParserDataStore.soundsystems.put(productId, soundsystem);
						System.out.println("soundsystems HashMap: " + SaxParserDataStore.soundsystems);
						break;

					case "phones":

						Phone phone = new Phone();
						phone.setId(productId);
						phone.setName(productName);
						phone.setPrice(productPrice);
						phone.setRetailer(productManufacturer);
						phone.setCondition(productCondition);
						phone.setDiscount(productDiscount);
						phone.setImage(productImage);
						SaxParserDataStore.phones.remove(productId);
						SaxParserDataStore.phones.put(productId, phone);
						System.out.println("phones HashMap: " + SaxParserDataStore.phones);
						break;

					case "laptops":

						Laptop laptop = new Laptop();
						laptop.setId(productId);
						laptop.setName(productName);
						laptop.setPrice(productPrice);
						laptop.setRetailer(productManufacturer);
						laptop.setCondition(productCondition);
						laptop.setDiscount(productDiscount);
						laptop.setImage(productImage);
						SaxParserDataStore.laptops.remove(productId);
						SaxParserDataStore.laptops.put(productId, laptop);
						System.out.println("laptops HashMap: " + SaxParserDataStore.laptops);
						break;

					case "voiceassistants":

						VoiceAssistant voiceassistant = new VoiceAssistant();
						voiceassistant.setId(productId);
						voiceassistant.setName(productName);
						voiceassistant.setPrice(productPrice);
						voiceassistant.setRetailer(productManufacturer);
						voiceassistant.setCondition(productCondition);
						voiceassistant.setDiscount(productDiscount);
						voiceassistant.setImage(productImage);
						SaxParserDataStore.voiceassistants.remove(productId);
						SaxParserDataStore.voiceassistants.put(productId, voiceassistant);
						System.out.println("voiceassistants HashMap: " + SaxParserDataStore.voiceassistants);
						break;

					case "fitnesswatchs":

						FitnessWatch fitnesswatch = new FitnessWatch();
						fitnesswatch.setId(productId);
						fitnesswatch.setName(productName);
						fitnesswatch.setPrice(productPrice);
						fitnesswatch.setRetailer(productManufacturer);
						fitnesswatch.setCondition(productCondition);
						fitnesswatch.setDiscount(productDiscount);
						fitnesswatch.setImage(productImage);
						SaxParserDataStore.fitnesswatchs.remove(productId);
						SaxParserDataStore.fitnesswatchs.put(productId, fitnesswatch);
						System.out.println("fitnesswatchs HashMap: " + SaxParserDataStore.fitnesswatchs);
						break;

					case "smartwatchs":

						SmartWatch smartwatch = new SmartWatch();
						smartwatch.setId(productId);
						smartwatch.setName(productName);
						smartwatch.setPrice(productPrice);
						smartwatch.setRetailer(productManufacturer);
						smartwatch.setCondition(productCondition);
						smartwatch.setDiscount(productDiscount);
						smartwatch.setImage(productImage);
						SaxParserDataStore.smartwatchs.remove(productId);
						SaxParserDataStore.smartwatchs.put(productId, smartwatch);
						System.out.println("smartwatchs HashMap: " + SaxParserDataStore.smartwatchs);
						break;

					case "headphones":

						Headphone headphone = new Headphone();
						headphone.setId(productId);
						headphone.setName(productName);
						headphone.setPrice(productPrice);
						headphone.setRetailer(productManufacturer);
						headphone.setCondition(productCondition);
						headphone.setDiscount(productDiscount);
						headphone.setImage(productImage);
						SaxParserDataStore.headphones.remove(productId);
						SaxParserDataStore.headphones.put(productId, headphone);
						System.out.println("headphones HashMap: " + SaxParserDataStore.headphones);
						break;

					case "wirelessplans":

						WirelessPlan wirelessplan = new WirelessPlan();
						wirelessplan.setId(productId);
						wirelessplan.setName(productName);
						wirelessplan.setPrice(productPrice);
						wirelessplan.setRetailer(productManufacturer);
						wirelessplan.setCondition(productCondition);
						wirelessplan.setDiscount(productDiscount);
						wirelessplan.setImage(productImage);
						SaxParserDataStore.wirelessplans.remove(productId);
						SaxParserDataStore.wirelessplans.put(productId, wirelessplan);
						System.out.println("wirelessplans HashMap: " + SaxParserDataStore.wirelessplans);
						break;

					case "accessories":

						Accessory accessorie = new Accessory();
						accessorie.setId(productId);
						accessorie.setName(productName);
						accessorie.setPrice(productPrice);
						accessorie.setRetailer(productManufacturer);
						accessorie.setCondition(productCondition);
						accessorie.setDiscount(productDiscount);
						accessorie.setImage(productImage);
						SaxParserDataStore.accessories.remove(productId);
						SaxParserDataStore.accessories.put(productId, accessorie);
						System.out.println("accessories HashMap: " + SaxParserDataStore.accessories);
						break;

					}
					msg = MySqlDataStoreUtilities.addproducts(producttype, productId, productName, productPrice,
							productImage, productManufacturer, productCondition, productDiscount, prod);
				} catch (Exception e) {
					msg = "Product cannot be inserted";
				}
				msg = "Product has been successfully added";
			}
		} else if (action.equals("update")) {

			if (producttype.equals("consoles")) {
				allconsoles = MySqlDataStoreUtilities.getConsoles();
				if (!allconsoles.containsKey(productId)) {
					msg = "Product not available";
				}

			} else if (producttype.equals("games")) {
				allgames = MySqlDataStoreUtilities.getGames();
				if (!allgames.containsKey(productId)) {
					msg = "Product not available";
				}
			} else if (producttype.equals("tablets")) {
				alltablets = MySqlDataStoreUtilities.getTablets();
				if (!alltablets.containsKey(productId)) {
					msg = "Product not available";
				}
			} else if (producttype.equals("accessories")) {
				allaccessories = MySqlDataStoreUtilities.getAccessories();
				if (!allaccessories.containsKey(productId)) {
					msg = "Product not available";
				}
			} else if (producttype.equals("tvs")) {
				allTvs = MySqlDataStoreUtilities.getTvs();
				if (!allTvs.containsKey(productId)) {
					msg = "Product not available";
				}
			} else if (producttype.equals("soundsystems")) {
				allSoundSystems = MySqlDataStoreUtilities.getSoundSystem();
				if (!allSoundSystems.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("phones")) {
				allPhones = MySqlDataStoreUtilities.getPhones();
				if (!allPhones.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("laptops")) {
				allLaptops = MySqlDataStoreUtilities.getLaptops();
				if (!allLaptops.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("voiceassistants")) {
				allVoiceAssistants = MySqlDataStoreUtilities.getVoiceAssistant();
				if (!allVoiceAssistants.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("fitnesswatchs")) {
				allFitnessWatchs = MySqlDataStoreUtilities.getFitnessWatchs();
				if (!allFitnessWatchs.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("smartwatchs")) {
				allSmartWatchs = MySqlDataStoreUtilities.getSmartWatchs();
				if (!allSmartWatchs.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			else if (producttype.equals("headphones")) {
				allHeadphones = MySqlDataStoreUtilities.getHeadphones();
				if (!allHeadphones.containsKey(productId)) {
					msg = "Product not available";
				}
			} else if (producttype.equals("wirelessplans")) {
				allWirelessPlans = MySqlDataStoreUtilities.getWirelessPlans();
				if (!allWirelessPlans.containsKey(productId)) {
					msg = "Product not available";
				}
			}

			if (msg.equals("good")) {

				try {
					switch (producttype) {
					case "tvs":

						Tv tv = new Tv();
						tv.setId(productId);
						tv.setName(productName);
						tv.setPrice(productPrice);
						tv.setRetailer(productManufacturer);
						tv.setCondition(productCondition);
						tv.setDiscount(productDiscount);
						tv.setImage(productImage);
						SaxParserDataStore.tvs.remove(productId);
						SaxParserDataStore.tvs.put(productId, tv);
						System.out.println("tvs HashMap: " + SaxParserDataStore.tvs);
						break;

					case "soundsystems":

						SoundSystem soundsystem = new SoundSystem();
						soundsystem.setId(productId);
						soundsystem.setName(productName);
						soundsystem.setPrice(productPrice);
						soundsystem.setRetailer(productManufacturer);
						soundsystem.setCondition(productCondition);
						soundsystem.setDiscount(productDiscount);
						soundsystem.setImage(productImage);
						SaxParserDataStore.soundsystems.remove(productId);
						SaxParserDataStore.soundsystems.put(productId, soundsystem);
						System.out.println("soundsystems HashMap: " + SaxParserDataStore.soundsystems);
						break;

					case "phones":

						Phone phone = new Phone();
						phone.setId(productId);
						phone.setName(productName);
						phone.setPrice(productPrice);
						phone.setRetailer(productManufacturer);
						phone.setCondition(productCondition);
						phone.setDiscount(productDiscount);
						phone.setImage(productImage);
						SaxParserDataStore.phones.remove(productId);
						SaxParserDataStore.phones.put(productId, phone);
						System.out.println("phones HashMap: " + SaxParserDataStore.phones);
						break;

					case "laptops":

						Laptop laptop = new Laptop();
						laptop.setId(productId);
						laptop.setName(productName);
						laptop.setPrice(productPrice);
						laptop.setRetailer(productManufacturer);
						laptop.setCondition(productCondition);
						laptop.setDiscount(productDiscount);
						laptop.setImage(productImage);
						SaxParserDataStore.laptops.remove(productId);
						SaxParserDataStore.laptops.put(productId, laptop);
						System.out.println("laptops HashMap: " + SaxParserDataStore.laptops);
						break;

					case "voiceassistants":

						VoiceAssistant voiceassistant = new VoiceAssistant();
						voiceassistant.setId(productId);
						voiceassistant.setName(productName);
						voiceassistant.setPrice(productPrice);
						voiceassistant.setRetailer(productManufacturer);
						voiceassistant.setCondition(productCondition);
						voiceassistant.setDiscount(productDiscount);
						voiceassistant.setImage(productImage);
						SaxParserDataStore.voiceassistants.remove(productId);
						SaxParserDataStore.voiceassistants.put(productId, voiceassistant);
						System.out.println("voiceassistants HashMap: " + SaxParserDataStore.voiceassistants);
						break;

					case "fitnesswatchs":

						FitnessWatch fitnesswatch = new FitnessWatch();
						fitnesswatch.setId(productId);
						fitnesswatch.setName(productName);
						fitnesswatch.setPrice(productPrice);
						fitnesswatch.setRetailer(productManufacturer);
						fitnesswatch.setCondition(productCondition);
						fitnesswatch.setDiscount(productDiscount);
						fitnesswatch.setImage(productImage);
						SaxParserDataStore.fitnesswatchs.remove(productId);
						SaxParserDataStore.fitnesswatchs.put(productId, fitnesswatch);
						System.out.println("fitnesswatchs HashMap: " + SaxParserDataStore.fitnesswatchs);
						break;

					case "smartwatchs":

						SmartWatch smartwatch = new SmartWatch();
						smartwatch.setId(productId);
						smartwatch.setName(productName);
						smartwatch.setPrice(productPrice);
						smartwatch.setRetailer(productManufacturer);
						smartwatch.setCondition(productCondition);
						smartwatch.setDiscount(productDiscount);
						smartwatch.setImage(productImage);
						SaxParserDataStore.smartwatchs.remove(productId);
						SaxParserDataStore.smartwatchs.put(productId, smartwatch);
						System.out.println("smartwatchs HashMap: " + SaxParserDataStore.smartwatchs);
						break;

					case "headphones":

						Headphone headphone = new Headphone();
						headphone.setId(productId);
						headphone.setName(productName);
						headphone.setPrice(productPrice);
						headphone.setRetailer(productManufacturer);
						headphone.setCondition(productCondition);
						headphone.setDiscount(productDiscount);
						headphone.setImage(productImage);
						SaxParserDataStore.headphones.remove(productId);
						SaxParserDataStore.headphones.put(productId, headphone);
						System.out.println("headphones HashMap: " + SaxParserDataStore.headphones);
						break;

					case "wirelessplans":

						WirelessPlan wirelessplan = new WirelessPlan();
						wirelessplan.setId(productId);
						wirelessplan.setName(productName);
						wirelessplan.setPrice(productPrice);
						wirelessplan.setRetailer(productManufacturer);
						wirelessplan.setCondition(productCondition);
						wirelessplan.setDiscount(productDiscount);
						wirelessplan.setImage(productImage);
						SaxParserDataStore.wirelessplans.remove(productId);
						SaxParserDataStore.wirelessplans.put(productId, wirelessplan);
						System.out.println("wirelessplans HashMap: " + SaxParserDataStore.wirelessplans);
						break;

					case "accessories":

						Accessory accessorie = new Accessory();
						accessorie.setId(productId);
						accessorie.setName(productName);
						accessorie.setPrice(productPrice);
						accessorie.setRetailer(productManufacturer);
						accessorie.setCondition(productCondition);
						accessorie.setDiscount(productDiscount);
						accessorie.setImage(productImage);
						SaxParserDataStore.accessories.remove(productId);
						SaxParserDataStore.accessories.put(productId, accessorie);
						System.out.println("accessories HashMap: " + SaxParserDataStore.accessories);
						break;

					}

					msg = MySqlDataStoreUtilities.updateproducts(producttype, productId, productName, productPrice,
							productImage, productManufacturer, productCondition, productDiscount);
				} catch (Exception e) {
					msg = "Product cannot be updated";
				}
				msg = "Product has been successfully updated";
			}
		} else if (action.equals("delete")) {
			msg = "bad";
			allconsoles = MySqlDataStoreUtilities.getConsoles();
			if (allconsoles.containsKey(productId)) {
				msg = "good";

			}

			allgames = MySqlDataStoreUtilities.getGames();
			if (allgames.containsKey(productId)) {
				msg = "good";
			}

			alltablets = MySqlDataStoreUtilities.getTablets();
			if (alltablets.containsKey(productId)) {
				msg = "good";
			}

			allaccessories = MySqlDataStoreUtilities.getAccessories();
			if (allaccessories.containsKey(productId)) {
				msg = "good";
			}
			allTvs = MySqlDataStoreUtilities.getTvs();
			if (allTvs.containsKey(productId)) {
				msg = "good";
				System.out.println("Contaims product id");
			}
			allSoundSystems = MySqlDataStoreUtilities.getSoundSystem();
			if (allSoundSystems.containsKey(productId)) {
				msg = "good";
			}
			allPhones = MySqlDataStoreUtilities.getPhones();
			if (allPhones.containsKey(productId)) {
				msg = "good";
			}
			allLaptops = MySqlDataStoreUtilities.getLaptops();
			if (allLaptops.containsKey(productId)) {
				msg = "good";
			}
			allVoiceAssistants = MySqlDataStoreUtilities.getVoiceAssistant();
			if (allVoiceAssistants.containsKey(productId)) {
				msg = "good";
			}

			allFitnessWatchs = MySqlDataStoreUtilities.getFitnessWatchs();
			if (allFitnessWatchs.containsKey(productId)) {
				msg = "good";
			}
			allSmartWatchs = MySqlDataStoreUtilities.getSmartWatchs();
			if (allSmartWatchs.containsKey(productId)) {
				msg = "good";
			}
			allHeadphones = MySqlDataStoreUtilities.getHeadphones();
			if (allHeadphones.containsKey(productId)) {
				msg = "good";
			}
			allWirelessPlans = MySqlDataStoreUtilities.getWirelessPlans();
			if (allWirelessPlans.containsKey(productId)) {
				msg = "good";
			}
			if (msg.equals("good")) {

				try {

					switch (producttype) {

					case "tvs":

						SaxParserDataStore.tvs.remove(productId);
						System.out.println("tvs HashMap: " + SaxParserDataStore.tvs);
						break;

					case "soundsystems":

						SaxParserDataStore.soundsystems.remove(productId);
						System.out.println("soundsystems HashMap: " + SaxParserDataStore.soundsystems);
						break;

					case "phones":

						SaxParserDataStore.phones.remove(productId);
						System.out.println("phones HashMap: " + SaxParserDataStore.phones);
						break;

					case "laptops":

						SaxParserDataStore.laptops.remove(productId);
						System.out.println("laptops HashMap: " + SaxParserDataStore.laptops);
						break;

					case "voiceassistants":

						SaxParserDataStore.voiceassistants.remove(productId);
						System.out.println("voiceassistants HashMap: " + SaxParserDataStore.voiceassistants);
						break;

					case "fitnesswatchs":

						SaxParserDataStore.fitnesswatchs.remove(productId);
						System.out.println("fitnesswatchs HashMap: " + SaxParserDataStore.fitnesswatchs);
						break;

					case "smartwatchs":

						SaxParserDataStore.smartwatchs.remove(productId);
						System.out.println("smartwatchs HashMap: " + SaxParserDataStore.smartwatchs);
						break;

					case "headphones":

						SaxParserDataStore.headphones.remove(productId);
						System.out.println("headphones HashMap: " + SaxParserDataStore.headphones);
						break;

					case "wirelessplans":

						SaxParserDataStore.wirelessplans.remove(productId);
						System.out.println("wirelessplans HashMap: " + SaxParserDataStore.wirelessplans);
						break;

					case "accessories":

						SaxParserDataStore.accessories.remove(productId);
						System.out.println("accessories HashMap: " + SaxParserDataStore.accessories);
						break;

					}
					msg = MySqlDataStoreUtilities.deleteproducts(productId);
				} catch (Exception e) {
					msg = "Product cannot be deleted";
				}
				msg = "Product has been successfully deleted";
			} else {
				msg = "Product not available";
			}
		}

		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<h4 style='color:white'>" + msg + "</h4>");
		pw.print("</div></div></div><div class='clear'></div>");
		utility.printHtml("Footer.html");

	}
}