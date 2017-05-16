package hangman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hangman {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        File file = new File("src/hangman/words.txt");
        BufferedReader readerFile = new BufferedReader(new FileReader(file));

        List<String> allWords = new ArrayList<>();
        String line;
        while ((line = readerFile.readLine()) != null){
            allWords.add(line);
        }

        int indexWord = new Random().nextInt(allWords.size());
        String getRandomWord = allWords.get(indexWord);

        StringBuilder sb = new StringBuilder();
        sb.append(getRandomWord);
        for (int i = 0; i < getRandomWord.length(); i++) {
            sb.setCharAt(i,'*');
        }

        String replaceWord = sb.toString();
        System.out.println(replaceWord);

        int count = 3;
        while (count > 0){

            System.out.print("Enter your letter: ");
            String character = reader.readLine();

            if(getRandomWord.contains(character)){
                for (int i = 0; i < getRandomWord.toCharArray().length; i++) {
                    String letter = String.valueOf(getRandomWord.charAt(i));
                    if(character.equals(letter)){
                        sb.setCharAt(i,getRandomWord.charAt(i));
                    }
                }
                System.out.println(sb.toString());

            }else {
                count--;
                if(count == 0){
                    System.out.println("Game over!");
                }else {
                    System.out.printf("Wrong letter! Remain %d experience!%n", count);
                }
            }

            if(!sb.toString().contains("*")){
                System.out.println("You are winner!");
                break;
            }
        }
    }
}
