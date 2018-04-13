package io.healthe.util;

import io.healthe.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Models the Healthe API
 */
public interface HealtheService {
	
	@FormUrlEncoded
	@POST("api/login_user.php")
	Call<User> loginUser(@Field("email") String email, @Field("password") String password);
	
	//Creates new user
	@FormUrlEncoded
	@POST("api/create_user.php")
	Call<User> createUser(
			@Field("name") String name,
			@Field("dob") String dob,
			@Field("weight") String weight,
			@Field("height") String height,
			@Field("email") String email,
			@Field("user_password") String password
	);
}
