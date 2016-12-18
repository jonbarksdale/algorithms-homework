import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Require a single integer argument");
    }

    int samples = Integer.parseInt(args[0]);

    String[] inputs = StdIn.readAllStrings();

    RandomizedQueue<String> queue = new RandomizedQueue<>();

    for (String input : inputs) {
      queue.enqueue(input);
    }

    for (int i = 0; i < samples; i++) {
      StdOut.println(queue.dequeue());
    }
  }
}
