package com.example.myapplication.ui.gallery;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                populateFields(root);

                textView.setText(s);
            }



            private void populateFields(View view) {
                String URL = "http://172.25.224.1:8080/apis/posts/find/183086";

                //TextView textView = view.findViewById(R.id.mainContent);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String s1 = null;
                        TextView textView1;
                        ImageView img;
                        ArrayList<Float> values = new ArrayList<Float>();

                        try {
                            JSONObject jsonObject = response;
                            Log.e("msg", response.toString());

                            textView1 = view.findViewById(R.id.maxPH);
                            textView1.setText(jsonObject.getString("ph_max"));
                            //values.add(parseFloat(jsonObject.getString("ph_max")));

                            textView1 = view.findViewById(R.id.minPH);
                            textView1.setText(jsonObject.getString("ph_min"));
                            //values.add(parseFloat(jsonObject.getString("ph_min")));

                            textView1 = view.findViewById(R.id.minTemp);
                            textView1.setText(jsonObject.getString("temp_min"));
                            //values.add(parseFloat(jsonObject.getString("temp_min")));

                            textView1 = view.findViewById(R.id.maxTemp);
                            textView1.setText(jsonObject.getString("temp_max"));
                            //values.add(parseFloat(jsonObject.getString("temp_max")));

                            textView1 = view.findViewById(R.id.soilMoistrue);
                            textView1.setText(jsonObject.getString("soil_texture"));
                            //values.add(parseFloat(jsonObject.getString("soil_texture")));

                            img = view.findViewById(R.id.imagePic);
                            Glide.with(view).load(jsonObject.getString("image")).into(img);

                            textView1 = view.findViewById(R.id.plantName);
                            textView1.setText(jsonObject.getString("name"));

                            textView1 = view.findViewById(R.id.light);
                            textView1.setText(jsonObject.getString("light"));
                            //values.add(parseFloat(jsonObject.getString("light")));

                            populateCurrent(root, values);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //TextView text = view.findViewById(R.id.test);
                        //text.setText(s1.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
                queue.add(request);
            }

            private void populateCurrent(View view, ArrayList<Float> vals) {
                String URL = "http://172.25.224.1:8080/apis/posts/dummyData";

                //TextView textView = view.findViewById(R.id.mainContent);
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String s1 = null;
                        String s2;
                        String current;
                        TextView textView1;

                        try {
                            JSONObject jsonObject = response;
                            Log.e("msg", response.toString());

                            // PH
                            textView1 = (TextView) view.findViewById(R.id.maxPH);
                            Log.e("TEST",textView1.getText().toString());

                            s1 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.minPH);
                            s2 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.d_ph);
                            current = jsonObject.getString("ph");
                            textView1.setText(current);
                            Log.e("s1", s1 );
                            Log.e("s2", s2);
                            Log.e("c", current);
                            if (!s1.equals("null") && !s2.equals("null")) {
                                if (parseFloat(current) > parseFloat(s1) || parseFloat(current) < parseFloat(s2)) {
                                    textView1.setTextColor(Color.RED);
                                    textView1 = view.findViewById(R.id.ph_lab);
                                    textView1.setTextColor(Color.RED);
                                }
                            }

                            // Temperature
                            textView1 = view.findViewById(R.id.maxTemp);
                            s1 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.minTemp);
                            s2 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.d_temp);
                            current = jsonObject.getString("temperature");
                            textView1.setText(current);

                            if (!s1.equals("null") && !s2.equals("null")) {
                                if (parseFloat(current) > parseFloat(s1) || parseFloat(current) < parseFloat(s2)) {
                                    textView1.setTextColor(Color.RED);
                                    textView1 = view.findViewById(R.id.tempLab);
                                    textView1.setTextColor(Color.RED);

                                }
                            }

                            // soil moisture
                            textView1 = view.findViewById(R.id.soilMoistrue);
                            s1 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.d_soil);
                            current = jsonObject.getString("soil_moisture");
                            textView1.setText(current);

                            if (!s1.equals("null")) {
                                if (parseFloat(current) > parseFloat(s1) || parseFloat(current) < parseFloat(s1)) {
                                    textView1.setTextColor(Color.RED);
                                    textView1 = view.findViewById(R.id.soilLab);
                                    textView1.setTextColor(Color.RED);

                                }
                            }

                            // light
                            textView1 = view.findViewById(R.id.light);
                            s1 = (String) textView1.getText();

                            textView1 = view.findViewById(R.id.d_light);
                            current = jsonObject.getString("light");
                            textView1.setText(current);

                            if (!s1.equals("null")) {
                                if (parseFloat(current) > parseFloat(s1) || parseFloat(current) < parseFloat(s1)) {
                                    textView1.setTextColor(Color.RED);
                                    textView1 = view.findViewById(R.id.lightLab);
                                    textView1.setTextColor(Color.RED);

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //TextView text = view.findViewById(R.id.test);
                        //text.setText(s1.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.toString());
                    }
                });
                queue.add(request);

            }





        });
        return root;
    }

}

/*


 */