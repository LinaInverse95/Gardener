package testtask;

import java.util.*;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Gardener implements Observer {

    //������� ������� ����������� ����� ��������
    List<int[]> movements;
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

    public List<int[]> getMovements() {
        return movements;
    }

    public void setMovements(List<int[]> movements) {
        this.movements = movements;
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

            int a = machine.getCurrentBf().getId();
            int b = bf.getId();
            int c = 0;

            if (a>b) c = movements.get(a)[b];
            if (a<b) c = movements.get(b)[a];
            machine.move(bf, ready);

            ready += c;
            machine.pour(bf, ready);
            ready += 10;

            readies.poll();
            readies.add(0, ready);
            //machine.setCurrentBf(bf);
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
                ready = Time.getInstance().getTact()+1;
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
