package com.example.beourguest;

public class booking {
    private String club_name;
    private String date,booking_id,gtotal,qrkey;
    private int stags,couples,image;
    booking(String club_name, String date, int stags, int couples, int image, String id, String total, String key)
    {
        this.club_name=club_name;
        this.date=date;
        this.stags=stags;
        this.couples=couples;
        this.image=image;
        this.booking_id=id;
        this.gtotal=total;
        this.qrkey=key;
    }
    String getClub_name()
    {
        return club_name;
    }
    String getDate()
    {
        return date;
    }
    String getCount()
    {
        return stags+" Stags / "+couples+" Couples";
    }
    String getID() {
        return booking_id;
    }
    String getstag() {
        return String.valueOf(stags);
    }
    String getcouple() {
        return String.valueOf(couples);
    }
    String getgtotal() {
        return gtotal;
    }
    String getkey() {
        return qrkey;
    }
    int getImage()
    {
        return image;
    }
}
