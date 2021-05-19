package com.example.fit5046;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class DietFragment extends Fragment implements View.OnClickListener  {

    private View vDisplayAPIResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayAPIResult = inflater.inflate(R.layout.activity_daily_diet, container,
                false);

        final EditText editText=(EditText) vDisplayAPIResult.findViewById(R.id.editText) ;
        Button btnSearch = (Button) vDisplayAPIResult.findViewById(R.id.btnSearchInfo);
        Button btnSearchNutrition = (Button) vDisplayAPIResult.findViewById(R.id.btnSearchNutrition);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                DietFragment.SearchGoogleAsyncTask searchGoogleAsyncTask=new DietFragment.SearchGoogleAsyncTask();
                searchGoogleAsyncTask.execute(keyword);

            }
        });


        btnSearchNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                DietFragment.SearchFatSecreteAsyncTask searchFateSecreteAsyncTask=new DietFragment.SearchFatSecreteAsyncTask();
                searchFateSecreteAsyncTask.execute(keyword);

            }
        });


        return vDisplayAPIResult;
    }
    @Override
    public void onClick(View v) {
//        // Get myUnits file
//        SharedPreferences spMyUnits =
//                getActivity().getSharedPreferences("myUnits", Context.MODE_PRIVATE);
//        String units= spMyUnits.getString("message",null);
//        tvDisplayUnits.setText(units);
    }



    private class SearchFatSecreteAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SearchFatSecretAPI api = new SearchFatSecretAPI();
            String s= api.searchFood(params[0],1);
            return s;
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) vDisplayAPIResult.findViewById(R.id.tvResult);
            tv.setText(SearchFatSecretAPI.getSnippet(result));
        }
    }

    private class SearchGoogleAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new
                    String[]{"1"});
        }
        @Override
        protected void onPostExecute(String result) {
            TextView tv= (TextView) vDisplayAPIResult.findViewById(R.id.tvResult);
            tv.setText(SearchGoogleAPI.getSnippet(result));
            String url = SearchGoogleAPI.getImageUrl(result);
            new DietFragment.DownloadImageFromInternet((ImageView) vDisplayAPIResult.findViewById(R.id.imageId))
                    .execute(url);
        }
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(getActivity().getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

}
