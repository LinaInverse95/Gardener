package testtask;

import java.util.Observable;

/**
 * Created by ALUCARA on 08.07.2015.
 */

//�����, ������� ��������� ������� ������� � ���������. ����������� ���������� ������, ��������� � ������ ���������+
//������������� ������� �������
public class Time extends Observable{
    //����, ��������� � ������ ���������
    private int tact;
    //������� ���������� �������, ����� �����-���� ������ �����������
    private int timer;
    //����� � ��������� ���� ��� ����
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


    //���������� ������� ���� � ���������
    public int getTact() {
        return tact;
    }

    //���������� �������� ���������� ������
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

    //������������, ����� ��������� �����, ����� ������ ����� ������
    public void wait(int t)
    {
        timer = t;
    }

    //���������� ����������� ������
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
