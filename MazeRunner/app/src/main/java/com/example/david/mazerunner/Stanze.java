package com.example.david.mazerunner;

/**
 * Created by David on 26/03/2018.
 */

class Stanze {

    int destra, su, sinistra, giu;

    public Stanze(int destra, int su, int sinistra, int giu){
        this.destra=destra;
        this.su=su;
        this.sinistra=sinistra;
        this.giu=giu;
    }

    public int getDestra() {
        return destra;
    }

    public int getSu() {
        return su;
    }

    public int getSinistra() {
        return sinistra;
    }

    public int getGiu() {
        return giu;
    }
}
