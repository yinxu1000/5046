package com.example.fit5046;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AddNewFood extends AppCompatActivity {
    private TextView tv_back;
    private EditText et_inputWord;
    static URL share_url;
    private ListView foodList;
    private Button btn_search;
    private Context addfoodact = this;


    List<HashMap<String, String>> foodListArray;
    SimpleAdapter myListAdapter;

    HashMap<String,String> map = new HashMap<String,String>();
    String[] colHEAD = new String[] {"result","detail"};
    int[] dataCell = new int[] {R.id.tv_list_items,R.id.tv_list_detail};

    //------------------------------------------------------------------------------



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        tv_back = findViewById( R.id.tv_back );
        tv_back.setOnClickListener( new TextView.OnClickListener() {


            @Override
            public void onClick(View v) {
                AddNewFood.this.finish();
            }
        } );

        init();
    }

    private void init(){


        btn_search = findViewById( R.id.btn_searchButton );
        btn_search.setOnClickListener( new Button.OnClickListener() {



            @Override
            public void onClick(View v) {

                et_inputWord = findViewById( R.id.et_foodSearch );
                String inputWord = et_inputWord.getText().toString();

                SearchAsynckTask search = new SearchAsynckTask(addfoodact);
                search.execute(inputWord);


                SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(inputWord);

            }


        } );



    }


    private class SearchAsynckTask extends AsyncTask<String,Void, String> {
        private Context context;
        public SearchAsynckTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            return  FoodSearchAPI.getFoodDetail( strings[0] );

        }

        @Override
        protected void onPostExecute(String response){
            foodListArray = new ArrayList<HashMap<String, String>>();
            foodList = findViewById(R.id.list_searchResualt);
            List<String> list = Arrays.asList(response.split( "," ));
            String name = list.get( 0 );
            String unit = list.get( 1 );
            String calorie = list.get( 2 );
            String fat = list.get( 3 );


            String nameIs = "name: " ;
            String detail = "uint: "+unit+"\n"+"calories: "+calorie+"\n"+"fat:"+fat;
            map.put("result", nameIs);
            map.put( "detail",detail );
            foodListArray.add(map);

            myListAdapter = new SimpleAdapter(context,foodListArray,R.layout.search_resualt,colHEAD,dataCell);
            foodList.setAdapter(myListAdapter);
        }

    }

    private class SearchAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String imageObject = SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
            String imageLink = SearchGoogleAPI.getImageUrl(imageObject);
            Bitmap image = SearchGoogleAPI.getBitmapFromURL(imageLink);
            return image;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView iv = findViewById(R.id.img_search_result);
            iv.setImageBitmap(result);
        }
    }
    

}
