package com.lexandroid.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonModel implements Parcelable {
    private int id;
    private String birthday, known_for_department, deathday, name, biography, place_of_birth, profile_path;
    private float popularity;

    public PersonModel(int id, String birthday, String known_for_department, String deathday, String name, String biography, String place_of_birth, float popularity, String profile_path) {
        this.id = id;
        this.birthday = birthday;
        this.known_for_department = known_for_department;
        this.deathday = deathday;
        this.name = name;
        this.biography = biography;
        this.place_of_birth = place_of_birth;
        this.popularity = popularity;
        this.profile_path = profile_path;
    }

    protected PersonModel(Parcel in) {
        id = in.readInt();
        birthday = in.readString();
        known_for_department = in.readString();
        deathday = in.readString();
        name = in.readString();
        biography = in.readString();
        place_of_birth = in.readString();
        popularity = in.readFloat();
        profile_path = in.readString();
    }

    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
        @Override
        public PersonModel createFromParcel(Parcel in) {
            return new PersonModel(in);
        }

        @Override
        public PersonModel[] newArray(int size) {
            return new PersonModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public String getDeathday() {
        return deathday;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(birthday);
        parcel.writeString(known_for_department);
        parcel.writeString(deathday);
        parcel.writeString(name);
        parcel.writeString(biography);
        parcel.writeString(place_of_birth);
        parcel.writeFloat(popularity);
        parcel.writeString(profile_path);
    }
}
