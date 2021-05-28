package fr.valen.app2048;

public class BoxItem {

    private int posX;
    private int posY;
    private int value;

    public BoxItem(int posX, int posY, int value){
        this.posX = posX;
        this.posY = posY;
        this.value = value;
    }

    public int getPosX(){ return posX; }

    public int getPosY() { return posY; }

    public int getValue(){ return value; }



}
