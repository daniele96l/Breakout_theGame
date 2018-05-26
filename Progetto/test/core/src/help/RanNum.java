package help;

import java.util.Random;

public class RanNum {

    public RanNum(){

    }
    public String generate(){

        Random n = new Random();
        String s = "" + n.nextInt(1000);

        return  s;
    }
}
