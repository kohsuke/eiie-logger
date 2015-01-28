/**
 * @author Kohsuke Kawaguchi
 */
public class Foo {
    static {
        if (true)
            throw new IllegalArgumentException("Yo!");
    }
}
