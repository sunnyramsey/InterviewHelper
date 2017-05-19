package ars.ramsey.interviewhelper.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramsey on 2017/5/19.
 */

public class Avatar implements Parcelable{

    private String id;
    private String template;

    public Avatar(String id, String template) {
        this.id = id;
        this.template = template;
    }

    protected Avatar(Parcel in) {
        id = in.readString();
        template = in.readString();
    }

    public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
        @Override
        public Avatar createFromParcel(Parcel in) {
            return new Avatar(in);
        }

        @Override
        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(template);
    }
}
