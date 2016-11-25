/**
 * 
 */
package affichage;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

// ********* ChartListener
public class ChartListener implements MouseListener, MouseMotionListener,
		MouseWheelListener {
	ChartPanel panel;
	int x;
	int y;
	boolean leftButton = false;
	boolean rightButton = false;

	ChartListener(ChartPanel panel) {
		this.panel = panel;
	}

	// MouseListener
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftButton = true;
		else if (e.getButton() == MouseEvent.BUTTON3)
			rightButton = true;
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1)
			leftButton = false;
		else if (e.getButton() == MouseEvent.BUTTON3)
			rightButton = false;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	// MouseMotionListener
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		Point2D.Double p = panel.toCoord(new Point(x, y));
		panel.mouse.setLocation(p.x, p.y);
		panel.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		int diffx = e.getX() - x;
		int diffy = e.getY() - y;
		if (leftButton) {
			Point2D.Double p = panel.toCoord(new Point(panel.getWidth() / 2
					- diffx, panel.getHeight() / 2 - diffy));
			panel.center(p.x, p.y);

		}
		if (rightButton) {
			Point2D.Double p = panel.toCoord(new Point(e.getX(), e.getY()));
			panel.mouse.setLocation(p.x, p.y);
			int cx, cy;
			if (p.x < panel.getOrigin().x)
				cx = -1;
			else
				cx = 1;
			if (p.y < panel.getOrigin().y)
				cy = -1;
			else
				cy = 1;
			panel.scale.x *= (1 + cx * diffx / 500.0);
			panel.scale.y *= (1 - cy * diffy / 500.0);
		}
		x = e.getX();
		y = e.getY();
		panel.repaint();
	}

	// MouseWheelListener
	public void mouseWheelMoved(MouseWheelEvent e) {
		double sc = e.getWheelRotation() / 10.0;
		panel.scale.x *= (1 - sc);
		panel.scale.y *= (1 - sc);
		Point2D.Double p = panel.toCoord(new Point(x, y));
		panel.mouse.setLocation(p.x, p.y);
		panel.repaint();
	}
}
