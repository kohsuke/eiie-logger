/**
 * @author Kohsuke Kawaguchi
 */
public class Demo {
    public static void main(String[] args) {
        try {
            new Foo();
        } catch (Error e) {
            // somebody wrote a poor error handler
            System.out.println("Swallowing a problem");
        }
    }
}
