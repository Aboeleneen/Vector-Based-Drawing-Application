package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Triangle extends Shapes2D {

	public Triangle() {
		super();
		this.getProperties().put("type", 6.0);
	}

	@Override
	public void draw(Graphics canvas) {
		Graphics2D graph = (Graphics2D) canvas;

		double startX = this.getPosition().getX() + this.getProperties().get("x1");
		double startY = this.getPosition().getY() + this.getProperties().get("y1");
		double endX = this.getPosition().getX() + this.getProperties().get("x2");
		double endY = this.getPosition().getY() + this.getProperties().get("y2");
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int[] PointsX = new int[3];
		int[] PointsY = new int[3];
		PointsX[0] = (int) startX;
		PointsX[1] = (int) endX;
		PointsX[2] = (int) (Math.max(startX, endX) - Math.abs(startX - endX) / 2);

		PointsY[0] = (int) startY;
		PointsY[1] = (int) startY;
		PointsY[2] = (int) endY;

		graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
		graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
		graph.setColor(this.getColor());
		graph.draw(new Polygon(PointsX, PointsY, 3));

		if (this.getFillColor() != null) {
			graph.setColor(this.getFillColor());
			graph.fill(new Polygon(PointsX, PointsY, 3));
		}

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newShape = this.cloing("Triangle", this);
		return newShape;
	}

}
