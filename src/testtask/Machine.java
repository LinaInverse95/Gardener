package testtask;

import java.io.PrintStream;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Machine {

    int N;
    //����� ������ ���������� � �������� ������
    PrintStream out;

    public Machine(int N, PrintStream out) {
        this.N = N; this.out = out;
    }

    //������������� � ������
    public void move(BedFlower bedFlower, int tact) {
        out.println("Machine #"+ N+ "  "+tact + " MoveTo " + bedFlower);
    }

    //������ ������
    public void pour(BedFlower bedFlower, int tact) {
        out.println("Machine #"+ N+ "  "+tact + " Pour " + bedFlower);
    }

    @Override
    public String toString() {
        return "Machine{" +
                "N=" + N +
                '}';
    }
}
