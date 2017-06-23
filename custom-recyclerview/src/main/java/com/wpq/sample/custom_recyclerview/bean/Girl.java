package com.wpq.sample.custom_recyclerview.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wpq
 * @version 1.0
 */
public class Girl implements Parcelable {

    private String title;
    private String url;
    private String id;
    private int width;
    private int height;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public Girl() {
    }

    protected Girl(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.id = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<Girl> CREATOR = new Parcelable.Creator<Girl>() {
        @Override
        public Girl createFromParcel(Parcel source) {
            return new Girl(source);
        }

        @Override
        public Girl[] newArray(int size) {
            return new Girl[size];
        }
    };
}
