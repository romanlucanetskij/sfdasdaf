import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimatedPanel extends JPanel implements Runnable {
  private List<MovingShape> shapes;
  private Thread animator;

  public AnimatedPanel() {
    shapes = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      shapes.add(new MovingShape());
    }
    animator = new Thread(this);
    animator.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (MovingShape shape : shapes) {
      shape.draw(g);
    }
  }

  @Override
  public void run() {
    while (true) {
      for (MovingShape shape : shapes) {
        shape.move(getWidth(), getHeight());
      }
      repaint();
      try {
        Thread.sleep(30);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  class MovingShape {
    int x, y, dx, dy, size;
    Color color;

    public MovingShape() {
      x = (int) (Math.random() * 800);
      y = (int) (Math.random() * 600);
      dx = (int) (Math.random() * 4 + 1);
      dy = (int) (Math.random() * 4 + 1);
      size = (int) (Math.random() * 20 + 10);
      color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f);
    }

    public void move(int width, int height) {
      x += dx;
      y += dy;
      if (x < 0 || x > width - size) dx = -dx;
      if (y < 0 || y > height - size) dy = -dy;
    }

    public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval(x, y, size, size);
    }
  }
}
