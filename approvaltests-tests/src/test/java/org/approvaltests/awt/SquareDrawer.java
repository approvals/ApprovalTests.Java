package org.approvaltests.awt;

import com.spun.swing.Paintable;
import com.spun.util.Colors;
import org.lambda.actions.Action0;

import java.awt.*;

public class SquareDrawer implements Paintable {
    private int size;

    public Paintable setSquareSize(int size){
        this.size = size;
        return this;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(100, 100);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Colors.Purples.Thistle);
        g.fillRect(10,10,size,size);
    }

    @Override
    public void registerRepaint(Action0 repaint) {

    }
}
