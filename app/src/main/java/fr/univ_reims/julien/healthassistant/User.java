package fr.univ_reims.julien.healthassistant;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Julien on 12/02/2017.
 */

public class User implements Parcelable {
    private int id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Calendar birthday;

    public User(int id, String login, String email, String firstName, String lastName, String birthday) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            this.birthday.setTime(sdf.parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public User(JSONObject o) {
        try {
            this.id = o.getInt("id");
            this.login = o.getString("login");
            this.email = o.getString("email");
            this.firstName = o.getString("firstName");
            this.lastName = o.getString("lastName");
            this.birthday = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            this.birthday.setTime(sdf.parse(o.getString("birthday")));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected User(Parcel in) {
        id = in.readInt();
        login = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        birthday = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            birthday.setTime(sdf.parse(in.readString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        dest.writeInt(id);
        dest.writeString(login);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(sdf.format(birthday.getTime()));
    }
}
