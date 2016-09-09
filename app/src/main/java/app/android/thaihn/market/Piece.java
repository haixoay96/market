package app.android.thaihn.market;

import java.io.Serializable;

/**
 * Created by haixo on 4/16/2016.
 */
public class Piece implements Serializable {
    public static Piece cache = null;
    public static String DIENTHOAI = "Điện Thoại";
    public static String LAPTOP = "LapTop";
    public static String TAINGHE = "Tai Nghe";
    public static String LOA = "Loa";
    public static String TIVI ="Ti vi";
    public static String DOKHAC = "Đồ Vật Khác";

    public static String TITLE = "title";
    public static String CATEGORY= "category";
    public static String IMAGE = "image";
    public static String USER = "user";
    public static String PHONENUMBER = "phoneNumber";
    public static String DESCRIP = "descrip";
    private String title;
    private String category;
    private String image;
    private String user;
    private String phoneNumber;
    private String descrip;

    public Piece() {
    }

    public Piece(String title, String category, String image, String user, String phoneNumber, String descrip) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.user = user;
        this.phoneNumber = phoneNumber;
        this.descrip = descrip;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
}
