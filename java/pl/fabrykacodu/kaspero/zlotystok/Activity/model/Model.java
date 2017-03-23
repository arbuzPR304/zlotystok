package pl.fabrykacodu.kaspero.zlotystok.Activity.model;

import java.io.Serializable;

/**
 * Created by kaspero on 12.03.2017.
 */

public class Model implements Serializable {

    final String drawable = "drawable/";

    private String id;
    private String titleItem;
    private String imgUrl;
    private String opis;
    private String imgUrl2;
    private String dosjcie;


    public Model(String id, String tytul, String photoMin, String opis, String imgUrl2) {

        this.id = id;
        this.titleItem = tytul;
        this.imgUrl = photoMin;
        this.opis = opis;
        this.imgUrl2 =imgUrl2;

    }

    public Model(String id, String tytul, String photoMin, String opis, String imgUrl2, String dosjcie) {
        this.id = id;
        this.titleItem = tytul;
        this.imgUrl = photoMin;
        this.opis = opis;
        this.imgUrl2 =imgUrl2;
        this.dosjcie = dosjcie;

    }

    public String getDosjcie() {
        return dosjcie;
    }

    public String getImgUrl2() {
        return drawable+imgUrl2;
    }

    public String getId() {
        return id;
    }

    public String getTitleItem() {
        return titleItem;
    }

    public String getImgUrl() {
        return drawable+imgUrl;
    }

    public String getOpis() {
        return opis;
    }
}
