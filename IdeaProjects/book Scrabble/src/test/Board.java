package test;



import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    public Tile[][] board;
    public static Board checkIfboard = null;
    public static int flag=0;
    public static int firstWord=0;
    public Board() {
        this.board = new Tile[15][15];
        for (Tile[] tiles : board) {
            Arrays.fill(tiles, null);
        }
    }

    public Tile[][] getTiles() {
        return board.clone();
    }

    public static Board getBoard() {
        if (checkIfboard == null) {
            checkIfboard = new Board();
        }
        return checkIfboard;
    }
    public int tryPlaceWord(Word word) {
        if (!boardLegal(word)) {
            return 0;
        }

        ArrayList<Word> newWords = getWords(word);
        int res = 0;
        for (Word newWord : newWords) {
            if (!dictionaryLegal(newWord)) {
                return 0;
            }
            int wordScore = getScore(newWord);
            res += wordScore;
            placeWordOnBoard(newWord);
        }

        return res;
    }

    public void  placeWordOnBoard(Word word){
        int row = word.getRow();
        int col = word.getCol();
        if (word.isVertical()) {
            for (Tile tile : word.getTiles()) {
                board[row][col] = tile;
                row++;
            }
        } else {
            for (Tile tile : word.getTiles()) {
                board[row][col] = tile;
                col++;
            }
        }
    }

    public boolean boardLegal(Word word) {

        int col = word.getCol();
        int row = word.getRow();
        int size = word.getTiles().length;
        boolean vertical = word.vertical;

        if (inSite(row, col, size, vertical)) {
                if(star(row, col, size, vertical)) {
                    flag = 1;
                    return true;
                }
            }

        if (inSite(row, col, size, vertical)){
            return overLap(row, col, size, vertical, word);
        }
        return false;
    }

    public boolean overLap(int row, int col, int size, boolean upDown,Word word) {
        int test = 0;
        for (int i = 0; i < size; i++) {
            int c;
            int r;
            if (upDown) {
                r = row + i; //every iteration check next row;
            } else {
                r = row;  //same row diff col;
            }
            if (upDown) {
                c = col; //same col diff row;
            } else {
                c = col + i;
            }
           if (board[r][c] != null&&null != word.getTiles()[i] && board[r][c] != word.getTiles()[i]) {
               return false;
           }

            if (leanOn(r, c)) {
                test = 1;
            }
        }
        if (test == 1) {
            return true;
        }
        return false;
    }
    public boolean leanOn(int r,int c){
        return (r > 0 && board[r - 1][c] != null) || // check above
                (r < 14 && board[r + 1][c] != null) || // check below
                (c > 0 && board[r][c - 1] != null) || // check left
                (c < 14 && board[r][c + 1] != null); // check right
    }
    public boolean inSite(int row, int col, int size, boolean upDown) {
        if (row < 0 || col < 0) {
            return false;
        }

        if (upDown) {
            return row + size - 1 <= 14;
        } else {
            return col + size - 1 <= 14;
        }
    }

    public boolean star(int row, int col, int size, boolean upDown) { // check if the first turn is on middle
        if (upDown) {
            if (col != 7) {
                return false;
            } else return row + (size - 1) >= 7;
        } else {
            if (row != 7) {
                return false;
            } else return col + (size - 1) >= 7;
        }
    }

    public boolean dictionaryLegal (Word word){
        return true;
    }

    public ArrayList<Word> getWords(Word word) {
        ArrayList<Word> words = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();//dynamic array for the new word.
        for (int i =0;i<word.getTiles().length;i++){//in case the new word have null letter(the letter on the board)
           if(word.tiles[i]==null&&word.isVertical())
           {
               word.tiles[i]=board[word.getRow()+i][word.getCol()];
            } else if (word.tiles[i]==null&&!word.isVertical()) {
                word.tiles[i]=board[word.getRow()][word.getCol()+i];
            }
        }
        words.add(word);
        Board b= new Board();
        b.placeWordOnBoard(word);//place the word on temp board and make the logic more simple
        for (int i = 0; i < word.getTiles().length; i++) {
            int r = word.getRow();
            int c = word.getCol();
            boolean test = true;
            int row=0;//verbal to indicate where the new word that crete start in case horizontal
            int col=0;//verbal to indicate where the new word that crete start in case vertical
            if (word.isVertical()) {
                r += i;
                if (r < 15 && board[r][c] == null) {
                    if (c > 0 && board[r][c - 1] != null) {
                        // check for left overlapping
                        int j = c - 1;
                        while (j >= 0 && board[r][j-1] != null) { //until there is overlapping left
                            j--;
                        }
                        col=j;
                        while(j!=word.getCol()+1){
                            if(j<15 &&board[r][j]!=null){
                                tiles.add(board[r][j])
                          ;}

                            else if(j<15 &&board[r][j]==null){ // in case the letter not on board we will take the letter from the word.
                                tiles.add(word.getTiles()[i]);
                            }
                           col--;
                            j++;
                        }

                        test=false;
                    }

                    if (c < 14 && board[r][c + 1] != null||b.board[r][c+1]!=null) {
                        // check for right overlapping
                        if(test&&b.board[r][c]!=null) { //in case this first letter
                            tiles.add(b.board[r][c]);
                            col=c;
                        }
                        int j = c+1;
                        while (j < 15 && board[r][j] != null||j<15 && b.board[r][j]!=null) {
                                if(board[r][j]!=null){
                                tiles.add(board[r][j]);
                                }
                                else{
                                tiles.add(b.board[r][j]);
                            }
                            j++;
                        }
                        test=false;
                    }
                    if(!test){
                    words.add(new Word(tiles.toArray(new Tile[tiles.size()]), r, col, false));//convert the array list object to an regular array
                    tiles.clear();}
                }
            } else { // Horizontal
                c += i;
                if (c < 15 && board[r][c] == null) {
                    if (r > 0 && board[r - 1][c] != null) {
                        // check for top overlapping
                        int j = r - 1;
                        while (j >= 0 && board[j-1][c] != null) {
                            j--;
                        }
                        row=j;
                        while(j!=word.getRow()+1){
                            if(j<15&&board[j][c]!=null){
                            tiles.add(board[j][c]);}
                            else if(j<15&&board[j][c]==null){
                                tiles.add(word.getTiles()[i]);
                            }
                            j++;
                        }
                      test=false;
                    }
                    if (r < 14 && board[r + 1][c] != null ||b.board[r+1][c]!=null) {
                        // check for bottom  overlapping
                        if(test&&b.board[r][c]!=null) {// in case ths is first word
                            tiles.add(b.board[r][c]);
                            row=r;
                        }
                        int j = r+1;
                        while (j < 15 && board[j][c] != null||j<15 &&b.board[j][c]!=null) {
                            if(board[j][c]==null){
                                tiles.add(b.board[j][c]);
                            }
                            else {
                                tiles.add(board[j][c]);
                            }
                            j++;
                        }
                        test=false;
                    }
                    if(!test){
                    words.add(new Word(tiles.toArray(new Tile[tiles.size()]), row, c, true)); //convert the array list object to an regular array
                    tiles.clear();
                   }
                }
            }
        }

        return words;
    }

    public int  getScore(Word word) {
        int res = 0;
        int sumWord = 0;
        for (int i = 0; i < word.getTiles().length; i++) { //calc the sum of the word
            sumWord += word.getTiles()[i].score;
        }
        int[] scores = {6, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 2,0, 0,6,  //this 15*15 array with the scores like the demand rules,the numbers represent: 4,6=sum of word*2,3. 2,3=letter score*2,3
                0, 4, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 4, 0,
                0, 0, 4, 0, 0, 0, 2,0, 2, 0, 0, 0, 4, 0, 0,
                2, 0,0, 4, 0, 0, 0, 2, 0, 0, 0, 4, 0, 0, 2,
                0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0,
                0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0,
                0, 0, 2, 0, 0, 0, 2,0, 2, 0,0, 0, 2, 0, 0,
                6, 0, 0, 2, 0, 0, 0, 4, 0, 0, 0, 2, 0, 0, 6,
                0, 0, 2, 0, 0, 0, 2,0, 2, 0, 0, 0, 2, 0, 0,
                0, 3, 0,0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0,
                0, 0, 0, 0, 4, 0, 0, 0, 0, 0,4, 0, 0, 0, 0,
                2, 0, 0, 4, 0, 0, 0, 2, 0, 0, 0, 4, 0, 0, 2,
                0, 0, 4, 0, 0, 0, 2,0, 2, 0, 0, 0,4, 0, 0,
                0, 4, 0, 0, 0, 3,0, 0, 0, 3, 0, 0, 0, 4,0,
               6,0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 2, 0, 0, 6};
        int[][] scores2D = new int[scores.length / 15][15];

        for (int i = 0; i < scores.length / 15; i++) { //turn the array scores for 2d array for more simple logic
            for (int j = 0; j < 15; j++) {
                scores2D[i][j] = scores[i * 15 + j];
            }
        }
        Board b= new Board();
        b.placeWordOnBoard(word);
        int size=word.getTiles().length;
        int r=word.getRow();
        int c=word.getCol();
        if(word.isVertical()){
           for(int i =0;i<size;i++){
               if(b.board[r+i][c]!=null){
                   if(scores2D[r+i][c]>3) {
                       if (firstWord == 0 && r+i == 7 && c == 7) {//only if this is first word and this word overlapping on star point
                           res+=word.getTiles()[i].score;
                           res += sumWord*2;
                           res -= sumWord;
                       }
                       else{
                           if (scores2D[r + i][c] == 4&&firstWord!=1) {
                               res+=word.getTiles()[i].score;
                               res +=sumWord*2;
                               res -= sumWord;
                           } else if (scores2D[r + i][c] == 6) {
                               res+=word.getTiles()[i].score;
                               res += sumWord*3;
                               res -= sumWord / 2;
                           } else if (firstWord==1) {
                               res+=word.getTiles()[i].score;
                           }
                       }
                   }
                      else if(scores2D[r+i][c]>0){
                          if(firstWord==1&&c==7&r+i==7){
                              res+=word.getTiles()[i].score;
                          }
                          res+=scores2D[r+i][c]*word.getTiles()[i].score;
                      } else if (scores2D[r+i][c]==0) {
                          res+=word.getTiles()[i].score;
                      }
                   }
               }
           }
        else{// Horizontal
            for(int i =0;i<size;i++){
                if(b.board[r][c+i]!=null){
                    if(scores2D[r][c+i]>3){
                        if(firstWord==0&&r==7&&c+i==7){//only if this is first word and this word overlapping on star point
                            res+=word.getTiles()[i].score;
                            res+=sumWord*2;
                            res-=sumWord;
                        }
                        else {
                            if (scores2D[r][c+i] == 4) {
                                res+=word.getTiles()[i].score;
                                res += sumWord*2;
                                res -= sumWord;
                            } else if (scores2D[r][c+i] == 6) {
                                res+=word.getTiles()[i].score;
                                res += sumWord*3;
                                res -= sumWord / 2;
                            }
                            else if (firstWord==1) {
                                res+=word.getTiles()[i].score;
                            }
                        }
                    }
                        else if(scores2D[r][c+i]>0){
                        if(firstWord==1&&c+i==7&r==7){
                            res+=word.getTiles()[i].score;
                        }
                            res+=scores2D[r][c+i]*word.getTiles()[i].score;
                        } else if (scores2D[r][c+i]==0) {
                            res+=word.getTiles()[i].score;
                        }
                    }
                }
            }
firstWord=1;
return res;
    }
}