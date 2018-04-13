package io.healthe.util;

import io.healthe.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Models the Healthe API
 */
public interface HealtheService {
	
	//Retrieves
	@FormUrlEncoded
	@POST("api/users.php")
	Call<User> getCurrentUser(@Field("email") String email, @Field("password") String password);
	
	//Creates new user
	@FormUrlEncoded
	@POST("api/create_user.php")
	Call<Void> createUser(@Body User user);
}
