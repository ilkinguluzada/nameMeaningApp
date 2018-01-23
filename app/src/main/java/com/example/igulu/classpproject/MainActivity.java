package com.example.igulu.classpproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView resultName;
    TextView resultMeaning;
    View resultHolder;
    EditText name;
    String nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        resultName = findViewById(R.id.resultName);
        resultMeaning = findViewById(R.id.resultMeaning);
        resultHolder = findViewById(R.id.resultHolder);
    }

    public void sendRequest(View view){
        nameTxt = name.getText().toString();
        nameTxt = nameTxt.substring(0, 1).toUpperCase() + nameTxt.substring(1);
        nameTxt = nameTxt.replace("I", "İ");
        sendWithRetrofit(nameTxt);
    }


    private void sendWithRetrofit(String name){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AppConfig.BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppInterface apiInterface = retrofit.create(AppInterface.class);

        Call<Object> test = apiInterface.Search(name);

        test.enqueue(new Callback<Object>() {


            @Override
            public void onResponse(Call<Object> call, retrofit2.Response<Object> response) {
                // System.out.println(response.body().toString());
                Object meaning = response.body();
                 String jsonString = new Gson().toJson(meaning, Object.class);

                 try{

                     JSONObject jsonObject = new JSONObject(jsonString);

                     if (!jsonObject.has("Response")){
                         Toast.makeText(MainActivity.this, "Belə Ad Mövcud Deyil!",
                                 Toast.LENGTH_LONG).show();
                     }else{
                         JSONObject responseJson = jsonObject.getJSONObject("Response");
                         resultMeaning.setText(responseJson.getString("Meaning"));
                         resultHolder.setVisibility(View.VISIBLE);
                         resultName.setText(nameTxt);
                     }

                 }catch (Exception e){
                     e.printStackTrace();
                 }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });



    }
}
