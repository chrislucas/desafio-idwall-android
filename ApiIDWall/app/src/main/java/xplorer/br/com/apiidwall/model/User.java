package xplorer.br.com.apiidwall.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private String id, token, email;

    public User() { }

    private User(Parcel parcel) { readToParcel(parcel); }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel writer, int i) {
        writer.writeString(id);
        writer.writeString(token);
        writer.writeString(email);
    }

    private void readToParcel(Parcel reader) {
        id = reader.readString();
        token = reader.readString();
        email = reader.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    @Override
    public String toString() {
        return String.format("Email: %s.\nID : %s.\n Token: %s.", email, id, token);
    }
}
