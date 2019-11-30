import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        storeReview(request, response);
    }

    protected void storeReview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to add items to cart");
                response.sendRedirect("Login");
                return;
            }
            String ProductModelName = request.getParameter("ProductModelName");
            String ProductCategory = request.getParameter("ProductCategory");
            String ProductPrice = request.getParameter("ProductPrice");
            String RetailerName = request.getParameter("RetailerName");
            String RetailerZip = request.getParameter("RetailerZip");
            String RetailerCity = request.getParameter("RetailerCity");
            String RetailerState = request.getParameter("RetailerState");
            String ProductOnSale = request.getParameter("ProductOnSale");
            String ManufacturerName = request.getParameter("ManufacturerName");
            String ManufacturerRebate = request.getParameter("ManufacturerRebate");
            String UserID = request.getParameter("UserID");
            String UserAge = request.getParameter("UserAge");
            String UserGender = request.getParameter("UserGender");
            String UserOccupation = request.getParameter("UserOccupation");
            String ReviewRating = request.getParameter("ReviewRating");
            String ReviewDate = request.getParameter("ReviewDate");
            String ReviewText = request.getParameter("ReviewText");

            String message = utility.storeReview(ProductModelName, ProductCategory, ProductPrice, RetailerName,
                    RetailerZip, RetailerCity, RetailerState, ProductOnSale, ManufacturerName, ManufacturerRebate,
                    UserID, UserAge, UserGender, UserOccupation, ReviewRating, ReviewDate, ReviewText);

            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");
            pw.print("<form name ='Cart' action='CheckOut' method='post'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Review</a>");
            pw.print("</h2><div class='entry'>");
            if (message.equals("Successfull"))
                pw.print("<h2>Review for &nbsp" + ProductModelName + " Stored </h2>");
            else
                pw.print("<h2>Mongo Db is not up and running </h2>");

            pw.print("</div></div></div><div class='clear'></div>");
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
