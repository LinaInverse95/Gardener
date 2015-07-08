package testtask;

import java.io.PrintStream;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Machine {

    //������� ������������� ����������� ������
    BedFlower currentBf;

    int N;
    //����� ������ ���������� � �������� ������
    PrintStream out;

    public Machine(int N, PrintStream out, BedFlower firstBf) {
        this.N = N; this.out = out;this.currentBf = firstBf;
    }

    //������������� � ������
    public void move(BedFlower bedFlower, int tact) {
        out.println("Machine #"+ N+ "  "+tact + " MoveTo " + bedFlower);
        currentBf = bedFlower;
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

    public BedFlower getCurrentBf() {
        return currentBf;
    }

    public void setCurrentBf(BedFlower currentBf) {
        this.currentBf = currentBf;
    }
}
