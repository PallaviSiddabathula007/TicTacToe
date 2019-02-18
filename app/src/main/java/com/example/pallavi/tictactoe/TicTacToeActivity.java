package com.example.pallavi.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicTacToeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mPlayer1;
    private TextView mPlayer2;
    private Button mResetButton;

    private Button[][] mTicTacToeBoard;

    private boolean mPlayer1Turn;

    private int mRoundCount;
    private int mPlayer1Score;
    private int mPlayer2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_layout);

        loadView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", mRoundCount);
        outState.putInt("player1Score", mPlayer1Score);
        outState.putInt("player2Score", mPlayer2Score);
        outState.putBoolean("player1Turn", mPlayer1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mRoundCount = savedInstanceState.getInt("roundCount");
        mPlayer1Score = savedInstanceState.getInt("player1Score");
        mPlayer2Score = savedInstanceState.getInt("player2Score");
        mPlayer1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    private void loadView() {
        mPlayer1 = findViewById(R.id.player1);
        mPlayer2 = findViewById(R.id.player2);

        mResetButton = findViewById(R.id.resetButton);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        loadTicTacToeBoardButtons();
    }

    private void loadTicTacToeBoardButtons() {
        mTicTacToeBoard = new Button[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                mTicTacToeBoard[i][j] = findViewById(resId);
                mTicTacToeBoard[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (mPlayer1Turn) {
            ((Button) v).setText("X");
        }
        else {
            ((Button) v).setText("0");
        }

        mRoundCount++;

        if (checkForWin()) {
            if (mPlayer1Turn) {
                player1Wins();
            }
            else {
                player2Wins();
            }
        }
        else if (mRoundCount == 9){
            matchDraw();
        }
        else {
            mPlayer1Turn = !mPlayer1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0 ; j < 3; j++) {
                field[i][j] = mTicTacToeBoard[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        mPlayer1Score++;
        Toast.makeText(this, "Player 1 Wins !!", Toast.LENGTH_LONG).show();
        updatePlayerScore();
        resetGameBoard();
    }

    private void player2Wins() {
        mPlayer2Score++;
        Toast.makeText(this, "Player 2 Wins !!", Toast.LENGTH_LONG).show();
        updatePlayerScore();
        resetGameBoard();
    }

    private void matchDraw() {
        Toast.makeText(this, "Draw !!", Toast.LENGTH_LONG).show();
        resetGameBoard();
    }

    private void resetGameBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mTicTacToeBoard[i][j].setText("");
            }
        }
        mRoundCount = 0;
        mPlayer1Turn = true;
    }

    private void resetGame() {
        mPlayer1Score = 0;
        mPlayer2Score = 0;
        resetGameBoard();
        updatePlayerScore();
    }

    private void updatePlayerScore() {
        mPlayer1.setText("Player 1 : " + mPlayer1Score);
        mPlayer2.setText("Player 2 : " + mPlayer2Score);
    }
}
