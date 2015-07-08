package testtask;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Gardener implements Observer {

    //Поливальная машина.
    Machine machine;
    //время, в которое машина будет готова полить еще одну клумбу
    int ready = 0;

    //Очередь клумб, которые надо полить. Формируется, т.к. сообщения от датчиков могут поступить в один и тот же такт,
    //либо время между оповещениями будет слишком мало времени, чтобы поливальные машины могли сразу же приступить к поливу.
    public Queue<BedFlower> bedFlowers = new LinkedList<>();

    public void update(Observable o, Object arg) {
        if (o instanceof WeatherData) {
            WeatherData weatherData = (WeatherData) o;
            //Получили объект клумбы, относительно которой поступил сигнал
            BedFlower bedFlower = weatherData.getBedFlower();
            //Проверили, не рано ли поливать клумбу (прошло ли 4 часа)
            if(bedFlower.getLastPouring() !=-1 && bedFlower.getLastPouring()+240> Time.getInstance().getTact()) return;
            if (ready < Time.getInstance().getTact()) ready = Time.getInstance().getTact();
            //Добавили в очередь клумб для полива
            bedFlowers.add(bedFlower);
            //Нужно подождать момента, пока машина не освободится
            Time.getInstance().wait(ready);
        }
        if (o instanceof Time) {
            //Поступил сигнал, что время прошло
            //Вытащили клумбу из очереди
            BedFlower bf = bedFlowers.poll();
            //Команда машине переместиться к клумбе и полить цветы.
            //Соответственно вычисляется и сохраняется значение времени, когда машина будет готова.
            ready = Time.getInstance().getTact() + 5;
            machine.move(bf);
            ready += 10;
            machine.pour(bf);
            //Сохранили время последнего полива
            bf.setLastPouring(Time.getInstance().getTact());
            //Нужно подождать момента, пока машина не освободится
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
