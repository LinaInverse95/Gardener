package testtask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class Main {

    //���������, ������� ���������� ������������ �������� ���������� � �����������, ����� ������ �� �����
    //� ��������� ������� ������� "����". ����������� ���� ����������� �����, ��� ����������
    //����������� ������������ �������, ��������� � �����, �������������� �������� ������������ �������.
    //��� ����������� �������� ������������ �������� ���������� ������� ������ ��������

    public void tacts(List<WeatherData> datas) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("work"));
        String str;

        Time time = Time.getInstance(); //�����, ����������� � ������ ���������� ���������.

        // ��������� �����������, ���� �� ���������� ���� � �������� ������������ ��������
        while ((str = reader.readLine()) != null) {
            //������ ������ ������� � ������ � �����
            str = str.trim();
            //������� ������ ����� � ����� � �������������
            if (str.isEmpty() || (str.charAt(0)) == '#') continue;
            //������ ����� ��������� �� �������, �.�. ����� ������� ����� ���� ��������� �������� � ����� ���������
            StringTokenizer tokenizer = new StringTokenizer(str, " \n\t\r");
            //���� ������� � ������ ������ 3, � �� ��������������� �� ������.
            if (tokenizer.countTokens() != 3) throw new IOException("Incorrect data in file 'work'");
            int[] ints = new int[3];
            for (int i = 0; i < 3; i++) {
                try {
                    ints[i] = new Integer((String)tokenizer.nextElement());
                } catch (RuntimeException e) {
                    //���� ����� ������� ���������� �� ������ ��������������, �� ����� ��� ������ � �������, �.�. � ������������ ������� ������
                    throw new IOException("Incorrect data in file 'work'");
                }
            }
            //���� ����� � ����� ����������� �� �� �������, ������� ���������� � �������� �����.
            if(ints[0] < time.getTact()) throw new IOException("Incorrect time in file 'work'");
            //�������� ������ �������, ��������� � ��������� ������. � ���� ��������� ����������.
            //������ �������� �������� ����� �������, �.�. � ������ ������� ����� ����������� �� ������.
            WeatherData wd = datas.get(ints[1]);
            //����� ������������� ������� ������ ������ ��������� ���������� ������ ���������.
            time.leapTacts(ints[0]);
            //������ ����������� � ����, ��������� � �����.
            wd.setMeasurements(ints[2]);
        }
        //���������� ���������� ������
        Time.getInstance().stopWork();
    }


    //����� ��������� �������� � ������� � ������������ ������������ ������� ������� �� ����� � ������
    public List<Integer> load() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bfs"));
        String str;
        //������, � ������� �������� �������� ���������� ������������ ��������
        List<Integer> temperatureLimiters = new ArrayList<>();
        //��������� ��������� �� ����� � ����������� ������
        while ((str = reader.readLine()) != null) {
            //������ ������ ������� � ������ � �����
            str = str.trim();
            //������� ������ ����� � ����� � �������������
            if (str.isEmpty() || (str.charAt(0)) == '#') continue;
            //���������� ����������� ��� ���������� ������� � ������ ����������
            try {
                temperatureLimiters.add(new Integer(str));
            } catch (RuntimeException e) {
                //���� ����� ������� ���������� �� ������ ��������������, �� ����� ��� ������ � �������, �.�. � ������������ ������� ������
                throw new IOException("Incorrect data in file 'bfs'");
            }
        }
        return temperatureLimiters;
    }

    //����� ������� � ����������� ������ � ������� ���������, ��������� ������ � �����������, ���������� �� �����
    public List<WeatherData> initialise(List<Integer> temeratures, Gardener gardener) {
        List<WeatherData> datas = new ArrayList<>();
        //���������� ����� - ������������ ��� ������������� �����.
        int i = 0;
        for (Integer t : temeratures) {
            //�������� ������.
            BedFlower bf = new BedFlower(i);
            //�������� �������. ��������� ���������� ������������� ���, ��� ������ � ������ ��������� ���� � �����.
            WeatherData wd = new WeatherData(bf, t);
            //������������ ���������� ������. �������� ������� ����������.
            wd.addObserver(gardener);
            datas.add(wd);
            i++;
        }
        return datas;
    }

    public static void main(String... args) {
        Main main = new Main();
        //������� ��������
        Gardener gardener = new Gardener();
        //������ ������. ������ ������ ����� �������� �������� � ������ � ��������� �����.
        Machine machine = new Machine(System.out);
        //��������� ���� ������ ������
        gardener.setMachine(machine);
        //�������� ������ ����� ������� ����� ���������. �� ����� �������� ����������, ����� ������� �����, ����� ������ �������� ������
        Time.getInstance().addObserver(gardener);
        //������ ���������
        try {
            main.tacts(main.initialise(main.load(), gardener));
        } catch (Exception e) {
            System.out.print("Something went wrong =( : " + e.getMessage());
        }
    }
}
