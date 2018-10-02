package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            sandwich = new Sandwich();
            JSONObject sandwichJsonObject = new JSONObject(json);

            JSONObject sandwichNameJsonObject = sandwichJsonObject.getJSONObject("name");
            JSONArray sandwichAlsoKnowAsJsonArray = sandwichNameJsonObject.getJSONArray("alsoKnownAs");
            JSONArray ingredientsJsonArray = sandwichJsonObject.getJSONArray("ingredients");

            sandwich.setAlsoKnownAs(convertJsonArrayToStringArray(sandwichAlsoKnowAsJsonArray));
            sandwich.setIngredients(convertJsonArrayToStringArray(ingredientsJsonArray));
            sandwich.setMainName(sandwichNameJsonObject.getString("mainName"));
            sandwich.setDescription(sandwichJsonObject.getString("description"));
            sandwich.setPlaceOfOrigin(sandwichJsonObject.getString("placeOfOrigin"));
            sandwich.setImage(sandwichJsonObject.getString("image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    //Convert Json to String Array
    private static List<String> convertJsonArrayToStringArray(JSONArray jsonArray){
        List<String> stringArray = new ArrayList<>();

        for(int i = 0, count = jsonArray.length(); i< count; i++) {
            try {
                String jsonString = jsonArray.getString(i);
                stringArray.add(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return stringArray;
    }
}
