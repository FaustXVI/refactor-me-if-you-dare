/**
 * 
 */
package affichage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;

import javax.swing.JPanel;

//*********** ChartPanel
public class ChartPanel extends JPanel {

	private boolean drawString;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7554793651258624517L;
	private Hashtable<String, Chart> charts = null;
	private Point2D.Double center = new Point2D.Double(0, 0);
	 Point2D.Double mouse = new Point2D.Double(0, 0);
	 Point2D.Double scale = new Point2D.Double(100, 100);
	private DecimalFormat formatter = (DecimalFormat) NumberFormat
			.getInstance(Locale.US);
	private Color background = Color.WHITE;
	private boolean logX = false;
	private boolean logY = false;
	private boolean axisX = true;
	private boolean axisY = true;

	ChartPanel(Hashtable<String, Chart> charts) {
		this.charts = charts;
		drawString = false;
		ChartListener cl = new ChartListener(this);
		addMouseListener(cl);
		addMouseMotionListener(cl);
		addMouseWheelListener(cl);
	}

	@Override
	public void paintComponent(Graphics g) {
		Image buffer = createImage(getWidth(), getHeight());
		Graphics2D g2D = (Graphics2D) buffer.getGraphics();
		g2D.setBackground(background);
		g2D.clearRect(0, 0, getWidth(), getHeight());

		Enumeration<String> e = charts.keys();
		while (e.hasMoreElements())
			drawChart((Chart) charts.get(e.nextElement()), g2D);
		drawFramework(g2D);
		g.drawImage(buffer, 0, 0, this);
	}

	void drawFramework(Graphics g) {
		if (axisX)
			drawAxisX(g);
		if (axisY)
			drawAxisY(g);

		int n = 0;
		Enumeration<String> e = charts.keys();
		while (e.hasMoreElements()) {
			n++;
			String key = (String) e.nextElement();
			g.setColor(charts.get(key).color);
			g.drawString(key, 10, 20 * n);
		}

		g.setColor(Color.BLACK);
		if (drawString) {
			g.drawString("mouse : (" + format(mouse.x, 2) + ","
					+ format(mouse.y, 2) + ")", 10, getHeight() - 50);
			g.drawString("center : (" + format(center.x, 2) + ","
					+ format(center.y, 2) + ")", 10, getHeight() - 30);
			String s = "scale : (" + format(scale.x, 2);
			if (logX)
				s += "[log]";
			s += "," + format(scale.y, 2);
			if (logY)
				s += "[log]";
			s += ")";
			g.drawString(s, 10, getHeight() - 10);
		}
	}

	Point2D.Double getOrigin() {
		double x0, y0;
		if (!logX)
			x0 = 0;
		else
			x0 = 1;
		if (!logY)
			y0 = 0;
		else
			y0 = 1;
		return new Point2D.Double(x0, y0);
	}

	boolean markX(int n, double unit, Graphics g) {
		double x;
		if (!logX)
			x = n * unit;
		else
			x = Math.pow(10, n) * unit;
		Point p = toPixel(new Point2D.Double(x, getOrigin().y));
		if (p != null) {
			g.drawLine(p.x, p.y - 6, p.x, p.y + 6);
			String s = format(x, 1);
			if (n == 1 || n == -1 || (n % 10 == 0))
				g.drawString(s + "", p.x + 5 - 7 * s.length(), p.y + 24);
			if (n > 0)
				return (p.x < getWidth());
			else
				return (p.x > 0);
		}
		return false;
	}

	boolean markY(int n, double unit, Graphics g) {
		double y;
		if (!logY)
			y = n * unit;
		else
			y = Math.pow(10, n) * unit;
		Point p = toPixel(new Point2D.Double(getOrigin().x, y));
		if (p != null) {
			g.drawLine(p.x - 6, p.y, p.x + 6, p.y);
			String s = format(y, 1);
			if (n == 1 || n == -1 || (n % 10 == 0))
				g.drawString(s + "", p.x - 10 - 7 * s.length(), p.y + 4);
			if (n > 0)
				return (p.y > 0);
			else
				return (p.y < getHeight());
		}
		return false;
	}

	void drawAxisX(Graphics g) {
		((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(Color.BLACK);
		Point p = toPixel(getOrigin());
		g.drawLine(0, p.y, getWidth(), p.y);
		double unit = Math.pow(10, Math.floor(Math.log10(100 / scale.x)));
		int n = 0;
		if (axisY)
			n = 1;
		while (markX(n, unit, g))
			n++;
		n = -1;
		while (markX(n, unit, g))
			n--;
	}

	void drawAxisY(Graphics g) {
		((Graphics2D) g).setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND));
		g.setColor(Color.BLACK);
		Point p = toPixel(getOrigin());
		g.drawLine(p.x, 0, p.x, getHeight());
		double unit = Math.pow(10, Math.floor(Math.log10(100 / scale.y)));
		int n = 0;
		if (axisX)
			n = 1;
		while (markY(n, unit, g))
			n++;
		n = -1;
		while (markY(n, unit, g))
			n--;
	}

	Point toPixel(Point2D.Double p) {
		if (p == null)
			return null;
		int px;
		int py;
		if (!logX)
			px = (int) (getWidth() / 2 + scale.x * (p.x - center.x));
		else {
			if (p.x <= 0)
				return null;
			px = (int) (getWidth() / 2 + scale.x
					* (Math.log(p.x) - Math.log(center.x)));
		}
		if (!logY)
			py = (int) (getHeight() / 2 - scale.y * (p.y - center.y));
		else {
			if (p.y <= 0)
				return null;
			py = (int) (getHeight() / 2 - scale.y
					* (Math.log(p.y) - Math.log(center.y)));
		}
		return new Point(px, py);
	}

	Point.Double toCoord(Point p) {
		double px;
		double py;
		if (!logX)
			px = center.x + (p.x - getWidth() / 2) / scale.x;
		else
			px = Math
					.exp(Math.log(center.x) + (p.x - getWidth() / 2) / scale.x);
		if (!logY)
			py = center.y - (p.y - getHeight() / 2) / scale.y;
		else
			py = Math.exp(Math.log(center.y) - (p.y - getHeight() / 2)
					/ scale.y);
		return new Point.Double(px, py);
	}

	void drawConnection(Chart chart, Graphics g, int i, int j) {
		Point p1 = null;
		Point p2 = null;
		if ((i >= 0) && (i < chart.vertices.size()))
			p1 = toPixel(chart.vertices.get(i));
		if ((j >= 0) && (j < chart.vertices.size()))
			p2 = toPixel(chart.vertices.get(j));
		if (p1 == null && p2 == null)
			return;
		else if (p1 != null && p2 == null)
			g.drawLine(p1.x, p1.y, p1.x, p1.y);
		else if (p1 == null && p2 != null)
			g.drawLine(p2.x, p2.y, p2.x, p2.y);
		else
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	void drawChart(Chart chart, Graphics g) {
		if (chart.vertices == null)
			return;
		g.setColor(chart.color);
		((Graphics2D) g).setStroke(new BasicStroke((float) chart.width,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		for (int i = 0; i < chart.vertices.size(); i++) {
			drawConnection(chart, g, i, i + 1);
		}
	} // switch

	String format(double x, int type) {
		if (Math.abs(x) > 0 && (Math.abs(x) < 0.0001 || Math.abs(x) > 10000))
			formatter.applyPattern("0E0");
		else
			switch (type) {
			case 1:
				formatter.applyPattern("#0.######################");
				break;
			case 2:
				formatter.applyPattern("#0.0000");
				;
				break;
			}
		return formatter.format(x);
	}

	/**
	 * @return the drawString
	 */
	public boolean isDrawString() {
		return drawString;
	}

	/**
	 * @param drawString
	 *            the drawString to set
	 */
	public void setDrawString(boolean drawString) {
		this.drawString = drawString;
	}
	
	public void add(String key, ArrayList<Point2D.Double> vertices) {
		charts.put(key, new Chart(vertices, Color.BLACK, 1, 1));
	}
	
	public void color(String key, Color color) {
		Chart chart = charts.get(key);
		if (chart != null)
			chart.color = color;
	}
	
	public void remove(String key) {
		charts.remove(key);
	}
	
	public void background(Color color) {
		background = color;
	}

	public void axis(boolean axisX, boolean axisY) {
		this.axisX = axisX;
		this.axisY = axisY;
	}

	public void log(boolean logX, boolean logY) {
		this.logX = logX;
		this.logY = logY;
		if (center.x <= 0)
			center.x = 1;
		if (center.y <= 0)
			center.y = 1;
	}

	public void scale(double scaleX, double scaleY) {
		scale.x = scaleX;
		scale.y = scaleY;
	}

	public void center(double centerX, double centerY) {
		center.x = centerX;
		center.y = centerY;
	}
	
	public void width(String key, double width) {
		Chart chart = charts.get(key);
		if (chart != null)
			chart.width = width;
	}

	public void connect(String key, int connect) {
		Chart chart = charts.get(key);
		if (chart != null)
			chart.connect = connect;
	}

	public void edges(String key, Point[] edges) {
		Chart chart = charts.get(key);
		if (chart != null)
			chart.edges = edges;
	}
}