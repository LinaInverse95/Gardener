package testtask;

import java.util.*;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Gardener implements Observer {

    //“аблица времени перемещени€ между клумбами
    List<int[]> movements;
    //ѕоливальна€ машина, обслуживающа€ текущий запрос
    Machine machine;
    //врем€, в которое текуща€ машина будет готова полить еще одну клумбу
    int ready = 0;

    //ќчередь всех машин
    LinkedList<Machine> machines;
    //ќчередь, содержаща€ значени€ времени, в которое машины освобод€тс€.
    LinkedList<Integer> readies = new LinkedList<>();

    public void setMachines(LinkedList<Machine> machines) {
        this.machines = machines;
        //—начала все машины доступны
        for (int i = 0; i < machines.size(); i++)
            readies.push(0);
    }

    public List<int[]> getMovements() {
        return movements;
    }

    public void setMovements(List<int[]> movements) {
        this.movements = movements;
    }

    //ќчередь клумб, которые надо полить. ‘ормируетс€, т.к. между оповещени€ми будет слишком мало времени,
    // чтобы поливальные машины могли сразу же приступить к поливу.
    public Queue<BedFlower> bedFlowers = new LinkedList<>();

    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            //машина, котора раньше всех освободитс€, находитс€ первой в очереди, и ей будет отдан запрос
            ready = readies.peek();
            machine = machines.peek();
            WeatherData weatherData = (WeatherData) o;
            //ѕолучили объект клумбы, относительно которой поступил сигнал
            BedFlower bedFlower = weatherData.getBedFlower();
            //ѕроверили, не рано ли поливать клумбу (прошло ли 4 часа)
            if (bedFlower.getLastPouring() != -1 && bedFlower.getLastPouring() + 240 > Time.getInstance().getTact())
                return;
            if (ready < Time.getInstance().getTact()) {
                //readies.poll();
                ready = Time.getInstance().getTact();
                //readies.add(0, ready);
            }
            //ƒобавили в очередь клумб дл€ полива
            bedFlowers.add(bedFlower);
            //Ќужно подождать момента, пока машина не освободитс€
            Time.getInstance().wait(ready);
        }
        if (o instanceof Time) {
            //ѕоступил сигнал, что врем€ прошло
            //¬ытащили клумбу из очереди
            BedFlower bf = bedFlowers.poll();
            // оманда машине переместитьс€ к клумбе и полить цветы.
            //—оответственно вычисл€етс€ и сохран€етс€ значение времени, когда машина будет готова.
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
            //теперь машины, которым требуетс€ больше всего времени, чтобы освободитьс€, наход€тс€ в конце очереди
            machines.add(machines.poll());
            readies.add(readies.poll());
            ready = readies.peek();
            machine = machines.peek();
            //—охранили врем€ последнего полива
            bf.setLastPouring(Time.getInstance().getTact());
            //Ќужно подождать момента, пока машина не освободитс€
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
