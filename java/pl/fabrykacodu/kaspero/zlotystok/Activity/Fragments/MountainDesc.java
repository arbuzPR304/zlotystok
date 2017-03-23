package pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.fabrykacodu.kaspero.zlotystok.Activity.model.Model;
import pl.fabrykacodu.kaspero.zlotystok.R;


public class MountainDesc extends Fragment {

    TextView opis;
    TextView adres;
    TextView nazwa;
    ImageView img2;

    Model currentObj;

    public Model getCurrentObj() {
        return currentObj;
    }

    public void setCurrentObj(Model currentObj) {
        this.currentObj = currentObj;
    }

    public MountainDesc() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_mountains_desc_option, container, false);
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        String title = getArguments().getString("TITLE");
        String img = getArguments().getString("IMG");
        String desc = getArguments().getString("DESC");
        String way = getArguments().getString("WAY");

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.dojazd);



        opis = (TextView) view.findViewById(R.id.opis);
        opis.setText(desc);
        adres = (TextView) view.findViewById(R.id.adres);
        adres.setText(way);
        nazwa = (TextView)view.findViewById(R.id.titleopis);
        nazwa.setText(title);

        img2 = (ImageView) view.findViewById(R.id.imageItem);

        if(way == null) {
            linearLayout.setVisibility(View.GONE);
        }
        int res = img2.getResources().getIdentifier(img,null,img2.getContext().getPackageName());
        img2.setImageResource(res);


        ImageView powrot = (ImageView) view.findViewById(R.id.back_btn);

        powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }

}
