package pl.fabrykacodu.kaspero.zlotystok.Activity.Holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import pl.fabrykacodu.kaspero.zlotystok.Activity.model.Model;
import pl.fabrykacodu.kaspero.zlotystok.R;

/**
 * Created by kaspero on 13.03.2017.
 */

public class LocationHolder extends RecyclerView.ViewHolder {

    private ImageView locationImage;
    private TextView locationTitle;
    private TextView locationAddres;


    public LocationHolder(View itemView) {
        super(itemView);

    locationImage = (ImageView)itemView.findViewById(R.id.locationImage);
    locationTitle = (TextView) itemView.findViewById(R.id.location_title);
    locationAddres = (TextView)itemView.findViewById(R.id.location_desc);

    }

    public void updateUI(Model location){

        String url = location.getImgUrl();
        int res = locationImage.getResources().getIdentifier(url,null,locationImage.getContext().getPackageName());
        locationImage.setImageResource(res);
        locationTitle.setText(location.getTitleItem());
        String opis = location.getOpis();
        opis = opis.substring(0,50)+ " ... ";
        locationAddres.setText(opis);
    }
}
