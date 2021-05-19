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
                //为了跳转到注册界面，并实现注册功能
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
                            // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                        }else if(md5Password.equals(restfulPassword)){
                            //一致登录成功
                            //save user object
                            SharedPreferences sp = getSharedPreferences( "signUpUser", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = sp.edit();
                            editor.putString( "username",username);




                            Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();

                            //登录成功后关闭此页面进入主页
                            Intent data=new Intent();
                            //datad.putExtra( ); name , value ;
                            data.putExtra("isLogin",true);
                            //RESULT_OK为Activity系统常量，状态码为-1
                            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                            setResult(RESULT_OK,data);
                            //销毁登录界面
                            LoginActivity.this.finish();
                            //跳转到主界面，登录成功的状态传递到 MainActivity 中
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
        //添加restful接口
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName , "");
    }


    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
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
