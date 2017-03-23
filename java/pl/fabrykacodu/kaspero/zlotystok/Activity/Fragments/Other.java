package pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments;


import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import pl.fabrykacodu.kaspero.zlotystok.Activity.Adapter.LocationAdapter;
import pl.fabrykacodu.kaspero.zlotystok.Activity.model.Model;
import pl.fabrykacodu.kaspero.zlotystok.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Other extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;




    public Other() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        String title = null;
        try {
            title = getArguments().getString("edttext");
        }
        catch(Exception err) {
            title = null;
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        ArrayList<Model> locations = new ArrayList<>();


        //TODO POBIERANIE Z XML DANYCH

        if (title.equals("szlaki")){
            locations = getMountainsXML();
        }else if(title.equals("historia")){
            locations = getHistoryXML();
        }



        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new LocationAdapter(locations,getContext());
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    public ArrayList<Model> getMountainsXML(){

        ArrayList<Model> locations = new ArrayList<>();
        XmlResourceParser parser = getActivity().getResources().getXml(R.xml.szlaki);
        int eventType = -1;
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String itemValue = parser.getName();
                if (itemValue.equals("item")) {
                    String id = parser.getAttributeValue(null, "id");
                    String photoMin = parser.getAttributeValue(null, "photoMin");
                    String tytul = parser.getAttributeValue(null, "tytul");
                    String opis = parser.getAttributeValue(null, "opis");
                    String photoBig = parser.getAttributeValue(null, "photoBig");
                    String dojscie = parser.getAttributeValue(null, "dojscie");

                    Model model = new Model(id, tytul, photoMin, opis, photoBig, dojscie);
                    locations.add(model);
                }
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return locations;
    }

    public ArrayList<Model> getHistoryXML(){

        ArrayList<Model> locations = new ArrayList<>();
        XmlResourceParser parser = getActivity().getResources().getXml(R.xml.hisoria);
        int eventType = -1;
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String itemValue = parser.getName();
                if (itemValue.equals("item")) {
                    String id = parser.getAttributeValue(null, "id");
                    String photoMin = parser.getAttributeValue(null, "photoMin");
                    String tytul = parser.getAttributeValue(null, "tytul");
                    String opis = parser.getAttributeValue(null, "opis");
                    String photoBig = parser.getAttributeValue(null, "photoBig");

                    Model model = new Model(id, tytul, photoMin, opis, photoBig);
                    locations.add(model);
                }
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return locations;
    }

}
