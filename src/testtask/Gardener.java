package testtask;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Gardener implements Observer {

    //����������� ������, ������������� ������� ������
    Machine machine;
    //�����, � ������� ������� ������ ����� ������ ������ ��� ���� ������
    int ready = 0;

    //������� ���� �����
    LinkedList<Machine> machines;
    //�������, ���������� �������� �������, � ������� ������ �����������.
    LinkedList<Integer> readies = new LinkedList<>();

    public void setMachines(LinkedList<Machine> machines) {
        this.machines = machines;
        //������� ��� ������ ��������
        for (int i = 0; i < machines.size(); i++)
            readies.push(0);
    }

    //������� �����, ������� ���� ������. �����������, �.�. ����� ������������ ����� ������� ���� �������,
    // ����� ����������� ������ ����� ����� �� ���������� � ������.
    public Queue<BedFlower> bedFlowers = new LinkedList<>();

    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            //������, ������ ������ ���� �����������, ��������� ������ � �������, � �� ����� ����� ������
            ready = readies.peek();
            machine = machines.peek();
            WeatherData weatherData = (WeatherData) o;
            //�������� ������ ������, ������������ ������� �������� ������
            BedFlower bedFlower = weatherData.getBedFlower();
            //���������, �� ���� �� �������� ������ (������ �� 4 ����)
            if (bedFlower.getLastPouring() != -1 && bedFlower.getLastPouring() + 240 > Time.getInstance().getTact())
                return;
            if (ready < Time.getInstance().getTact()) {
                //readies.poll();
                ready = Time.getInstance().getTact();
                //readies.add(0, ready);
            }
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
            ready = Time.getInstance().getTact();
            machine.move(bf, ready);
            ready+=5;
            machine.pour(bf, ready);
            ready += 10;
            readies.poll();
            readies.add(0, ready);
            //������ ������, ������� ��������� ������ ����� �������, ����� ������������, ��������� � ����� �������
            machines.add(machines.poll());
            readies.add(readies.poll());
            ready = readies.peek();
            machine = machines.peek();
            //��������� ����� ���������� ������
            bf.setLastPouring(Time.getInstance().getTact());
            //����� ��������� �������, ���� ������ �� �����������
            if (ready < Time.getInstance().getTact()) {
                readies.poll();
                ready = Time.getInstance().getTact();
                readies.add(0, ready);
            }
            if (!bedFlowers.isEmpty()) {
                Time.getInstance().wait(ready);
            }
        }
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
