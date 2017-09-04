package com.example.bharatkumarreddy.chessqueens;

public class puzzle {

    private int[][] board = new int[8][8];
    private int x,y;
    private int[] sol = new int[8];
    private int queens = 8;

    puzzle(){
        for(int i=0;i<8;i++) {
            sol[i] = -1;
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
    }
//convert position into co-ordinates
    void getXY(int pos){
        x=pos/8;
        y=pos%8;

    }
//public function to place queen on board
     boolean placeQueen(int pos){

         getXY(pos);

         if(board[x][y] == 0) {
             placeQueenAt(x,y);
             return true;
         }
         return false;
     }
//public function to remove queen on board
    void removeQueen(int pos){

        getXY(pos);
        removeQueenAt(x,y);
    }
//queen placing algorithm
    private void placeQueenAt(int x,int y){

        sol[x] = y;
        board[x][y]=99;
        queens--;

        for(int i=0;i<8;i++)
            if(i!=y)
                board[x][i]++;

        for(int i=0;i<8;i++)
            if(i!=x)
                board[i][y]++;


        for(int i=x+1,j=y+1;i<8&&j<8;i++,j++){
            board[i][j]++;
        }

        for(int i=x+1,j=y-1;i<8&&j>=0;i++,j--){
            board[i][j]++;
        }

        for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
            board[i][j]++;
        }

        for(int i=x-1,j=y+1;i>=0&&j<8;i--,j++){
            board[i][j]++;
        }
    }
//queen removing algorithm
    private void removeQueenAt(int x,int y){

        sol[x] = -1;
        board[x][y]=0;
        queens++;

        for(int i=0;i<8;i++)
            if(i!=y)
                board[x][i]--;

        for(int i=0;i<8;i++)
            if(i!=x)
                board[i][y]--;


        for(int i=x+1,j=y+1;i<8&&j<8;i++,j++){
            board[i][j]--;
        }

        for(int i=x+1,j=y-1;i<8&&j>=0;i++,j--){
            board[i][j]--;
        }

        for(int i=x-1,j=y-1;i>=0&&j>=0;i--,j--){
            board[i][j]--;
        }

        for(int i=x-1,j=y+1;i>=0&&j<8;i--,j++){
            board[i][j]--;
        }
    }
//check if all queens are placed
    boolean check(){

        for(int i=0;i<8;i++) if(sol[i]==-1) return false;
        return true;
    }
//check if no more moves are possible
    public boolean isEnd(){

        boolean flag = true;
        if(check()) return false;

        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 0) {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }
//return number of queens left to be placed
    public int queensLeft(){
        return queens;
    }
}
