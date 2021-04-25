package com.example.myapplication.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/



        // Floating Button Stuff -> Adds a plant to t
        FloatingActionButton f = root.findViewById(R.id.floatingActionButton);
        f.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                View popup = LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);
                popup.setFocusable(true);
                final PopupWindow popupWindow = new PopupWindow(popup, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);
                popupWindow.showAsDropDown(popup, 0,0);
                Button closeBtn = popup.findViewById(R.id.button2);
                closeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                     popupWindow.dismiss();
                    }
                });
                Button sbmtBtn = popup.findViewById(R.id.ok_button);
                sbmtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {
                        EditText val = (EditText) popup.findViewById(R.id.inputText);
                        String strVal = val.getText().toString();
                        // Create Navigation Item

                        popupWindow.dismiss();
                        Snackbar.make(view, "Plant Added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });
            }
        });

        return root;
    }

}