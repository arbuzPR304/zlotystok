package pl.fabrykacodu.kaspero.zlotystok.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments.DescribeFragment;
import pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments.MainFragment;
import pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments.MountainDesc;
import pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments.Other;
import pl.fabrykacodu.kaspero.zlotystok.Activity.Fragments.contact;
import pl.fabrykacodu.kaspero.zlotystok.Activity.model.Model;
import pl.fabrykacodu.kaspero.zlotystok.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager =getSupportFragmentManager();
    private Fragment fragment = manager.findFragmentById(R.id.fragment_container);
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         /** niebieski pasek u gory ekranu */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** activity_main.xml */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);

        /** hamburger */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        /** pasek wlasciwosci */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(!isNetworkConnected()){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Aplikacja będzie lepiej działać z Internetem !\nMapy będą dostępne.")
                    .setTitle(R.string.app_name);
            builder.setPositiveButton("ROZUMIEM",null);

// 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }



        Bundle bundle = new Bundle();
        bundle.putString("edttext", "atrakcja");

        fragment = new MainFragment();
        fragment.setArguments(bundle);
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String title = null;


        switch (id){
            case R.id.atrakcje:
                title = "atrakcja";
                break;
            case R.id.zabytki:
                title = "zabytek";
                break;

            case R.id.szlaki:
                title = "szlaki";
                break;

            case R.id.noclegi:
                title = "nocleg";
                break;

            case R.id.bar:
                title = "bar";
                break;

            case R.id.historia:
                title = "historia";
                break;

            case R.id.kontakt:
                title = "kontakt";
                break;

            case R.id.exit:
                title = null;
                finish();
                System.exit(0);
                break;

            default:
                title = null;
        }

        if(title.equals("kontakt")){
            fragment = new contact();
            this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

        }
        else if(title.equals("szlaki") || title.equals("historia")){
            stratOtherFragment(title);
        }
        else if (title!=null){

            Bundle bundle = new Bundle();
            bundle.putString("edttext", title);

            fragment = new MainFragment();
            fragment.setArguments(bundle);

            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void startDescribeFragment(String id, Double szer, Double dlugo){
        Bundle bundle = new Bundle();
        bundle.putString("edttext", id);
        bundle.putDouble("szer",szer);
        bundle.putDouble("dlugo",dlugo);

        fragment = new DescribeFragment();
        fragment.setArguments(bundle);

        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }



    public void stratOtherFragment(String title){

        Bundle bundle = new Bundle();
        bundle.putString("edttext", title);

        fragment = new Other();
        fragment.setArguments(bundle);

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();


    }

    public void switchContent(Model model) {

        fragment = new MountainDesc();
        Bundle bundle = new Bundle();

        bundle.putString("TITLE", model.getTitleItem());
        bundle.putString("IMG", model.getImgUrl2());
        bundle.putString("DESC", model.getOpis());
        bundle.putString("WAY", model.getDosjcie());

        fragment.setArguments(bundle);

        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);


        transaction.commit();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
