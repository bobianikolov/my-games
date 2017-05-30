package guess_the_number;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GuessTheNumber {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int number;
        int guess = 0;
        System.out.print("Please enter you guess: ");
        Random random = new Random();
        number = random.nextInt(100);

        while (guess != number){
            guess = Integer.parseInt(reader.readLine());
            if(guess < number){
                System.out.println("Your guess is too low!");
                System.out.print("Please enter you guess: ");

            }else if(guess > number){
                System.out.println("Your guess is too high!");
                System.out.print("Please enter you guess: ");
            }
        }
        System.out.println("You are win!");
    }
}
