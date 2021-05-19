package com.example.fit5046;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FoodSearchAPI {

    public static String search(String keyword) {
        final String API_KEY = "&sort=n&max=25&offset=0&api_key=CtBfz5VnJPdTnbCXaAmxTrkV2Ubjmcftcqtpm05o";
        keyword = keyword.trim().replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";


        try {
            url = new URL("https://api.nal.usda.gov/ndb/search/?format=json&q="+
                    keyword + API_KEY);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }
        return textResult;
    }





    public static String getNdno(String str) {

        String ndbno = null;
        try {
        JSONObject resualt = new JSONObject( str );
        JSONObject init  = resualt.getJSONObject( "list" );
        JSONArray firstItem = init.getJSONArray( "item" );
        JSONObject offset0 = firstItem.getJSONObject( 0 );
        ndbno = offset0.getString( "ndbno" );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ndbno;

    }

    public static String getFoodDetail(String keyword){
        String str = search( keyword );
        String ndbno = getNdno( str );
        String result = null;
        String foodName = "";
        String fatAMO = "";
        String calorieAMO = "";
        String servingUNT = "";
        final String apiURl = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=";
        URL url = null;
        String type = "&type=f&format=json&api_key=";
        String apiKey = "CtBfz5VnJPdTnbCXaAmxTrkV2Ubjmcftcqtpm05o";
        HttpURLConnection connection = null;
        String textResult ="";


        try {
            url = new URL(apiURl+ndbno+type+apiKey);
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            connection.disconnect();
        }

        try {
            JSONObject info = new JSONObject( textResult );
            JSONArray foods = info.getJSONArray( "foods" );
            JSONObject food = foods.getJSONObject( 0 );
            JSONObject foodItem = food.getJSONObject( "food" );
            JSONArray nutrients = foodItem.getJSONArray( "nutrients" );

            servingUNT =  getUnit(nutrients);
            foodName = getFoodName(foodItem);
            calorieAMO = getCalorieAMO(nutrients);
            fatAMO =  getFatAMO(nutrients);

        } catch (JSONException e) {
            e.printStackTrace();
        }


       /* result.put( "name",foodName );
        result.put( "calorieAMO",calorieAMO );
        result.put( "servingUNT",servingUNT );
        result.put( "fatAMO",fatAMO );*/

        result =  foodName+","+servingUNT+","+calorieAMO+","+fatAMO;

        return result;


    }
//---------------------------------------------------------
// extract food detail methods

    private static String getFoodName(JSONObject job){
        try {

            JSONObject desc = job.getJSONObject( "desc" );
            String name = desc.getString( "name" );
            return name;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "no name";

    }


    private static String getCalorieAMO(JSONArray nutrients){



        try {

                for (int i = 0; i < nutrients.length(); i++) {
                    JSONObject nutriItem = nutrients.getJSONObject( i );  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    if (nutriItem.getString( "name" ).trim().equals("Energy")){
                        String result = nutriItem.getString("value"  );
                        return result;

                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "no colories";

    }

    private static String getUnit(JSONArray jay){
        try {

            JSONObject nutri = jay.getJSONObject( 0);
            JSONArray measures = nutri.getJSONArray( "measures" );
            JSONObject  lable = measures.getJSONObject( 0 );
            String unit = lable.getString( "label" );
            return unit;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "no unit";
    }


    private static String getFatAMO(JSONArray nutrients){


        try {
            for (int i = 0; i < nutrients.length(); i++) {
                JSONObject nutriItems = nutrients.getJSONObject( i );
                if (nutriItems.getString( "nutrient_id" ).trim().equals("204")){
                    String fat = nutriItems.getString( "value" );
                    return fat;
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "no fat";

    }






 //----------------------------------------------------------------
}
