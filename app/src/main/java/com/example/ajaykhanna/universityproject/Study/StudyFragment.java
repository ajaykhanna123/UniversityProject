package com.example.ajaykhanna.universityproject.Study;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ajaykhanna.universityproject.R;


public class StudyFragment extends Fragment implements View.OnClickListener{

    TextView CGPACalculator;
    Button btnCivil;
    Button btnMec;
    Button btnEce;
    Button btnCse;
    TextView txtIstYear;
    TextView txtCGPa;


    public StudyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_study, container, false);

        btnCivil=view.findViewById(R.id.txtCivilStudy);
        btnCse=view.findViewById(R.id.txtComputerStudy);
        btnEce=view.findViewById(R.id.txtElectronicsStudy);
        btnMec=view.findViewById(R.id.txtMechicalStudy);
        txtIstYear=view.findViewById(R.id.txtIstyearStudy);
        txtCGPa=view.findViewById(R.id.txtCGPA);


        btnMec.setOnClickListener(this);
        btnCse.setOnClickListener(this);
        btnEce.setOnClickListener(this);
        btnCivil.setOnClickListener(this);
        txtCGPa.setOnClickListener(this);
        txtIstYear.setOnClickListener(this);
        return view;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtCGPA:
            Intent cgpa=new Intent(getActivity(),CGPACalulator.class);
            startActivity(cgpa);
            break;
            case R.id.txtIstyearStudy:
                Intent istYearStudy=new Intent(getActivity(),IstYearStudy.class);
                startActivity(istYearStudy);
                break;
            case R.id.txtCivilStudy:
                Intent civil=new Intent(getActivity(),CivilStudyActivity.class);
                startActivity(civil);
                break;
            case R.id.txtMechicalStudy:
                Intent mec=new Intent(getActivity(),MecStudyActivity.class);
                startActivity(mec);
                break;
            case R.id.txtComputerStudy:
                Intent cse=new Intent(getActivity(),CseStudyActivity.class);
                startActivity(cse);
                break;
            case R.id.txtElectronicsStudy:
                Intent ece=new Intent(getActivity(),ElectronicsStudyActivity.class);
                startActivity(ece);
                break;



        }

    }
    private void computerScience()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.year)
                .setItems(R.array.years, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if(which==0)
                        {

                        }
                    }
                });

    }
}
