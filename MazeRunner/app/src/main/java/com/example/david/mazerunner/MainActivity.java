package com.example.david.mazerunner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Pupazzo p1;
    private ImageView omino;

    private boolean up=false;
    private boolean down=false;
    private boolean left=false;
    private boolean right=false;

    private RelativeLayout rl;

    private float altezzaFrame;
    private float larghezzaFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        omino = findViewById(R.id.noob);
        rl = findViewById(R.id.relative);//Layout del main activity
        p1 = new Pupazzo(this);
        p1.start();



        new Thread(()->{

            while(true){
                runOnUiThread(()-> muovi());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }

    public void muovi() {

        FrameLayout frame = findViewById(R.id.fragment1);
        altezzaFrame = frame.getHeight();
        larghezzaFrame = frame.getWidth();
        ImageView porta = findViewById(R.id.porta0);

        /**Passo tutte le info neccessarie al omino**/
        p1.setAltezzaPorta(porta.getHeight());
        p1.setAltezzaRl(rl.getHeight());
       // p1.setAltezzaFrame(rl.getWidth());
        p1.setAltezzaFrame(altezzaFrame);
        p1.setLarghezzaFrame(larghezzaFrame);
        p1.setAltezzaOmino(omino.getWidth());

        //passo la direzione all'omino
        p1.setUp(up);
        p1.setDown(down);
        p1.setRight(right);
        p1.setLeft(left);


        //infine setto le coordinate dell imageView
        omino.setX((float) p1.getOminoX());
        omino.setY((float) p1.getOminoY());

    }


    public void naviga(View view) {
        int id = view.getId();
        up = id == R.id.upp;
        down = id == R.id.down;
        left = id == R.id.left;
        right = id == R.id.right;

    }


     float x1, x2, y1, y2;
     final static float MIN_DISTANCE = 150.0f;

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float deltaY = y2 - y1;
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        annullaTutto();
                        right = true;

                    }
                    // Right to left swipe action
                    else {
                        annullaTutto();
                        left = true;
                    }

                } else if (Math.abs(deltaY) > MIN_DISTANCE) {
                    if (y2 > y1) {//giu
                        annullaTutto();
                        down = true;
                    } else {
                        annullaTutto();
                        up = true;
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    public void annullaTutto() {
        right = false;
        left = false;
        up = false;
        down = false;
    }

}





























    /*

    ImageView omino;
    double ominoX;
    double ominoY;

    boolean up=false;
    boolean down=false;
    boolean left=false;
    boolean right=false;


    RelativeLayout rl;

    private Stanze stanza[][]=new Stanze[3][3];
    private int posizioneOminoX;
    private int posizioneOminoY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        omino=findViewById(R.id.noob);
        System.out.println(omino.getX());

        rl=findViewById(R.id.relative);

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



        new Thread(()->{

                while(true){
                    runOnUiThread(()-> muovi());
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

    float altezzaFrame;
    float larghezzaFrame;
   public void muovi(){

        FrameLayout frame=findViewById(R.id.fragment1);
         altezzaFrame=frame.getHeight();
         larghezzaFrame=frame.getWidth();
        ImageView porta=findViewById(R.id.porta0);



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


            double Cx=ominoX+omino.getWidth()/2;
            double Cy=ominoY+omino.getHeight()/2;
            if (Cx > (larghezzaFrame-porta.getWidth()/1.85)
                    && Cy > altezzaFrame/2+(rl.getHeight()-altezzaFrame)-(porta.getHeight()/2)
                    && Cy < altezzaFrame/2+(rl.getHeight()-altezzaFrame)+porta.getHeight()/2
                    && stanza[posizioneOminoY][posizioneOminoX].getDestra() == 1) { //se va a destra
                posizioneOminoX += 1;

                aggiorna(R.animator.slide_out_right);
            }
            if (Cy <(rl.getHeight()-altezzaFrame)+porta.getHeight()/2
                    &&Cx >larghezzaFrame/2-porta.getWidth()/2
                    &&Cx <larghezzaFrame/2+porta.getWidth()/2
                    && stanza[posizioneOminoY][posizioneOminoX].getSu() == 1) { //se va su
                posizioneOminoY -= 1;
                aggiorna(R.animator.slide_top);
            }
            if (Cy >(rl.getHeight()-altezzaFrame)+ altezzaFrame-(porta.getHeight()/1.35)
                    &&Cx >larghezzaFrame/2-porta.getWidth()/2
                    &&Cx <larghezzaFrame/2+porta.getWidth()/2
                    && stanza[posizioneOminoY][posizioneOminoX].getGiu() == 1) { //se va a giu
                posizioneOminoY += 1;
                aggiorna(R.animator.slide_down);
            }
            if (Cx<porta.getWidth()/1.95
                    && Cy > altezzaFrame/2+(rl.getHeight()-altezzaFrame)-(porta.getHeight()/2)
                    && Cy < altezzaFrame/2+(rl.getHeight()-altezzaFrame)+porta.getHeight()/2
                    && stanza[posizioneOminoY][posizioneOminoX].getSinistra() == 1) { //se va a sinistra
                posizioneOminoX -= 1;

                aggiorna(R.animator.slide_in_left);
            }

       if(ominoX>=larghezzaFrame-omino.getWidth()) ominoX=larghezzaFrame-omino.getWidth();
       if(ominoX<=0) ominoX=0;
       if(ominoY>=altezzaFrame-omino.getWidth()/4) ominoY=altezzaFrame-omino.getWidth()/4;
       if(ominoY<=rl.getHeight()-altezzaFrame) ominoY=rl.getHeight()-altezzaFrame;

           omino.setX((float) ominoX);
           omino.setY((float) ominoY);

    }

    private void aggiorna(int animazione) {
        try {
            creaFragment(animazione);
        }catch (Exception ex){
            Intent intent=new Intent(MainActivity.this, Vittoria.class);
            startActivity(intent);
        }

        ominoX=larghezzaFrame/2-omino.getWidth()/2;
        ominoY=(rl.getHeight()-altezzaFrame)+altezzaFrame/2-omino.getWidth()/2;
        annullaTutto();
    }


    public void creaFragment(int animazione) {

        int entrate[] = new int[4];
        entrate[0] = stanza[posizioneOminoY][posizioneOminoX].getDestra();
        entrate[1] = stanza[posizioneOminoY][posizioneOminoX].getSu();
        entrate[2] = stanza[posizioneOminoY][posizioneOminoX].getSinistra();
        entrate[3] = stanza[posizioneOminoY][posizioneOminoX].getGiu();

        System.out.println("posizione" + posizioneOminoY + "" + posizioneOminoX);

        Fragment1 f1 = new Fragment1();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(animazione, R.animator.fade);
        ft.replace(R.id.fragment, f1);
        ft.commit();





        for (int i = 0; i < entrate.length; i++) {

            System.out.println("entrate di i=" + entrate[i]);
            if (entrate[i] == 0) {
                final int i2=i;
                new Thread(()->{

                    runOnUiThread(()-> f1.setVis(i2));

                }).start();

            }
        }

    }


    public void naviga(View view){
        int id=view.getId();
        up=id==R.id.upp;
        down=id==R.id.down;
        left=id==R.id.left;
        right=id==R.id.right;

    }


    float x1,x2,y1,y2;
    final static float MIN_DISTANCE = 150.0f;

    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1= event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2=event.getY();
                float deltaY= y2-y1;
                float deltaX = x2 - x1;

                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    // Left to Right swipe action
                    if (x2 > x1) {
                        annullaTutto();
                        right=true;

                    }
                    // Right to left swipe action
                    else {
                        annullaTutto();
                        left=true;
                    }

                }else if(Math.abs(deltaY)> MIN_DISTANCE){
                     if(y2>y1){//giu
                        annullaTutto();
                        down=true;
                    }else {
                         annullaTutto();
                         up=true;
                     }
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    private void annullaTutto() {
        right=false;
        left=false;
        up=false;
        down=false;
    }

    */


