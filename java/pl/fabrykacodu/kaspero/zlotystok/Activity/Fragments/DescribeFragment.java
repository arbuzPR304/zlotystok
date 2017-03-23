package pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments;


import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import pl.fabrykacodu.kaspero.zlotystok.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class DescribeFragment extends Fragment {

    TextView opis;
    TextView adres;
    Button nawiguj;
    Button witryna;
    ImageView imageItem;

    public DescribeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_describe, container, false);
        // Inflate the layout for this fragment

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        opis = (TextView) view.findViewById(R.id.opis);
        adres = (TextView) view.findViewById(R.id.adres);

        nawiguj = (Button) view.findViewById(R.id.nawiguj);
        witryna = (Button) view.findViewById(R.id.witryna);

        opis.setText("");
        adres.setText("");

        String id = null;
        Double szer;
        Double dlugo;

        imageItem = (ImageView) view.findViewById(R.id.imageItem);




        ItemDesc itemDesc = new ItemDesc();
        try {
            id = getArguments().getString("edttext");
            szer = getArguments().getDouble("szer");
            dlugo = getArguments().getDouble("dlugo");
            itemDesc = processData(id,szer,dlugo);


        }
        catch(Exception err) {
        }

        opis.setText(itemDesc.getOpis());
        adres.setText(itemDesc.getAdres());
        int resourcePhoto = getResources().getIdentifier("item"+itemDesc.getId(),"drawable",getActivity().getPackageName());

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutAdres);

        if(itemDesc.getTyp().equals("zabytek")){
            linearLayout.setVisibility(View.GONE);
            witryna.setVisibility(View.GONE);
        }

        imageItem.setImageResource(resourcePhoto);
        final ItemDesc finalItemDesc = itemDesc;
        nawiguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wymiaryStr = finalItemDesc.getSzer().toString()+ ","+finalItemDesc.getDlugo().toString();

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+wymiaryStr);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);

            }
        });

        witryna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finalItemDesc.getWitryna().equals("null")){

                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalItemDesc.getWitryna()));
                    startActivity(browserIntent);
                }


            }
        });

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



    private ItemDesc processData(String labelID,double szer, double dlugo) {

        labelID = "item"+labelID;
        XmlResourceParser parser = getActivity().getResources().getXml(R.xml.atrakcjeopis);
        ItemDesc itemDesc = new ItemDesc();
        int eventType = -1;
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String itemValue = parser.getName();
                if (itemValue.equals(labelID)){
                    String opis = parser.getAttributeValue(null,"opis");
                    String adres = parser.getAttributeValue(null,"adres");
                    String id = parser.getAttributeValue(null,"id");
                    String witryna = parser.getAttributeValue(null,"witryna");
                    String typ = parser.getAttributeValue(null,"typ");

                    itemDesc = new ItemDesc(id,szer,dlugo,opis,adres,witryna,typ);
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
        return itemDesc;
    }

    class ItemDesc{

        private String id;
        private Double szer;
        private Double dlugo;
        private String opis;
        private String witryna;
        private String typ;

        public String getTyp() {
            return typ;
        }

        public ItemDesc(String id, Double szer, Double dlugo, String opis, String adres, String witryna, String typ) {
            this.id = id;
            this.szer = szer;
            this.dlugo = dlugo;
            this.opis = opis;
            this.adres = adres;
            this.witryna = witryna;
            this.typ = typ;
        }

        public String getWitryna() {
            return witryna;
        }

        public ItemDesc(){
            this.id = null;
            this.szer = 0.0;
            this.dlugo = 0.0;
            this.opis = null;
            this.adres = null;
        }

        public String getId() {
            return id;
        }

        public Double getSzer() {
            return szer;
        }

        public Double getDlugo() {
            return dlugo;
        }

        public String getOpis() {
            return opis;
        }

        public String getAdres() {
            return adres;
        }

        private String adres;
    }

}
