package pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments;

import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.fabrykacodu.kaspero.zlotystok.Activity.MainActivity;
import pl.fabrykacodu.kaspero.zlotystok.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements GoogleMap.OnMarkerClickListener{

    MapView mMapView;
    GoogleMap googleMap=null;
    private String string = null;
    private MainActivity mainActivity;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainActivity = (MainActivity) getActivity();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        try {
            string = getArguments().getString("edttext");
        }
        catch(Exception err) {
            string = null;
        }

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final MainFragment mainFragment = this;

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.map));

                int iconId = R.drawable.restaurant;
                int zoom = 15;

                LatLng zlotystok = new LatLng(50.442960, 16.875563);
                List items = processData();
                Iterator iterator = items.iterator();
                while (iterator.hasNext()){
                    Object next = iterator.next();
                    Item atr = (Item)next;
                    switch (atr.getTyp()){
                        case "nocleg":
                            iconId = R.drawable.hotel;
                            zoom =14;
                            break;
                        case "bar":
                            iconId = R.drawable.restaurant;
                            zoom =13;
                            break;
                        case "zabytek":
                            zoom =15;
                            iconId = R.drawable.library;
                            break;
                        case "atrakcja":
                            iconId = R.drawable.goldpot;
                    }


                    if(atr.getId().equals("19")){iconId = R.drawable.mine;}
                    else if(atr.getId().equals("1")){iconId = R.drawable.wheel;}
                    else if(atr.getId().equals("3")){iconId = R.drawable.z;}
                    else if(atr.getId().equals("4")){iconId = R.drawable.rocks;}
                    else if(atr.getId().equals("10")){iconId = R.drawable.monu;}
                    else if(atr.getId().equals("5")){iconId = R.drawable.cityhall;}
                    else if(atr.getId().equals("6")){iconId = R.drawable.church1;}
                    else if(atr.getId().equals("8")){iconId = R.drawable.church3;}
                    else if(atr.getId().equals("9")){iconId = R.drawable.church2;}


                    BitmapDescriptor bitmapDescriptor = createPureTextIcon(atr.getNazwa());
                    Marker icon = googleMap.addMarker(new MarkerOptions().position(new LatLng(atr.getSzerokosc(), atr.getDlugosc())));
                    icon.setIcon(BitmapDescriptorFactory.fromResource(iconId));
                    icon.setSnippet(atr.getId());


                    Marker text = googleMap.addMarker(new MarkerOptions().position(new LatLng(atr.getSzerokosc(), atr.getDlugosc())));
                    text.setIcon(bitmapDescriptor);
                    text.setAnchor(0.5f,0.5f);
                    text.setSnippet(atr.getId());


                }

                CameraPosition cameraPosition = new CameraPosition.Builder().target(zlotystok).zoom(zoom).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) mainFragment);

            }
        });


        return view;
    }



    public BitmapDescriptor createPureTextIcon(String text) {

        Paint textPaint = new Paint(); // Adapt to your needs

        textPaint.setTextSize(25);

        float textWidth = textPaint.measureText(text);
        float textHeight = textPaint.getTextSize();
        int width = (int) (textWidth);
        int height = (int) (textHeight)+20;

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);

        canvas.translate(0, height);


        canvas.drawText(text, 0, -5, textPaint);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(image);
        return icon;
    }



    private List processData() {

        XmlResourceParser parser = getActivity().getResources().getXml(R.xml.atrakcje);
        int eventType = -1;
        List items = new ArrayList();
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.START_TAG){
                String itemValue = parser.getName();
                if (itemValue.equals(string)){
                    String typ = parser.getAttributeValue(null,"typ");
                    String nazwa = parser.getAttributeValue(null,"nazwa");
                    String szerokosc = parser.getAttributeValue(null,"szerokosc");
                    String dlugosc = parser.getAttributeValue(null,"dlugosc");
                    String id = parser.getAttributeValue(null,"id");

                    Item item = new Item(id,typ,nazwa,szerokosc,dlugosc);
                    items.add(item);

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
        return items;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        LatLng lanLatLng = marker.getPosition();
        mainActivity.startDescribeFragment(marker.getSnippet(),lanLatLng.latitude,lanLatLng.longitude);

        return true;
    }
}
 class Item{
     private String typ;

     public String getTyp() {
         return typ;
     }

     private String id;
     private String nazwa;
     private String szerokosc;
     private String dlugosc;

     public Double getDlugosc() {
         return Double.parseDouble(dlugosc);
     }

     public Double getSzerokosc() {
         return Double.parseDouble(szerokosc);
     }

     public String getId() {
         return id;
     }

     public String getNazwa() {
         return nazwa;
     }

     public Item(String id, String typ, String nazwa, String szerokosc, String dlugosc) {
         this.nazwa = nazwa;
         this.typ = typ;
         this.szerokosc = szerokosc;
         this.dlugosc = dlugosc;
         this.id = id;
     }
 }