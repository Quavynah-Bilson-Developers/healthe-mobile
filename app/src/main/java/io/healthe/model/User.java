package io.healthe.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Models a user data source
 */
public class User implements Parcelable {
	public long id;
	public String username;
	public String email;
	public String password;
	public long createdOn;
	
	public User(String username, String email, String password) {
		this.id = System.currentTimeMillis();
		this.createdOn = new Date(System.currentTimeMillis()).getTime();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	protected User(Parcel in) {
		id = in.readLong();
		username = in.readString();
		email = in.readString();
		password = in.readString();
		createdOn = in.readLong();
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
	
	public static class Builder {
		private String username;
		private String email;
		private String password;
		
		public Builder setUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		
		public User build() {
			return new User(username, email, password);
		}
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(createdOn);
		dest.writeLong(id);
		dest.writeString(username);
		dest.writeString(email);
		dest.writeString(password);
	}
}
