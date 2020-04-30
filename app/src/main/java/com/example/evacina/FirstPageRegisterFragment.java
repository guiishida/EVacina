package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageRegisterFragment extends Fragment implements View.OnClickListener {

    public FirstPageRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_page_register, container, false);
        Button buttonSignIn = view.findViewById(R.id.button_SignIn);
        buttonSignIn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_SignIn) {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}
