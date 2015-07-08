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

    //Процедура, которая симулирует срабатывание датчиков оповещения о температуре, читая данные из файла
    //В программу введено понятие "такт". Бесконечный цикл отсчитывает такты, при достижении
    //температуры срабатывания датчика, указанной в файле, осуществляется имитация срабатывания датчика.
    //Для возможности имитации срабатывания датчиков приходится хранить список датчиков

    public void tacts(List<WeatherData> datas) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("work"));
        String str;

        Time time = Time.getInstance(); //Такты, отсчитанный с начала выполнения программы.

        // программа выполняется, пока не закончится файл с временем срабатывания датчиков
        while ((str = reader.readLine()) != null) {
            //убрать лишние пробелы в начале и конце
            str = str.trim();
            //пропуск пустых строк и строк с комментариями
            if (str.isEmpty() || (str.charAt(0)) == '#') continue;
            //строки будет разбирать по токенам, т.к. между словами может быть несколько пробелов и знаки табуляции
            StringTokenizer tokenizer = new StringTokenizer(str, " \n\t\r");
            //если токенов в строке больше 3, э то свидетельствует об ошибке.
            if (tokenizer.countTokens() != 3) throw new IOException("Incorrect data in file 'work'");
            int[] ints = new int[3];
            for (int i = 0; i < 3; i++) {
                try {
                    ints[i] = new Integer((String)tokenizer.nextElement());
                } catch (RuntimeException e) {
                    //если будет брошено исключение об ошибке преобразования, то пусть оно скажет о причине, т.е. о неправильных входных данных
                    throw new IOException("Incorrect data in file 'work'");
                }
            }
            //Если такты в файле расположены не по порядку, бросаем исключение о неверном файле.
            if(ints[0] < time.getTact()) throw new IOException("Incorrect time in file 'work'");
            //Получили объект датчика, указанный в очередной строке. С него поступило оповещение.
            //Объект возможно получить таким образом, т.к. в списке объекты точно упорядочены по номеру.
            WeatherData wd = datas.get(ints[1]);
            //Перед срабатыванием датчика должно пройти некоторое количество тактов вхолостую.
            time.leapTacts(ints[0]);
            //датчик срабатывает в такт, указанный в файле.
            wd.setMeasurements(ints[2]);
        }
        //Корректное завершение работы
        Time.getInstance().stopWork();
    }


    //метод загружает сведения о клумбах и температурах срабатывания каждого датчика из файла в список
    public List<Integer> load() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("bfs"));
        String str;
        //список, в котором хранятся значения температур срабатывания датчиков
        List<Integer> temperatureLimiters = new ArrayList<>();
        //построчно считываем из файла и анализируем строки
        while ((str = reader.readLine()) != null) {
            //убрать лишние пробелы в начале и конце
            str = str.trim();
            //пропуск пустых строк и строк с комментариями
            if (str.isEmpty() || (str.charAt(0)) == '#') continue;
            //добавление температуры для очередного датчика в список температур
            try {
                temperatureLimiters.add(new Integer(str));
            } catch (RuntimeException e) {
                //если будет брошено исключение об ошибке преобразования, то пусть оно скажет о причине, т.е. о неправильных входных данных
                throw new IOException("Incorrect data in file 'bfs'");
            }
        }
        return temperatureLimiters;
    }

    //метод создает и настраивает клумбы и датчики измерений, используя данные о температуре, полученные из файла
    public List<WeatherData> initialise(List<Integer> temeratures, Gardener gardener) {
        List<WeatherData> datas = new ArrayList<>();
        //Порядковый номер - используется для идентификации клумб.
        int i = 0;
        for (Integer t : temeratures) {
            //создание клумбы.
            BedFlower bf = new BedFlower(i);
            //создание датчика. Отношение композиции оправдывается тем, что клумба и датчик привязаны друг к другу.
            WeatherData wd = new WeatherData(bf, t);
            //Используется событийная модель. Садовник получит оповещение.
            wd.addObserver(gardener);
            datas.add(wd);
            i++;
        }
        return datas;
    }

    public static void main(String... args) {
        Main main = new Main();
        //Система садовник
        Gardener gardener = new Gardener();
        //Машина полива. Машина полива будет выводить сведения о работе в указанный поток.
        Machine machine = new Machine(System.out);
        //садовнику дали машину полива
        gardener.setMachine(machine);
        //садовник должен знать текущее время программы. Он будет получать оповещения, когда настало время, когда готова очередня машина
        Time.getInstance().addObserver(gardener);
        //Запуск программы
        try {
            main.tacts(main.initialise(main.load(), gardener));
        } catch (Exception e) {
            System.out.print("Something went wrong =( : " + e.getMessage());
        }
    }
}
