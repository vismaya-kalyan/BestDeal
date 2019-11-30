
import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        review(request, response);
    }

    protected void review(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to view Review");
                response.sendRedirect("Login");
                return;
            }
            String ProductModelName = request.getParameter("name");
            HashMap<String, ArrayList<Review>> hm = MongoDBDataStoreUtilities.selectReview();
            String ProductCategory = "";
            String ProductPrice = "";
            String RetailerName = "";
            String RetailerZip = "";
            String RetailerCity = "";
            String RetailerState = "";
            String ProductOnSale = "";
            String ManufacturerName = "";
            String ManufacturerRebate = "";
            String UserID = "";
            String UserAge = "";
            String UserGender = "";
            String UserOccupation = "";
            String ReviewRating = "";
            String ReviewDate = "";
            String ReviewText = "";

            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");

            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Review</a>");
            pw.print("</h2><div class='entry'>");

            // if there are no reviews for product print no review else iterate over all the
            // reviews using cursor and print the reviews in a table
            if (hm == null) {
                pw.println("<h2>Mongo Db server is not up and running</h2>");
            } else {
                if (!hm.containsKey(ProductModelName)) {
                    pw.println("<h2>There are no reviews for this product.</h2>");
                } else {
                    for (Review r : hm.get(ProductModelName)) {
                        pw.println("<h2> User:" + r.getUserID() + "</h2>");
                        pw.print("<table class='gridtable'>");
                        pw.print("<tr>");
                        pw.print("<td> ProductModelName: </td>");
                        ProductModelName = r.getProductModelName();
                        pw.print("<td>" + ProductModelName + "</td>");
                        pw.print("</tr>");

                        pw.print("<tr>");
                        pw.print("<td> ProductCategory: </td>");
                        ProductCategory = r.getProductCategory();
                        pw.print("<td>" + ProductCategory + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ProductPrice: </td>");
                        ProductPrice = r.getProductPrice();
                        pw.print("<td>" + ProductPrice + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> RetailerName: </td>");
                        RetailerName = r.getRetailerName();
                        pw.print("<td>" + RetailerName + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> RetailerZip: </td>");
                        RetailerZip = r.getRetailerZip();
                        pw.print("<td>" + RetailerZip + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> RetailerCity: </td>");
                        RetailerCity = r.getRetailerCity();
                        pw.print("<td>" + RetailerCity + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> RetailerState: </td>");
                        RetailerState = r.getRetailerState();
                        pw.print("<td>" + RetailerState + "</td>");
                        pw.print("</tr>");

                        pw.print("<tr>");
                        pw.print("<td> ProductOnSale: </td>");
                        ProductOnSale = r.getProductOnSale();
                        pw.print("<td>" + ProductOnSale + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ManufacturerName: </td>");
                        ManufacturerName = r.getManufacturerName();
                        pw.print("<td>" + ManufacturerName + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ManufacturerRebate: </td>");
                        ManufacturerRebate = r.getManufacturerRebate();
                        pw.print("<td>" + ManufacturerRebate + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> UserID: </td>");
                        UserID = r.getUserID();
                        pw.print("<td>" + UserID + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> UserAge: </td>");
                        UserAge = r.getUserAge();
                        pw.print("<td>" + UserAge + "</td>");
                        pw.print("</tr>");

                        pw.print("<tr>");
                        pw.print("<td> UserGender: </td>");
                        UserGender = r.getUserGender();
                        pw.print("<td>" + UserGender + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> UserOccupation: </td>");
                        UserOccupation = r.getUserOccupation();
                        pw.print("<td>" + UserOccupation + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ReviewRating: </td>");
                        ReviewRating = r.getReviewRating();
                        pw.print("<td>" + ReviewRating + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ReviewDate: </td>");
                        ReviewDate = r.getReviewDate();
                        pw.print("<td>" + ReviewDate + "</td>");
                        pw.print("</tr>");
                        pw.print("<tr>");
                        pw.print("<td> ReviewText: </td>");
                        ReviewText = r.getReviewText();
                        pw.print("<td>" + ReviewText + "</td>");
                        pw.print("</tr>");

                        pw.println("</table>");
                    }

                }
            }
            pw.print("</div></div></div><div class='clear'</div>");
            utility.printHtml("Footer.html");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

    }
}
