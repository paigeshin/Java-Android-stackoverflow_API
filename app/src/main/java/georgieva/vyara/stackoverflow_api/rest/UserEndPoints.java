package georgieva.vyara.stackoverflow_api.rest;

import georgieva.vyara.stackoverflow_api.model.UsersReceived;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserEndPoints {
    @GET("/2.2/users?page=1&pagesize=5&order=desc&site=stackoverflow")
    Call<UsersReceived> getUsers(@Query("sort") String sort);
}


