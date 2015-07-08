package testtask;

import java.io.PrintStream;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Machine {

    //Поток вывода информации о действия машины
    PrintStream out;

    public Machine(PrintStream out) {
        this.out = out;
    }

    //Переместиться к клумбе
    public void move(BedFlower bedFlower)
    {
        out.println(Time.getInstance().getTact() +" MoveTo "+ bedFlower);
    }

    //полить клумбу
    public void pour(BedFlower bedFlower)
    {
        out.println(Time.getInstance().getTact()+5 +" Pour "+ bedFlower);
    }
}
