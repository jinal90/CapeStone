
package com.udacity.food.feasta.foodfeasta.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Fooditem implements Parcelable{

    private String category;
    private String image;
    private String long_desc;
    private String name;
    private String price;
    private String short_desc;

    public Fooditem(){

    }

    protected Fooditem(Parcel in) {
        category = in.readString();
        image = in.readString();
        long_desc = in.readString();
        name = in.readString();
        price = in.readString();
        short_desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(image);
        dest.writeString(long_desc);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(short_desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Fooditem> CREATOR = new Creator<Fooditem>() {
        @Override
        public Fooditem createFromParcel(Parcel in) {
            return new Fooditem(in);
        }

        @Override
        public Fooditem[] newArray(int size) {
            return new Fooditem[size];
        }
    };

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The long_desc
     */
    public String getLong_desc() {
        return long_desc;
    }

    /**
     * 
     * @param long_desc
     *     The long_desc
     */
    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The short_desc
     */
    public String getShort_desc() {
        return short_desc;
    }

    /**
     * 
     * @param short_desc
     *     The short_desc
     */
    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

}
