package example2;

/**
 *
 * @author tadaki
 */
public class Token {
    public final int t;

    public Token(int t) {
        this.t = t;
    }
    
    @Override
    public String toString(){
        return String.valueOf(t);
    }
}
