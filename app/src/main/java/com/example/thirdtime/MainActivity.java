package com.example.thirdtime;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResults;
    private RequestQueue mQueue;
    EditText inputText; //Accepts ISBN 10 and ISBN 13 (SHOULD ONLY ACCEPT #### , RESTRICT ALPHABET)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResults = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        inputText = (EditText) findViewById(R.id.textfield_1);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });

    }

    private void jsonParse() {


        String url = "https://www.googleapis.com/books/v1/volumes?q=" + inputText.getText(); //Google Books API
        String bk_price = String.valueOf(inputText.getText());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray itemsArray = response.getJSONArray("items");
                                JSONObject itemsObj = itemsArray.getJSONObject(0); //To get first index
                                JSONObject volumeObj = itemsObj.getJSONObject("volumeInfo");
                                String title = volumeObj.optString("title");
                                JSONArray authorsArray = volumeObj.getJSONArray("authors");
                                String description = volumeObj.optString("description");
                                int pageCount = volumeObj.optInt("pageCount");
                                ArrayList<String> authorsArrayList = new ArrayList<>();
                                if (authorsArray.length() != 0) {
                                    for (int j = 0; j < authorsArray.length(); j++) {
                                        authorsArrayList.add(authorsArray.optString(0));
                                    }

                                    mTextViewResults.append("\n\n\n"+title + ", " + authorsArrayList + "\n\n"+"pages:"+pageCount+",\n"+description);
                                    bk_jsonParse(bk_price); //BookRuns API


                                }


                        }catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }


        });

        mQueue.add(request);

    }

    private void bk_jsonParse(String l) {


        String url = "https://booksrun.com/api/v3/price/buy/"+l+"?key=t2xlhotmhy246sby0zvu"; //Book Runs API


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {


                                //FOR BOOKRUNS API
                                JSONObject jsonObject = response.getJSONObject("result");
                                String status = jsonObject.getString("status");
                                JSONObject offers = jsonObject.getJSONObject("offers");
                                JSONObject bookrs = offers.getJSONObject("booksrun");
                                JSONObject new_price = bookrs.getJSONObject("new");
                                Integer price = new_price.getInt("price"); //price of new
                            mTextViewResults.append(status+" , "+ "\n"+"price of new: $"+price);
                                //FOR BOOKRUNS API


                            }


                        catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }


                });

        mQueue.add(request);
    }
}
