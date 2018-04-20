package com.example.david.mazerunner;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by David on 05/04/2018.
 */


    public class Pupazzo {

        private MainActivity mainActivity;
        private int posizioneOminoX;
        private int posizioneOminoY;
        private Stanze stanza[][]=new Stanze[3][3];

    public Pupazzo(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
    }

    public void start(){


        posizioneOminoX=0;
        posizioneOminoY=2;

        //0 1 2 x
        //1
        //2
        //y
        stanza[0][0]=new Stanze(1,0,0,1);
        stanza[0][1]=new Stanze(1,0,1,1);
        stanza[0][2]=new Stanze(0,1,1,1);
        stanza[1][0]=new Stanze(1,1,0,1);
        stanza[1][1]=new Stanze(1,1,1,1);
        stanza[1][2]=new Stanze(0,0,1,1);
        stanza[2][0]=new Stanze(1,1,0,0);//start
        stanza[2][1]=new Stanze(1,1,1,0);
        stanza[2][2]=new Stanze(0,1,1,0);

        new Thread(() -> {

            while (true) {
                muovi();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

        //omino.setX(409.0f);
        //omino.setY(792.5f);
        creaFragment(R.animator.slide_out_right);

    }

    private double ominoX;
    private double ominoY;
    private void muovi() {

        if (up) {
            ominoY -= 5;
        }
        if (down) {
            ominoY += 5;
        }
        if (right) {
            ominoX += 5;
        }
        if (left) {
            ominoX -= 5;
        }


        double Cx = ominoX + altezzaOmino / 2;
        double Cy = ominoY + altezzaOmino / 2;
        if (Cx > (larghezzaFrame - altezzaPorta / 1.85)
                && Cy > altezzaFrame / 2 + (altezzaRl - altezzaFrame) - (altezzaPorta / 2)
                && Cy < altezzaFrame / 2 + (altezzaRl - altezzaFrame) + altezzaPorta / 2
                && stanza[posizioneOminoY][posizioneOminoX].getDestra() == 1) { //se va a destra
            posizioneOminoX += 1;

            aggiorna(R.animator.slide_out_right);
        }
        if (Cy < (altezzaRl - altezzaFrame) + altezzaPorta / 2
                && Cx > larghezzaFrame / 2 - altezzaPorta / 2
                && Cx < larghezzaFrame / 2 + altezzaPorta / 2
                && stanza[posizioneOminoY][posizioneOminoX].getSu() == 1) { //se va su
            posizioneOminoY -= 1;
            aggiorna(R.animator.slide_top);
        }
        if (Cy > (altezzaRl - altezzaFrame) + altezzaFrame - (altezzaPorta / 1.35)
                && Cx > larghezzaFrame / 2 - altezzaPorta / 2
                && Cx < larghezzaFrame / 2 + altezzaPorta / 2
                && stanza[posizioneOminoY][posizioneOminoX].getGiu() == 1) { //se va a giu
            posizioneOminoY += 1;
            aggiorna(R.animator.slide_down);
        }
        if (Cx < altezzaPorta / 1.95
                && Cy > altezzaFrame / 2 + (altezzaRl - altezzaFrame) - (altezzaPorta / 2)
                && Cy < altezzaFrame / 2 + (altezzaRl - altezzaFrame) + altezzaPorta / 2
                && stanza[posizioneOminoY][posizioneOminoX].getSinistra() == 1) { //se va a sinistra
            posizioneOminoX -= 1;

            aggiorna(R.animator.slide_in_left);
        }

        if (ominoX >= larghezzaFrame - altezzaOmino) ominoX = larghezzaFrame - altezzaOmino;
        if (ominoX <= 0) ominoX = 0;
        if (ominoY >= altezzaFrame - altezzaOmino / 4)
            ominoY = altezzaFrame - altezzaOmino / 4;
        if (ominoY <= altezzaRl - altezzaFrame) ominoY = altezzaRl - altezzaFrame;


        getOminoX();
        getOminoY();


    }

    private void creaFragment(int animazione) {

        int entrate[] = new int[4]; //entrate per ogni stanza
        entrate[0] = stanza[posizioneOminoY][posizioneOminoX].getDestra();//porta destra
        entrate[1] = stanza[posizioneOminoY][posizioneOminoX].getSu();//su
        entrate[2] = stanza[posizioneOminoY][posizioneOminoX].getSinistra();//sinistra
        entrate[3] = stanza[posizioneOminoY][posizioneOminoX].getGiu();//giu

        System.out.println("posizione" + posizioneOminoY + "" + posizioneOminoX);

        Fragment1 f1 = new Fragment1();
        FragmentTransaction ft = mainActivity.getFragmentManager().beginTransaction();
        ft.setCustomAnimations(animazione, R.animator.fade);
        ft.replace(R.id.fragment, f1);
        ft.commit();


        for (int i = 0; i < entrate.length; i++) {

            System.out.println("entrate di i=" + entrate[i]);
            if (entrate[i] == 0) {
                final int i2 = i;
                new Thread(() -> {

                    mainActivity.runOnUiThread(() -> f1.setVis(i2));

                }).start();

            }
        }

    }

    private void aggiorna(int animazione) {
        ominoX = larghezzaFrame / 2 - altezzaOmino / 2;
        ominoY = (altezzaRl - altezzaFrame) + altezzaFrame / 2 - altezzaOmino / 2;
        mainActivity.annullaTutto();

        try {
            creaFragment(animazione);
        } catch (Exception ex) {
            Intent intent = new Intent(mainActivity, Vittoria.class);
            mainActivity.startActivity(intent);
        }



    }



    private float altezzaFrame;
    private float larghezzaFrame;

    public void setAltezzaFrame(float altezzaFrame) {
        this.altezzaFrame = altezzaFrame;
    }

    public void setLarghezzaFrame(float larghezzaFrame) {
        this.larghezzaFrame = larghezzaFrame;
    }

    private float altezzaPorta;
    public void setAltezzaPorta(float altezzaPorta) {
        this.altezzaPorta = altezzaPorta;
    }

    private float altezzaRl;
    public void setAltezzaRl(float altezzaRl) {
        this.altezzaRl = altezzaRl;
    }

    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public void setUp(boolean up) {

        this.up=up;
    }
    public void setLeft(boolean left) {

        this.left=left;
    }
    public void setRight(boolean right) {

        this.right=right;
    }
    public void setDown(boolean down) {

        this.down=down;
    }

    private float altezzaOmino;
    public void setAltezzaOmino(int altezzaOmino) {
        this.altezzaOmino=altezzaOmino;
    }

    public double getOminoX() {
        return ominoX;
    }
    public double getOminoY() {
        return ominoY;
    }
}


