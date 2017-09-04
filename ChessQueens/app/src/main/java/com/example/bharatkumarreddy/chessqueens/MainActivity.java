package com.example.bharatkumarreddy.chessqueens;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(21)

public class MainActivity extends AppCompatActivity {

    Integer[] tiles = {R.drawable.white,R.drawable.black};
    GridView gridView;
    final puzzle p = new puzzle();
    TextView queensLeftTv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textview to display number of queens left to be placed
        queensLeftTv = (TextView)findViewById(R.id.queensLeftTv);

        //grid view to display the chess board
        gridView = (GridView)findViewById(R.id.chessboard);
        gridView.setAdapter(new ChessBoard(this,p));
    }

    //adapter to generate chess board tiles
    public class ChessBoard extends BaseAdapter{
        Context context;
        ImageView squareView,piece;
        puzzle p;

        public ChessBoard(Context c,puzzle pz){
            p = pz;
            context = c;
        }
        @Override
        public int getCount() {
            return 64;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //new layout for chess board tile
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            final View squareContainerView = layoutInflater.inflate(R.layout.square, null);

            GridView grid = (GridView)parent;

            //creating an imageview object to set the tile image
            squareView = (ImageView)squareContainerView.findViewById(R.id.tile);
            squareView.setImageResource(tiles[(position + position / 8) % 2]);

            //listener for chessboard tile
            View.OnClickListener clickListener = new View.OnClickListener() {

                boolean flag = true,rmFlag = true;
                boolean ret , ret2;

                @Override
                public void onClick(View v) {

                    //creates a new square tile for layout
                    squareView = (ImageView)squareContainerView.findViewById(R.id.tile);
                    piece = (ImageView)squareContainerView.findViewById(R.id.piece);

                    //check if the current tile is empty.
                    if(flag){
                        ret = p.placeQueen(position);

                        //checking if current tile is a valid position to place queen
                        if(ret) {

                            piece.setImageResource(R.drawable.queen);
                            flag = false;
                            rmFlag = true;

                            //checking if the puzzle is solved.
                            ret2 = p.check();
                            if(ret2) {
                                //display alert message if puzzle solved
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                                alertDialog.setTitle("Congrats\nYou solved puzzle");
                                alertDialog.setIcon(R.drawable.party_icon);
                                AlertDialog errorDialog = alertDialog.create();
                                errorDialog.show();
                            }

                        }
                        //in case of invalid position flash a red symbol on the current tile
                        else {
                                squareView.setImageResource(R.drawable.red);
                                Handler handler = new Handler();

                                //flashing red signal for half second
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        squareView.setImageResource(tiles[(position + position / 8) % 2]);
                                    }
                                }, 500);

                        }
                    }
                    //In case a queen is present remove it.
                    else {
                        p.removeQueen(position);
                        piece.setImageResource(tiles[(position + position/8)%2 ]);
                        flag = true;
                        if(rmFlag) rmFlag = false;
                    }

                    //Alert box to indicate game over sice no more queens can be placed.
                    if(p.isEnd() && rmFlag){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setTitle("No more moves possible");
                        AlertDialog errorDialog = alertDialog.create();
                        errorDialog.show();
                    }
                }

            };

            //setting the tile height equal to width to create a square tile.
            int size = grid.getColumnWidth();
            squareContainerView.setLayoutParams(new GridView.LayoutParams(size, size));

            //setting click listener
            squareContainerView.setOnClickListener(clickListener);

            //updating the text view with number of queens left.
            queensLeftTv.setText("Queens Left : "+p.queensLeft());
            return squareContainerView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
