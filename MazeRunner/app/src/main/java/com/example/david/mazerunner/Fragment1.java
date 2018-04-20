package com.example.david.mazerunner;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by David on 25/03/2018.
 */

public class Fragment1 extends Fragment {

    private ImageView P0;
    private ImageView P1;
    private ImageView P2;
    private ImageView P3;
    View view;

    ImageView porte[]=new ImageView[4];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        view=inflater.inflate(R.layout.fragment1, container, false);

        return view;
    }

    public void setVis(int i){

        P0 = view.findViewById(R.id.porta0);
        P1 = view.findViewById(R.id.porta1);
        P2 = view.findViewById(R.id.porta2);
        P3 = view.findViewById(R.id.porta3);
        porte[0] = P0;
        porte[1] = P1;
        porte[2] = P2;
        porte[3] = P3;

        porte[i].setVisibility(View.INVISIBLE);


    }
    public void setVisYes(int i){

        P0 = view.findViewById(R.id.porta0);
        P1 = view.findViewById(R.id.porta1);
        P2 = view.findViewById(R.id.porta2);
        P3 = view.findViewById(R.id.porta3);
        porte[0] = P0;
        porte[1] = P1;
        porte[2] = P2;
        porte[3] = P3;

        porte[i].setVisibility(View.VISIBLE);


    }

}
