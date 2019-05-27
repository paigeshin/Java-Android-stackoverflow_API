package georgieva.vyara.stackoverflow_api.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import georgieva.vyara.stackoverflow_api.R;
import georgieva.vyara.stackoverflow_api.adapter.UsersAdapter;
import georgieva.vyara.stackoverflow_api.model.User;
import georgieva.vyara.stackoverflow_api.model.UsersReceived;
import georgieva.vyara.stackoverflow_api.rest.APIClient;
import georgieva.vyara.stackoverflow_api.rest.UserEndPoints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<User> myDataSource = new ArrayList<>();
    RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.usersRecyslerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new UsersAdapter(myDataSource, R.layout.list_item_user,
                getApplicationContext());

        mRecyclerView.setAdapter(myAdapter);

        loadUsers();
    }

    private void loadUsers() {

        UserEndPoints apiService =
                APIClient.getClient().create(UserEndPoints.class);

        Call<UsersReceived> call = apiService.getUsers("reputation");
        call.enqueue(new Callback<UsersReceived>() {
            @Override
            public void onResponse(Call<UsersReceived> call, Response<UsersReceived> response) {


                myDataSource.clear();
                myDataSource.addAll(response.body().getUsers());
                myAdapter.notifyDataSetChanged();

                List<User> users = response.body().getUsers();
                for (User user : users) {
                    System.out.println(
                            "Name: " + user.getUserName() +
                                    "; Location: " + user.getLocation() +
                                    "; Reputation:  " + user.getReputation()
                    );

                    System.out.println("Badges: ");

                    for (Map.Entry<String, Integer> entry : user.getBadges().entrySet()) {
                        String key = entry.getKey().toString();
                        Integer value = entry.getValue();
                        System.out.println(key + " : " + value);
                    }
                }

            }

            @Override
            public void onFailure(Call<UsersReceived> call, Throwable t) {
                System.out.println("FAILURE");

            }
        });

    }
}