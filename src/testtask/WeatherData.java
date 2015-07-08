package testtask;

import java.util.Observable;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class WeatherData extends Observable{

    //Температура, при которой срабатывает датчик
    private float controlTemperature;
    private float temperature;
    //private float humidity;

    //клумба, к которой прикреплен датчик
    private BedFlower bedFlower;

    public WeatherData() {
    }

    public WeatherData(float controlTemperature) {
        this.controlTemperature = controlTemperature;
    }

    public WeatherData(BedFlower bedFlower) {
        this.bedFlower = bedFlower;
    }

    public WeatherData(BedFlower bedFlower, float controlTemperature)
    {
        this.bedFlower = bedFlower;
        this.controlTemperature = controlTemperature;
    }

    public void measurementsChanged()
    {
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature/*, float humidity*/)
    {
        this.temperature = temperature;
        //this.humidity = humidity;
        measurementsChanged();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getControlTemperature() {
        return controlTemperature;
    }

    public void setControlTemperature(float controlTemperature) {
        this.controlTemperature = controlTemperature;
    }

    //public float getHumidity() {
    //    return humidity;
    //}

    public BedFlower getBedFlower() {
        return bedFlower;
    }

    public void setBedFlower(BedFlower bedFlower) {
        this.bedFlower = bedFlower;
    }

}
