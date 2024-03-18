package test;


import java.util.Objects;
import java.util.Random;

public class Tile {
    final char letter;
    final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o; // Cast o to Tile
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }
    public static class  Bag {
        private static Bag bag=null;
        public Tile[] tiles;
        public int[] letterScore;
        public int[] maxScore;
        public int[] lettersQuantities;
        public char[] letters ;
        private   Bag(){
            maxScore = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            letterScore= new int[]{1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
            lettersQuantities = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            letters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            tiles=new Tile[26];
            for (int i = 0; i < letters.length; i++) {
                tiles[i] = new Tile(letters[i], letterScore[i]);
            }
        }
        public Tile getRand() {
            for (int lettersQuantity : lettersQuantities) {
                if (lettersQuantity != 0) {
                    break;
                }
                return null;
            }
            while(true) {
                Random random = new Random();
                int randomNumber = random.nextInt(26);
                if (lettersQuantities[randomNumber] > 0) {
                    lettersQuantities[randomNumber] -= 1;
                    return tiles[randomNumber];
                }
            }
        }

        public Tile getTile(char letter) {
            for (int i = 0; i < letters.length; i++) {
                if (letter == letters[i] && lettersQuantities[i] > 0) {
                    lettersQuantities[i]--;
                    return tiles[i];
                }
            }
            return null;
        }

        public void put(Tile letter) {
            for (int i = 0; i < letters.length; i++) {
                if (letter.letter == letters[i]) {
                    if(lettersQuantities[i]<maxScore[i]){
                        lettersQuantities[i]++;
                        break;
                    }
                }
            }
        }
        public int size(){
            int sum=0;
            for (int j : lettersQuantities) {
                sum += j;
            }
            return sum;
        }
        public int[] getQuantities(){
            return lettersQuantities.clone();
        }
        public static Bag getBag(){
            if(bag==null){
                bag=new Bag();
            }
            return bag;
        }
    }
}
