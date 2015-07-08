package testtask;

import java.io.PrintStream;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Machine {

    //����� ������ ���������� � �������� ������
    PrintStream out;

    public Machine(PrintStream out) {
        this.out = out;
    }

    //������������� � ������
    public void move(BedFlower bedFlower)
    {
        out.println(Time.getInstance().getTact() +" MoveTo "+ bedFlower);
    }

    //������ ������
    public void pour(BedFlower bedFlower)
    {
        out.println(Time.getInstance().getTact()+5 +" Pour "+ bedFlower);
    }
}
