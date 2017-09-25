package com.myoungchi.android.sigmungo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.myoungchi.android.sigmungo.Items.UserInformation;
import com.myoungchi.android.sigmungo.http_client.APIclient;
import com.myoungchi.android.sigmungo.http_client.APIinterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by geni on 2017. 8. 3..
 */

public class MyPage extends AppCompatActivity {
    private UserInformation userInformation;
    private Toolbar toolbar;
    private Spinner dropdown;
    private RecyclerView recyclerView;
    private APIinterface apiInterface;

    private TextView userName, userId, writingCount, sympathyCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.my_page);
        userInformation = new UserInformation();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        userName = (TextView)findViewById(R.id.user_name);
        userId = (TextView)findViewById(R.id.user_id);
        writingCount = (TextView)findViewById(R.id.writing_count);
        sympathyCount = (TextView)findViewById(R.id.sympathy_count);
        dropdown = (Spinner)findViewById(R.id.time);
        recyclerView = (RecyclerView)findViewById(R.id.write_list);

        apiInterface = APIclient.getClient().create(APIinterface.class);

        apiInterface.getPostList("geni429").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject result = response.body();
                userName.setText(result.get("name").getAsString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

        setSupportActionBar(toolbar);

        String[] items = new String[]{"아침(breakfast)", "점심(launch)", "저녁(dinner)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        //get userinformation (통신 구현 필요)
//        userName.setText(userInformation.getUserName());
//        userId.setText(userInformation.getUserId());
//        writingCount.setText(userInformation.getMyWritingCount());
//        sympathyCount.setText(userInformation.getMySympathyCount());
    }

    //툴바에서 back버튼을 클릭할시에 종료시켜주는 코드
    public void onBackBtnClicked(View v){
        finish();
    }
}
