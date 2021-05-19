package com.example.fit5046;


import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyDietUnitFragment extends Fragment {
    //---------------------------------------------
    List<HashMap<String, String>> foodListArray;
    SimpleAdapter myListAdapter;
    ListView foodList;

    HashMap<String,String> map = new HashMap<String,String>();
    String[] colHEAD = new String[] {"name","category","servingUnit","calorieAmount","fat"};
    int[] dataCell = new int[] {R.id.list_food_name,R.id.list_food_category,R.id.list_food_servingUnit,R.id.list_foodCalorie,R.id.list_foodFat};

    String category;
    Button searchFood;

    //----------------------------------------------------
    View vEnterUnit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle
            savedInstanceState) {
        vEnterUnit = inflater.inflate(R.layout.fragment_diet, container, false);
        searchFood = vEnterUnit.findViewById( R.id.btn_searchFood );
        searchFood.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddNewFood.class);
                startActivity(intent);

            }
        } );

        init();

        return vEnterUnit;




    }

    public void init(){








        //spinner
        final Spinner sp_loa = vEnterUnit.findViewById( R.id.sp_foodCate_daily );
        List<String> loaList = new ArrayList<String>(  );

        loaList.add( "Candy" );
        loaList.add( "Bacon" );
        loaList.add( "Candy bars" );
        loaList.add( "Cabbage" );
        loaList.add( "Chocolate" );




        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_spinner_item, loaList);




        sp_loa.setAdapter( spinnerAdapter );

        sp_loa.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCategory = parent.getItemAtPosition( position ).toString();
                if(selectedCategory != null){


                    category = selectedCategory;
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... strings) {
                            return CallingRestful.findFoodByCategory( strings[0] );
                        }
                        @Override
                        protected void onPostExecute(String response){
                            foodListArray = new ArrayList<HashMap<String, String>>();
                            map = new HashMap<String,String>();
                            foodList = vEnterUnit.findViewById(R.id.tv_foodList);
                            try {
                                JSONArray findList = new JSONArray( response );

                                for (int i = 0; i<findList.length();i++){
                                    JSONObject itemFood = findList.getJSONObject( i );

                                    String name = itemFood.getString( "foodName" );
                                    String calorieAmount = itemFood.getString("calorieAmount");
                                    String category = itemFood.getString( "category" );
                                    String fat = itemFood.getString( "fat" );
                                    String servingUnit = itemFood.getString( "servingUnit" );
                                    map.put("name", name);
                                    map.put("category",category );
                                    map.put("servingUnit", servingUnit);
                                    map.put("calorieAmount",calorieAmount);
                                    map.put("fat",fat);
                                    foodListArray.add(map);

                                }

                                myListAdapter = new SimpleAdapter(getActivity(),foodListArray,R.layout.food_list_view,colHEAD,dataCell);
                                foodList.setAdapter(myListAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }.execute(category  );
                    Toast.makeText(parent.getContext(), "level of activity selected is " + selectedCategory,
                            Toast.LENGTH_LONG).show();



                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }});








    }
//--------------------------------------------------------------------------
//put text view

    


}
