import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Joke {
    // from http://www.jokes4us.com/miscellaneousjokes/mathjokes/geometryjokes.html
    public Joke(String q, String a) {

        this.q = q;
        this.a = a;
    }
    final String q;
    final String a;
    static ArrayList<Joke> jokes = new ArrayList<>();
    static Random random = new Random();
    static void readJokes() throws FileNotFoundException {
        Scanner in = new Scanner(new File("jokes.txt"));
        while (in.hasNextLine()) {
            Joke j = new Joke(in.nextLine(), in.nextLine());
            jokes.add(j);
        }
        in.close();
    }
    static Joke getJoke() {
        int n = random.nextInt(jokes.size());
        return jokes.get(n);
    }
    
    static Joke notAJoke() {
        return new Joke("Hello! I am TANBot. Please enter 3 or more measures of a triangle, "
                + "and I'll take care of solving the rest.", "A visualization of the triangle "
                        + "you requested should appear to the right. If solving triangles for hours "
                        + "on end is torture to you, I shall attempt to lighten the mood with "
                        + "high-quality jokes.");
                
    }
    
    public static void main(String args[]) throws Exception {
        readJokes();
        for(int i = 0; i < 3; i++) {
            Joke j = getJoke();
            System.out.println(j.q);
            Thread.sleep(2000);
            System.out.println(j.a);
            System.out.println();
            Thread.sleep(1000);
            
        }
    }
    

}
