package pl.fabrykacodu.kaspero.zlotystok.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.fabrykacodu.kaspero.zlotystok.Activity.Holder.LocationHolder;
import pl.fabrykacodu.kaspero.zlotystok.Activity.MainActivity;
import pl.fabrykacodu.kaspero.zlotystok.Activity.model.Model;
import pl.fabrykacodu.kaspero.zlotystok.R;

/**
 * Created by kaspero on 13.03.2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationHolder> {


    ArrayList<Model> locations;
    Context context;


    public LocationAdapter(ArrayList<Model> locations, Context context){
        this.locations = locations;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(LocationHolder holder, int position) {

        final Model location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity mainActivity = (MainActivity)context;

                mainActivity.switchContent(location);

            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public LocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new LocationHolder(card);
    }
}
