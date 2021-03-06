package com.example.fit5046;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class LoginActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back,btnSingUp,btnFindPsw;
    private Button btn_login;
    private String userName,password,spPsw;
    private EditText et_user_name,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }


    private void init() {

        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("Log in");
        tv_back=findViewById(R.id.tv_back);

        btnSingUp=findViewById(R.id.bolean_SignUp);
        btnFindPsw=findViewById(R.id.bolean_fgPassword);
        btn_login=findViewById(R.id.bolean_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginActivity.this.finish();
            }
        });
        //register
        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //???????????????????????????????????????????????????
                //Intent intent=new Intent();
                //intent.setClass(LoginActivity.this,RegisterActivity.class);
                //startActivity(intent);
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });
        //find password
        btnFindPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,MapsActivity.class));

            }
        });
        //login button

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_psw = findViewById( R.id.et_psw );
                et_user_name = findViewById( R.id.et_user_name );
                final String password = et_psw.getText().toString().trim();
                final String username = et_user_name.getText().toString().trim();



                final String md5Password =  password ;

                GetFirstNameAsynckTask getFirstASK = new GetFirstNameAsynckTask();
                getFirstASK.execute(username);

                new AsyncTask<String,Void,String>(){

                    @Override
                    protected String doInBackground(String... Void) {
                        return CallingRestful.findPassword(username);
                    }


                    @Override
                    protected void onPostExecute(String restfulPassword){



                        try {
                            JSONArray credentialList = new JSONArray( restfulPassword );
                            JSONObject credential = credentialList.getJSONObject( 0 );
                            JSONObject credentialpk = credential.getJSONObject( "credentialPK" );
                            restfulPassword = credentialpk.getString("passwordHash");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if(TextUtils.isEmpty(username)){
                            Toast.makeText(LoginActivity.this, "input username", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(TextUtils.isEmpty(password)){
                            Toast.makeText(LoginActivity.this, "input password", Toast.LENGTH_SHORT).show();
                            return;
                            // md5Psw.equals(); ??????????????????????????????????????????????????????SharedPreferences?????????
                        }else if(md5Password.equals(restfulPassword)){
                            //??????????????????
                            //save user object
                            SharedPreferences sp = getSharedPreferences( "signUpUser", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = sp.edit();
                            editor.putString( "username",username);




                            Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();

                            //??????????????????????????????????????????
                            Intent data=new Intent();
                            //datad.putExtra( ); name , value ;
                            data.putExtra("isLogin",true);
                            //RESULT_OK???Activity???????????????????????????-1
                            // ??????????????????????????????????????????data????????????????????????????????????back??????????????????????????????setResult??????data???
                            setResult(RESULT_OK,data);
                            //??????????????????
                            LoginActivity.this.finish();
                            //??????????????????????????????????????????????????? MainActivity ???
                            startActivity(new Intent(LoginActivity.this, home_drawer.class));

                            return;
                        }else if((restfulPassword!=null&&!TextUtils.isEmpty(restfulPassword)&&!md5Password.equals(restfulPassword))){
                            Toast.makeText(LoginActivity.this, "input password incorrect", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            Toast.makeText(LoginActivity.this, "user not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute( );
            }




    });}

    private String readPsw(String userName){
        //??????restful??????
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE????????????????????????
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }


    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo???????????????  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //???????????????
        SharedPreferences.Editor editor=sp.edit();
        //??????boolean?????????????????????
        editor.putBoolean("isLogin", status);
        //?????????????????????????????????
        editor.putString("loginUserName", userName);
        //????????????
        editor.commit();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //?????????????????????????????????????????????
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //?????????????????? et_user_name ??????
                et_user_name.setText(userName);
                //et_user_name?????????setSelection()???????????????????????????
                et_user_name.setSelection(userName.length());
            }
        }
    }
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    private class GetFirstNameAsynckTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return CallingRestful.finUserFirstNameByUsername( strings[0] );

        }
        @Override
        protected void onPostExecute(String result){


            try {
                JSONArray ja = new JSONArray( result );
                JSONObject ja0 = ja.getJSONObject(0 );
                JSONObject theUser = ja0.getJSONObject( "userTable" );
                String firtName = theUser.getString( "userSurname" );
                SharedPreferences sp = getSharedPreferences( "signupDate",MODE_PRIVATE );
                SharedPreferences.Editor edt = sp.edit();
                edt.putString( "firstName",firtName );
                edt.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}
