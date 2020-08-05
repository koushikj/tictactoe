package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0=circle, 1=cross, 2=unplayed
    int activePlayer=0;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    int[][] winningStates={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameOver=false;
    int played=0;
    MediaPlayer mediaPlayer;
    boolean volumeOn=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this,R.raw.tic);
        muteAudio(null);
    }

    public void muteAudio(View view) {
        ImageButton volumeButton = (ImageButton) findViewById(R.id.volumeImageButton);
        if(volumeOn){
            volumeOn=false;
            mediaPlayer.setVolume(0,0);
            volumeButton.setImageResource(R.drawable.mute);

        }else {
            volumeOn=true;
            mediaPlayer.setVolume(1,1);
            volumeButton.setImageResource(R.drawable.unmute);
        }

    }
    public void dropIn(View view) {
        mediaPlayer.start();
        played++;
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (!gameOver && gameState[tappedCounter]==2) {
            gameState[tappedCounter]=activePlayer;
            if(activePlayer==0){
                counter.setTranslationY(-1000f);
                counter.setImageResource(R.drawable.circle);
                counter.animate().translationYBy(1000f).setDuration(300).rotation(180f);
                activePlayer=1;
            }else{
                counter.setTranslationY(1000f);
                counter.setImageResource(R.drawable.corss);
                counter.animate().translationYBy(-1000f).setDuration(300).rotation(180f);
                activePlayer=0;
            }
        }
        checkGameState();
    }

    private void checkGameState() {
        for (int[] winningState : winningStates){
            if(gameState[winningState[0]]==gameState[winningState[1]] && gameState[winningState[1]]==gameState[winningState[2]] && gameState[winningState[0]]!=2){
                String winner = "Cross";
                if(gameState[winningState[0]]==0){
                    winner="Circle";
                }
                gameOver=true;
                TextView winnerMessage = findViewById(R.id.winnerMessage);
                winnerMessage.setText(String.format("%s %s", winner, getString(R.string.winningMessage)));
                LinearLayout linearLayout =findViewById(R.id.gameOverLayout);
                linearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(this,String.format("%s %s", winner, getString(R.string.winningMessage)),Toast.LENGTH_LONG).show();
            }
        }
        if(!gameOver && played==9){
            TextView winnerMessage = findViewById(R.id.winnerMessage);
            winnerMessage.setText(R.string.gameDraw);
            LinearLayout linearLayout =findViewById(R.id.gameOverLayout);
            linearLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this,R.string.gameDraw,Toast.LENGTH_LONG).show();
        }
    }

    public void playAgain(View view) {
        Toast.makeText(this,"Play Again",Toast.LENGTH_LONG).show();
        gameOver=false;
        gameState= new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
        setContentView(R.layout.activity_main);
        activePlayer=0;
        played=0;
    }
}