package com.example.android.myretrofitapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.myretrofitapp.data.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserRecyclerAdapter.OnUserListener{

    private RecyclerView usersRecyclerView;
    private UserRecyclerAdapter usersRecyclerAdapter;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //add retrofit library in the gradle file.
        //First of all as wer are going to communicate with a remote server we need to add this to
        //AndroidManifest.xml
        //<uses-permission android:name="android.permission.INTERNET"/>

        /*
        go to
            https://jsonplaceholder.typicode.com/users
        then copy the data into this webpage:
            http://www.jsonschema2pojo.org/
        It will generate an appropriate class hierarchy for the data


        You can execute Retrofit requests synchronously using call.execute(), or asynchronously
        using call.enqueue. Synchronous requests get executed on the main thread and run the risk
        of blocking the main UI thread across all versions of Android. In addition, if you attempt
        to execute a Retrofit request synchronously on Android 4.0 or higher, then your application
        will crash with a `NetworkOnMainThreadException` error. So, we’ll be using the enqueue()
        method to send our request asynchronously.

        So create

         */
        //Create a handler for the RetrofitInstance interface
        final Endpoint service = RetrofitClient.getRetrofitInstance().create(Endpoint.class);
        Call<List<User>> call = service.getAllUsers();
        //execute the request asynchronously
        doAsynCall(call);

        //from here create a RecyclerView and an UserRecyclerAdapter
        //when you finish the proceed to declare as global RecyclerView and the adapter
        //initialise the recycler view
        initRecyclerView();

        //make the fab to work calling the data from the website
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Call<List<User>> call = service.getAllUsers();
                doAsynCall(call);
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                 */
            }
        });

    }

    private void initRecyclerView() {
        //Get a reference to the RecyclerView
        usersRecyclerView = findViewById(R.id.usersRecyclerView);
        //create the adapter and pass the data
        usersRecyclerAdapter = new UserRecyclerAdapter(users, this);
        //Use a LinearLayoutManager with default vertical orientation
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        usersRecyclerView.setLayoutManager(layoutManager);
//        usersRecyclerView.setHasFixedSize(true);
        //set the adapter
        usersRecyclerView.setAdapter(usersRecyclerAdapter);
    }

    private void doAsynCall(Call<List<User>> call) {
        call.enqueue(new Callback<List<User>>() {
            //handle a successful response
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                loadData(response.body());
            }
            //handle execution failures
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(List<User> users) {
        //up to here you can test the data you've got from the restful web service.
        Log.d("XXXXX", users.toString());
        usersRecyclerAdapter.setUsers(users);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserClick(int position) {

        User user = usersRecyclerAdapter.getUsers().get(position);
        Toast.makeText(this, user.getUsername(), Toast.LENGTH_SHORT).show();

        /*

        Intent intent = new Intent(this, Post.class);
        startActivity(intent);
        create a new activity and then pass the data you want, and in this activity
        you can check the user posts for example.        /*

        Intent intent = new Intent(this, Post.class);
        startActivity(intent);

         */


    }
}
