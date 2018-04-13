package io.healthe.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Models a user data source
 */
public class User implements Parcelable {
	public long id;
	public String name;
	public String dob;
	public String weight;
	public String height;
	public String email;
	public String user_password;
	
	public User() {
	}
	
	public User(long id, String name, String dob, String weight, String height, String email, String user_password) {
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.email = email;
		this.user_password = user_password;
	}
	
	public static class Builder {
		private String username;
		private String email;
		private String dob;
		private String weight;
		private String height;
		private String password;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder setDob(String dob) {
			this.dob = dob;
			return this;
		}
		
		public Builder setWeight(String weight) {
			this.weight = weight;
			return this;
		}
		
		public Builder setHeight(String height) {
			this.height = height;
			return this;
		}
		
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public User build() {
			return new User(System.currentTimeMillis(), username, dob, weight, height, email,password);
		}
	}
	
	
	protected User(Parcel in) {
		id = in.readLong();
		name = in.readString();
		dob = in.readString();
		weight = in.readString();
		height = in.readString();
		email = in.readString();
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(name);
		dest.writeString(dob);
		dest.writeString(weight);
		dest.writeString(height);
		dest.writeString(email);
	}
	
	@Override
	public int describeContents() {
		return 0;
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
}
