package ie.tudublin;

import processing.data.TableRow;
import processing.core.PApplet;

public class Names {
    private String name;

    @Override
    public String toString() {
        return "Names [name=" + name + "]";
    }

    public Names(TableRow r)
    {
        this(r.getString("Name"));
    }

    public Names(String name) {
        this.name = name;
    }
    public void render(Audio pa)
    {
        pa.fill(255);
        pa.textSize(40);
        pa.text(name, 100, 100);
    }
}
