package pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.fabrykacodu.kaspero.zlotystok.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class contact extends Fragment {


    public contact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

}
