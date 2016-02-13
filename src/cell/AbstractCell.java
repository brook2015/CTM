package cell;

/**
 * Created by yaokaibin on 16-2-11.
 */
public abstract class AbstractCell implements Vertex{
    private int time;

    public abstract void storage();
    public abstract void transfer();
    public void iterate() {
        time++;

    }
}
