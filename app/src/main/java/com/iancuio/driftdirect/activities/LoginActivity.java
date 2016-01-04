package com.iancuio.driftdirect.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.customObjects.User;
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

    ProgressDialog dialog;

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

        dialog = ProgressDialog.show(this, "Logging In!", "Please Wait!");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();
        UserLoginService userLoginService;
        userLoginService = retrofit.create(UserLoginService.class);

        byte[] loginData = new byte[0];
        try {
            loginData = (nameEditText.getText().toString() + ":" + passwordEditText.getText().toString()).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String base64LoginData = Base64.encodeToString(loginData, Base64.DEFAULT);

        Call<User> userCall = userLoginService.getUserDetails(("Basic " + base64LoginData).trim());

        userCall.enqueue(new retrofit.Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {

                User user = response.body();

                SharedPreferences sharedPreferences = getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("token", ("Basic " + base64LoginData).trim());
                editor.putStringSet("roles", user.getRoles());
                editor.commit();

                dialog.dismiss();
                Toast.makeText(LoginActivity.this, "Successfully logged in! Welcome " + user.getUsername(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("failure",t.toString());

            }
        });
    }
}
