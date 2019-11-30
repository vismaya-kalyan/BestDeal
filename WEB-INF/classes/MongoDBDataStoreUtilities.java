import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;

public class MongoDBDataStoreUtilities {
	static DBCollection myReviews;

	public static DBCollection getConnection() {
		MongoClient mongo;
		mongo = new MongoClient("localhost", 27017);

		DB db = mongo.getDB("Reviews");
		myReviews = db.getCollection("productReviews");
		return myReviews;
	}

	public static String insertReview(String productModelName, String productCategory, String productPrice,
			String retailerName, String retailerZip, String retailerCity, String retailerState, String productOnSale,
			String manufacturerName, String manufacturerRebate, String userID, String userAge, String userGender,
			String userOccupation, String reviewRating, String reviewDate, String reviewText) {
		try {
			getConnection();
			BasicDBObject doc = new BasicDBObject("title", "productReviews")
					.append("productModelName", productModelName).append("productCategory", productCategory)
					.append("productPrice", productPrice).append("retailerName", retailerName)
					.append("retailerZip", retailerZip).append("retailerCity", retailerCity)
					.append("retailerState", retailerState).append("productOnSale", productOnSale)
					.append("manufacturerName", manufacturerName).append("manufacturerRebate", manufacturerRebate)
					.append("userID", userID).append("userAge", userAge).append("userGender", userGender)
					.append("userOccupation", userOccupation).append("reviewRating", Integer.parseInt(reviewRating))
					.append("reviewDate", reviewDate).append("reviewText", reviewText);
			myReviews.insert(doc);
			return "Successfull";
		} catch (Exception e) {
			return "UnSuccessfull";
		}

	}

	public static HashMap<String, ArrayList<Review>> selectReview() {
		HashMap<String, ArrayList<Review>> reviews = null;

		try {

			getConnection();
			DBCursor cursor = myReviews.find();
			reviews = new HashMap<String, ArrayList<Review>>();
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();

				if (!reviews.containsKey(obj.getString("productModelName"))) {
					ArrayList<Review> arr = new ArrayList<Review>();
					reviews.put(obj.getString("productModelName"), arr);
				}
				ArrayList<Review> listReview = reviews.get(obj.getString("productModelName"));
				Review review = new Review(obj.getString("productModelName"), obj.getString("productCategory"),
						obj.getString("productPrice"), obj.getString("retailerName"), obj.getString("retailerZip"),
						obj.getString("retailerCity"), obj.getString("retailerState"), obj.getString("productOnSale"),
						obj.getString("manufacturerName"), obj.getString("manufacturerRebate"), obj.getString("userID"),
						obj.getString("userAge"), obj.getString("userGender"), obj.getString("userOccupation"),
						obj.getString("reviewRating"), obj.getString("reviewDate"), obj.getString("reviewText"));
				// add to review hashmap
				listReview.add(review);

			}
			return reviews;
		} catch (Exception e) {
			reviews = null;
			return reviews;
		}

	}

	public static ArrayList<Bestrating> topProducts() {
		ArrayList<Bestrating> Bestrate = new ArrayList<Bestrating>();
		try {

			getConnection();
			int retlimit = 5;
			DBObject sort = new BasicDBObject();
			sort.put("reviewRating", -1);
			DBCursor cursor = myReviews.find().limit(retlimit).sort(sort);
			while (cursor.hasNext()) {
				BasicDBObject obj = (BasicDBObject) cursor.next();

				String prodcutnm = obj.get("productModelName").toString();
				String rating = obj.get("reviewRating").toString();
				Bestrating best = new Bestrating(prodcutnm, rating);
				Bestrate.add(best);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return Bestrate;
	}

	public static ArrayList<Mostsoldzip> mostsoldZip() {
		ArrayList<Mostsoldzip> mostsoldzip = new ArrayList<Mostsoldzip>();
		try {

			getConnection();
			DBObject groupProducts = new BasicDBObject("_id", "$retailerZip");
			groupProducts.put("count", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupProducts);
			DBObject limit = new BasicDBObject();
			limit = new BasicDBObject("$limit", 5);

			DBObject sortFields = new BasicDBObject("count", -1);
			DBObject sort = new BasicDBObject("$sort", sortFields);
			AggregationOutput output = myReviews.aggregate(group, sort, limit);
			for (DBObject res : output.results()) {

				String zipcode = (res.get("_id")).toString();
				String count = (res.get("count")).toString();
				Mostsoldzip mostsldzip = new Mostsoldzip(zipcode, count);
				mostsoldzip.add(mostsldzip);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mostsoldzip;
	}

	public static ArrayList<Mostsold> mostsoldProducts() {
		ArrayList<Mostsold> mostsold = new ArrayList<Mostsold>();
		try {

			getConnection();
			DBObject groupProducts = new BasicDBObject("_id", "$productModelName");
			groupProducts.put("count", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupProducts);
			DBObject limit = new BasicDBObject();
			limit = new BasicDBObject("$limit", 5);

			DBObject sortFields = new BasicDBObject("count", -1);
			DBObject sort = new BasicDBObject("$sort", sortFields);
			AggregationOutput output = myReviews.aggregate(group, sort, limit);

			for (DBObject res : output.results()) {

				String prodcutname = (res.get("_id")).toString();
				String count = (res.get("count")).toString();
				Mostsold mostsld = new Mostsold(prodcutname, count);
				mostsold.add(mostsld);

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return mostsold;
	}

	// Get all the reviews grouped by product and zip code;
	public static ArrayList<Review> selectReviewForChart() {

		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {

			getConnection();
			Map<String, Object> dbObjIdMap = new HashMap<String, Object>();
			dbObjIdMap.put("retailerZip", "$retailerZip");
			dbObjIdMap.put("productModelName", "$productModelName");
			DBObject groupFields = new BasicDBObject("_id", new BasicDBObject(dbObjIdMap));
			groupFields.put("count", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject projectFields = new BasicDBObject("_id", 0);
			projectFields.put("retailerZip", "$_id");
			projectFields.put("productModelName", "$productName");
			projectFields.put("reviewCount", "$count");
			DBObject project = new BasicDBObject("$project", projectFields);

			DBObject sort = new BasicDBObject();
			sort.put("reviewCount", -1);

			DBObject orderby = new BasicDBObject();
			orderby = new BasicDBObject("$sort", sort);

			AggregationOutput aggregate = myReviews.aggregate(group, project, orderby);

			for (DBObject result : aggregate.results()) {

				BasicDBObject obj = (BasicDBObject) result;
				Object o = com.mongodb.util.JSON.parse(obj.getString("retailerZip"));
				BasicDBObject dbObj = (BasicDBObject) o;
				Review review = new Review(dbObj.getString("productModelName"), dbObj.getString("retailerZip"),
						obj.getString("reviewCount"), null);
				reviewList.add(review);

			}
			return reviewList;

		}

		catch (

		Exception e) {
			reviewList = null;

			return reviewList;
		}

	}

}