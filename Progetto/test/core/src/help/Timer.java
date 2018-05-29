package help;

import java.util.ArrayList;
import java.util.Date;

public class Timer {



    public void checkTimer(ArrayList<Date> date, int numeroPlayer){

        if(date != null){
            Date date2 = new Date();
            for(int i =0; i < numeroPlayer;i++)
                if(date2.getTime() - date.get(i).getTime() > Info.getInstance().getDurataPowerUp())
                    Info.getInstance().getPaddleresizex().set(i,Info.getInstance().getPaddleresize());
        }
    }
}
