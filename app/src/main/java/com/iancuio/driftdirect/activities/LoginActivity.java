package com.iancuio.driftdirect.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.fasterxml.jackson.databind.JsonNode;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.service.UserLoginService;
import com.iancuio.driftdirect.utils.RestUrls;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.button_LoginActivityLayout_login)
    Button loginButton;
    @Bind(R.id.editText_LoginActivityLayout_name)
    EditText nameEditText;
    @Bind(R.id.editText_LoginActivityLayout_password)
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @OnClick(R.id.button_LoginActivityLayout_login)
    public void loginButtonClick(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();
        UserLoginService forecastIoService;
        forecastIoService = retrofit.create(UserLoginService.class);

        byte[] loginData = new byte[0];
        try {
            loginData = (nameEditText.getText().toString() + ":" + passwordEditText.getText().toString()).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64LoginData = Base64.encodeToString(loginData, Base64.DEFAULT);

        Call<JsonNode> forecastIoCall = forecastIoService.getUserDetails(("Basic " + base64LoginData).trim());

        forecastIoCall.enqueue(new retrofit.Callback<JsonNode>() {
            @Override
            public void onResponse(Response<JsonNode> response, Retrofit retrofit) {
                JsonNode data = response.body();
                Log.e("response", response.body().get("username").asText());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("failure",t.toString());

            }
        });
    }
}
