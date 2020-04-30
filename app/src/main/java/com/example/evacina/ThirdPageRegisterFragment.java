package com.example.evacina;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdPageRegisterFragment extends Fragment {
    private CheckBox allergy_eggs, allergy_protein, allergy_yeast, allergy_jello;
    private Button button_register;
    public ThirdPageRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third_page_register, container, false);
        initViews(view);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Boolean HasEggAllergy = allergy_eggs.isChecked();
                    Boolean HasProteinAllergy = allergy_protein.isChecked();
                    Boolean HasYeastAllergy = allergy_yeast.isChecked();
                    Boolean HasJelloAllergy = allergy_jello.isChecked();

                    //TODO send data to the database and call new activity
                }
            }
        });
        return view;
    }

    private boolean validate() {
        boolean valid = true;
        //TODO write validation function to check if information on the registration form is correct
        return true;
    }

    private void initViews(View view){
        allergy_eggs = view.findViewById(R.id.checkbox_allergy_egg);
        allergy_protein = view.findViewById(R.id.checkbox_allergy_protein_cow_milk);
        allergy_yeast = view.findViewById(R.id.checkbox_allergy_yeast);
        allergy_jello = view.findViewById(R.id.checkbox_allergy_jello);
        button_register = view.findViewById(R.id.button_Register);
    }
}
