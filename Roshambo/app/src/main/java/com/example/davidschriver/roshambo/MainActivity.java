package com.example.davidschriver.roshambo;
/*
/ David Schriver 2019-03-01
 */
import android.animation.*;
import android.animation.ObjectAnimator;
import android.animation.ObjectAnimator.*;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Roshambo rps = new Roshambo();
    private ImageView player_img;
    private ImageView game_img;
    private TextView result_lbl;
    private Integer[] imgs = {R.drawable.rock,R.drawable.paper,R.drawable.scissors,R.drawable.none};
    private ObjectAnimator playerTransition;
    private ObjectAnimator gameTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the views
        player_img = this.findViewById(R.id.player_img);
        game_img = this.findViewById(R.id.game_img);
        result_lbl = this.findViewById(R.id.result_lbl);

        //Set up animations
        playerTransition =  ObjectAnimator.ofFloat(player_img,
                "alpha", 0f, 1f)
                .setDuration(250);
        gameTransition =  ObjectAnimator.ofFloat(game_img,
                "alpha", 0f, 1f)
                .setDuration(250);

    }

    /**
     * Play a round of Roshambo
     * @param chosen
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void playRound(int chosen)
    {
        //Make the move
        rps.playerMakesMove(chosen);
        //Output the results
        switchImage(chosen,player_img);
        switchImage(rps.getGameMove(),game_img);
        result_lbl.setText(getString(rps.winLoseOrDraw()));

        //Animate it
        AnimatorSet set = new AnimatorSet();
        set.playTogether(gameTransition,playerTransition);
       // set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();

    }

    /**
     * Change the image of an ImageView to an image in the imgs array.
     * @param to
     * @param changeme
     */
    private void switchImage(int to, ImageView changeme)
    {
        changeme.setImageResource(imgs[to]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pressScissors(View view) {
        playRound(2);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pressRock(View view) {
        playRound(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pressPaper(View view) {
        playRound(1);
    }
}
