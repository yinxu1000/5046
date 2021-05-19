package com.example.fit5046;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrackCalorieFragment extends Fragment {
    View vCalorie;
    SharedPreferences sharedPreferences;
     TextView goal;
     TextView totalstep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vCalorie = inflater.inflate(R.layout.fragment_track, container, false);
        goal =(TextView) vCalorie.findViewById(R.id.tv_goal);
        totalstep = (TextView) vCalorie.findViewById(R.id.tv_totalstep);


        sharedPreferences = getActivity().getSharedPreferences( "signUpUser", Context.MODE_PRIVATE);
        final String userInfo = sharedPreferences.getString("userName","001");
        String steps = sharedPreferences.getString("steps","0");
        String goals = sharedPreferences.getString("goal","0");

        totalstep.setText("Total steps taken: "+steps);
        goal.setText("Daily goal: "+goals);


        return vCalorie;
    }

}
