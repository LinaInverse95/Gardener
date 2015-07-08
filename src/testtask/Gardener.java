package testtask;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Gardener implements Observer {

    //����������� ������.
    Machine machine;
    //�����, � ������� ������ ����� ������ ������ ��� ���� ������
    int ready = 0;

    //������� �����, ������� ���� ������. �����������, �.�. ��������� �� �������� ����� ��������� � ���� � ��� �� ����,
    //���� ����� ����� ������������ ����� ������� ���� �������, ����� ����������� ������ ����� ����� �� ���������� � ������.
    public Queue<BedFlower> bedFlowers = new LinkedList<>();

    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) o;
            //�������� ������ ������, ������������ ������� �������� ������
            BedFlower bedFlower = weatherData.getBedFlower();
            //���������, �� ���� �� �������� ������ (������ �� 4 ����)
            if(bedFlower.getLastPouring() !=-1 && bedFlower.getLastPouring()+240> Time.getInstance().getTact()) return;
            if (ready < Time.getInstance().getTact()) ready = Time.getInstance().getTact();
            //�������� � ������� ����� ��� ������
            bedFlowers.add(bedFlower);
            //����� ��������� �������, ���� ������ �� �����������
            Time.getInstance().wait(ready);
        }
        if (o instanceof Time) {
            //�������� ������, ��� ����� ������
            //�������� ������ �� �������
            BedFlower bf = bedFlowers.poll();
            //������� ������ ������������� � ������ � ������ �����.
            //�������������� ����������� � ����������� �������� �������, ����� ������ ����� ������.
            ready = Time.getInstance().getTact() + 5;
            machine.move(bf);
            ready += 10;
            machine.pour(bf);
            //��������� ����� ���������� ������
            bf.setLastPouring(Time.getInstance().getTact());
            //����� ��������� �������, ���� ������ �� �����������
            if (ready < Time.getInstance().getTact()) ready = Time.getInstance().getTact();
            if(!bedFlowers.isEmpty()){ Time.getInstance().wait(ready);}
        }
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
