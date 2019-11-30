import java.io.IOException;
import java.io.*;

public class Review implements Serializable {
    private String ProductModelName;
    private String ProductCategory;
    private String ProductPrice;
    private String RetailerName;
    private String RetailerZip;
    private String RetailerCity;
    private String RetailerState;
    private String ProductOnSale;
    private String ManufacturerName;
    private String ManufacturerRebate;
    private String UserID;
    private String UserAge;
    private String UserGender;
    private String UserOccupation;
    private String ReviewRating;
    private String ReviewDate;
    private String ReviewText;

    public Review(String productModelName, String productCategory, String productPrice, String retailerName,
            String retailerZip, String retailerCity, String retailerState, String productOnSale,
            String manufacturerName, String manufacturerRebate, String userID, String userAge, String userGender,
            String userOccupation, String reviewRating, String reviewDate, String reviewText) {
        ProductModelName = productModelName;
        ProductCategory = productCategory;
        ProductPrice = productPrice;
        RetailerName = retailerName;
        RetailerZip = retailerZip;
        RetailerCity = retailerCity;
        RetailerState = retailerState;
        ProductOnSale = productOnSale;
        ManufacturerName = manufacturerName;
        ManufacturerRebate = manufacturerRebate;
        UserID = userID;
        UserAge = userAge;
        UserGender = userGender;
        UserOccupation = userOccupation;
        ReviewRating = reviewRating;
        ReviewDate = reviewDate;
        ReviewText = reviewText;
    }

    public Review(String productName, String retailerpin, String reviewRating, String reviewText) {
        this.ProductModelName = productName;
        this.RetailerZip = retailerpin;
        this.ReviewRating = reviewRating;
        this.ReviewText = reviewText;
    }

    public String getProductModelName() {
        return ProductModelName;
    }

    public void setProductModelName(String productModelName) {
        ProductModelName = productModelName;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getRetailerName() {
        return RetailerName;
    }

    public void setRetailerName(String retailerName) {
        RetailerName = retailerName;
    }

    public String getRetailerZip() {
        return RetailerZip;
    }

    public void setRetailerZip(String retailerZip) {
        RetailerZip = retailerZip;
    }

    public String getRetailerCity() {
        return RetailerCity;
    }

    public void setRetailerCity(String retailerCity) {
        RetailerCity = retailerCity;
    }

    public String getRetailerState() {
        return RetailerState;
    }

    public void setRetailerState(String retailerState) {
        RetailerState = retailerState;
    }

    public String getProductOnSale() {
        return ProductOnSale;
    }

    public void setProductOnSale(String productOnSale) {
        ProductOnSale = productOnSale;
    }

    public String getManufacturerName() {
        return ManufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        ManufacturerName = manufacturerName;
    }

    public String getManufacturerRebate() {
        return ManufacturerRebate;
    }

    public void setManufacturerRebate(String manufacturerRebate) {
        ManufacturerRebate = manufacturerRebate;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public String getUserOccupation() {
        return UserOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        UserOccupation = userOccupation;
    }

    public String getReviewRating() {
        return ReviewRating;
    }

    public void setReviewRating(String reviewRating) {
        ReviewRating = reviewRating;
    }

    public String getReviewDate() {
        return ReviewDate;
    }

    public void setReviewDate(String reviewDate) {
        ReviewDate = reviewDate;
    }

    public String getReviewText() {
        return ReviewText;
    }

    public void setReviewText(String reviewText) {
        ReviewText = reviewText;
    }

}