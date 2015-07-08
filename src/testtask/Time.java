package testtask;

import java.util.Observable;

/**
 * Created by ALUCARA on 08.07.2015.
 */

//Класс, который имитирует течение времени в программе. Отсчитывает количество тактов, прошедших с начала программы+
//предоставляет функции таймера
public class Time extends Observable{
    //Такт, прошедший с начала программы
    private int tact;
    //Отметка ближайшего времени, когда какая-либо машина освободится
    private int timer;
    //Время в программе одно для всех
    private static Time _time = null;

    private Time() {
        tact = 0;
        timer = -1;
    }

    public static Time getInstance()
    {
        if(_time == null) _time = new Time();
        return _time;
    }


    //Возвращает текущий такт в программе
    public int getTact() {
        return tact;
    }

    //Пропускает заданное количество тактов
    public void leapTacts(int t)
    {
        while (tact!=t)
        {
            if(tact == timer) {
                setChanged();
                notifyObservers();
            }
            tact++;
        }
    }

    //Используется, чтобы назначить время, когда машина будет готова
    public void wait(int t)
    {
        timer = t;
    }

    //корректное заверешение работы
    public void stopWork()
    {
        while (tact<=timer)
        {
            if(tact == timer) {
                setChanged();
                notifyObservers();
            }
            tact++;
        }
    }
}
