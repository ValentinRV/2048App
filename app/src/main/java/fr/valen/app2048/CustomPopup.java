package fr.valen.app2048;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomPopup extends Dialog{

    // fields
    private String title;
    private String subTitle;
    private ImageView menu;
    private TextView titleView, subTitleView;

    // constructor
    public CustomPopup(Activity activity){

        super(activity, R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popup_template);
        this.title = "Popup Title";
        this.subTitle = "Popup SubTitle";
        this.menu = findViewById(R.id.menuGameOver);
        this.titleView = findViewById(R.id.gameOver);
        this.subTitleView = findViewById(R.id.gameOverScore);
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSubTitle(String subTitle){
        this.subTitle = subTitle;
    }

    public ImageView getMenuButton(){
        return menu;
    }

    public void build(){
        show();
        titleView.setText(title);
        subTitleView.setText(subTitle);
    }

}

