package ars.ramsey.interviewhelper.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramsey on 2017/5/9.
 */

public class Author implements Parcelable {

    private String bio;

    private String hash;

    private String description;

    private String profileUrl;

    //private Avatar avatar;

    private String slug;

    private String name;

    protected Author(Parcel in) {
        this.bio = in.readString();
        this.hash = in.readString();
        this.description = in.readString();
        this.profileUrl = in.readString();
        //this.avatar = in.readParcelable(Avatar.class.getClassLoader());
        this.slug = in.readString();
        this.name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bio);
        dest.writeString(this.hash);
        dest.writeString(this.description);
        dest.writeString(this.profileUrl);
        //dest.writeParcelable(this.avatar, flags);
        dest.writeString(this.slug);
        dest.writeString(this.name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
