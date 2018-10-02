package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView sandwichImageIv;
    TextView sandwichImageErrorTv;
    TextView descriptionLabelTv;
    TextView descriptionTv;
    TextView ingredientsLabelTv;
    TextView ingredientsTv;
    TextView alsoKnownLabelTv;
    TextView alsoKnownTv;
    TextView originLabelTv;
    TextView originTv;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //assign labels
        descriptionLabelTv = findViewById(R.id.description_label_tv);
        ingredientsLabelTv = findViewById(R.id.ingredients_label_tv);
        alsoKnownLabelTv = findViewById(R.id.also_known_label_tv);
        originLabelTv = findViewById(R.id.origin_label_tv);

        //assign image view and error text view
        sandwichImageIv = findViewById(R.id.image_iv);
        sandwichImageErrorTv = findViewById(R.id.image_error_tv);

        //assign text views
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        alsoKnownTv = findViewById(R.id.also_known_tv);
        originTv = findViewById(R.id.origin_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        //initial sandwich object
        sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Handle Image not load error and image url not included
        if(!TextUtils.isEmpty(sandwich.getImage())){
            Picasso.with(this).load(sandwich.getImage()).into(sandwichImageIv, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    sandwichImageErrorTv.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError() {
                    sandwichImageErrorTv.setVisibility(View.VISIBLE);
                }
            });
        }else{
            sandwichImageErrorTv.setVisibility(View.VISIBLE);
        }

        //Null Error Handle
        if(!TextUtils.isEmpty(sandwich.getMainName())){
            setTitle(sandwich.getMainName());
        }

        //Null Error Handle and remove Description label if description is not given
        if(!TextUtils.isEmpty(sandwich.getDescription())){
            descriptionTv.setText(sandwich.getDescription());
        }else{
            descriptionTv.setVisibility(View.GONE);
            descriptionLabelTv.setVisibility(View.GONE);
        }

        //Null Error Handle and remove alsoKnowsAs label if alsoKnowsAs array 0
        if(sandwich.getAlsoKnownAs()!=null && sandwich.getAlsoKnownAs().size() > 0){
            for (String alsoKnown: sandwich.getAlsoKnownAs()){
                alsoKnownTv.append(alsoKnown);
            }
        }else{
            alsoKnownTv.setVisibility(View.GONE);
            alsoKnownLabelTv.setVisibility(View.GONE);
        }

        //Null Error Handle and remove ingredient label if ingredient array 0
        if(sandwich.getIngredients()!= null && sandwich.getIngredients().size()>0){
            for (String ingredient: sandwich.getIngredients()){
                ingredientsTv.append(ingredient);
            }
        }else{
            ingredientsTv.setVisibility(View.GONE);
            ingredientsLabelTv.setVisibility(View.GONE);
        }

        //Null Error Handle and remove origin label if origin is not given
        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin()) ){
            originTv.setText(sandwich.getPlaceOfOrigin());
        }else{
            originTv.setVisibility(View.GONE);
            originLabelTv.setVisibility(View.GONE);
        }
    }
}
