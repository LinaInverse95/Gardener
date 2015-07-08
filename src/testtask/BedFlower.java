package testtask;

/**
 * Created by ALUCARA on 07.07.2015.
 */
public class BedFlower  {

    private int id;
    //время последнего полива
    private int lastPouring = -1;

    public BedFlower() {
    }

    public BedFlower(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BedFlower # " + id+".";
    }

    public int getLastPouring() {
        return lastPouring;
    }

    public void setLastPouring(int lastPouring) {
        this.lastPouring = lastPouring;
    }
}
