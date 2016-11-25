/**
 * 
 */
package affichage;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

// ******** Chart
public class Chart {
	ArrayList<Point2D.Double> vertices = null;
	Point[] edges = null;
	Color color = null;
	double width = 0;
	int connect = 0;

	Chart(ArrayList<Point2D.Double> vertices, Color color, double width, int connect) {
		this.vertices = vertices;
		this.edges = null;
		this.color = color;
		this.width = width;
		this.connect = connect;
	}
}