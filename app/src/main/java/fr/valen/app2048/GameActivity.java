package fr.valen.app2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import fr.valen.app2048.SimpleGestureFilter.SimpleGestureListener;

import java.util.List;

public class GameActivity extends AppCompatActivity implements SimpleGestureListener {

    private SimpleGestureFilter detector;
    private GameActivity activity;
    private ImageView menu;
    private TextView scoreView;
    private BoxItem[][] boxItemList = new BoxItem[4][4];
    private int score = 0;

    public void setImg(BoxItem boxItem){

        String boxName = "case_" + boxItem.getPosX() + "_" + boxItem.getPosY();
        int resId = getResources().getIdentifier(boxName, "id", getPackageName());
        ImageView box = (ImageView) findViewById(resId);
        String imgName = "b" + boxItem.getValue();
        int resDr = getResources().getIdentifier(imgName, "drawable", getPackageName());
        box.setBackgroundResource(resDr);

    }

    public void generateRandomBox(){
        if(!oneMoreSpace()){
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
            CustomPopup customPopup = new CustomPopup(activity);
            customPopup.setTitle("GAME OVER");
            customPopup.setSubTitle("Score : " + score);
            customPopup.getMenuButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            customPopup.build();
            return;
        }
        int x = 0;
        int y = 0;
        int val = 1;
        while(val != 0){
            x = (int) ((int)(Math.random()*((3-0)+1))+0);
            y = (int) ((int)(Math.random()*((3-0)+1))+0);
            val = boxItemList[x][y].getValue();
        }
        int rnd = (int) ((int)(Math.random()*((2-1)+1))+1)*2;
        boxItemList[x][y] = new BoxItem(x, y, rnd);
        setImg(boxItemList[x][y]);
        scoreView.setText("Score : " + score);
    }

    public boolean oneMoreSpace(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if(boxItemList[i][j].getValue() == 0){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.activity = this;
        this.scoreView = (TextView) findViewById(R.id.score);

        // Detect touched area
        detector = new SimpleGestureFilter(GameActivity.this, this);

        this.menu = (ImageView) findViewById(R.id.menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                boxItemList[i][j] = new BoxItem(i, j, 0);
                setImg(boxItemList[i][j]);
            }
        }
        generateRandomBox();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {

        //Detect the swipe gestures and display toast

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT:

                for(int i = 2; i >= 0; i--){
                    for(int j = 0; j < 4; j++){
                        if(boxItemList[i][j].getValue() != 0){
                            int x = i;
                            int y = j;
                            while(boxItemList[x + 1][y].getValue() == 0){
                                boxItemList[x + 1][y] = new BoxItem(x + 1, y, boxItemList[x][y].getValue());
                                boxItemList[x][y] = new BoxItem(x, y, 0);
                                x++;
                                if(x == 3){
                                    break;
                                }
                            }
                            if(x < 3) {
                                if (boxItemList[x + 1][y].getValue() == boxItemList[x][y].getValue()) {
                                    boxItemList[x + 1][y] = new BoxItem(x + 1, y, boxItemList[x][y].getValue() * 2);
                                    boxItemList[x][y] = new BoxItem(x, y, 0);
                                    score += boxItemList[x + 1][y].getValue();
                                }
                            }
                        }
                    }
                }
                generateRandomBox();

                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        setImg(boxItemList[i][j]);
                    }
                }

                break;

            case SimpleGestureFilter.SWIPE_LEFT:

                for(int i = 1; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        if(boxItemList[i][j].getValue() != 0){
                            int x = i;
                            int y = j;
                            while(boxItemList[x - 1][y].getValue() == 0){
                                boxItemList[x - 1][y] = new BoxItem(x - 1, y, boxItemList[x][y].getValue());
                                boxItemList[x][y] = new BoxItem(x, y, 0);
                                x--;
                                if(x == 0){
                                    break;
                                }
                            }
                            if(x > 0) {
                                if (boxItemList[x - 1][y].getValue() == boxItemList[x][y].getValue()) {
                                    boxItemList[x - 1][y] = new BoxItem(x - 1, y, boxItemList[x][y].getValue() * 2);
                                    boxItemList[x][y] = new BoxItem(x, y, 0);
                                    score += boxItemList[x - 1][y].getValue();
                                }
                            }
                        }
                    }
                }
                generateRandomBox();

                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        setImg(boxItemList[i][j]);
                    }
                }

                break;

            case SimpleGestureFilter.SWIPE_DOWN:

                for(int i = 0; i < 4; i++){
                    for(int j = 2; j >= 0; j--){
                        if(boxItemList[i][j].getValue() != 0){
                            int x = i;
                            int y = j;
                            while(boxItemList[x][y + 1].getValue() == 0){
                                boxItemList[x][y + 1] = new BoxItem(x, y + 1, boxItemList[x][y].getValue());
                                boxItemList[x][y] = new BoxItem(x, y, 0);
                                y++;
                                if(y == 3){
                                    break;
                                }
                            }
                            if(y < 3) {
                                if (boxItemList[x][y + 1].getValue() == boxItemList[x][y].getValue()) {
                                    boxItemList[x][y + 1] = new BoxItem(x, y + 1, boxItemList[x][y].getValue() * 2);
                                    boxItemList[x][y] = new BoxItem(x, y, 0);
                                    score += boxItemList[x][y + 1].getValue();
                                }
                            }
                        }
                    }
                }
                generateRandomBox();

                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        setImg(boxItemList[i][j]);
                    }
                }

                break;

            case SimpleGestureFilter.SWIPE_UP:

                for(int i = 0; i < 4; i++){
                    for(int j = 1; j < 4; j++){
                        if(boxItemList[i][j].getValue() != 0){
                            int x = i;
                            int y = j;
                            while(boxItemList[x][y - 1].getValue() == 0){
                                boxItemList[x][y - 1] = new BoxItem(x, y - 1, boxItemList[x][y].getValue());
                                boxItemList[x][y] = new BoxItem(x, y, 0);
                                y--;
                                if(y == 0){
                                    break;
                                }
                            }
                            if(y > 0) {
                                if (boxItemList[x][y - 1].getValue() == boxItemList[x][y].getValue()) {
                                    boxItemList[x][y - 1] = new BoxItem(x, y - 1, boxItemList[x][y].getValue() * 2);
                                    boxItemList[x][y] = new BoxItem(x, y, 0);
                                    score += boxItemList[x][y - 1].getValue();
                                }
                            }
                        }
                    }
                }
                generateRandomBox();

                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        setImg(boxItemList[i][j]);
                    }
                }

                break;

        }
    }


    //Toast shown when double tapped on screen
    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "You have Double Tapped.", Toast.LENGTH_SHORT).show();
    }
}