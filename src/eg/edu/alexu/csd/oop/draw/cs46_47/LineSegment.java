package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import eg.edu.alexu.csd.oop.draw.Shape;

public class LineSegment extends Shapes2D {

	private shapeFactory factory;

	public LineSegment() {
		super();
		factory = new shapeFactory();
		this.getProperties().put("type", 1.0);
	}

	@Override
	public void draw(Graphics canvas) {
		double startX = this.getPosition().getX() + this.getProperties().get("x1");
		double startY = this.getPosition().getY() + this.getProperties().get("y1");
		double endX = this.getPosition().getX() + this.getProperties().get("x2");
		double endY = this.getPosition().getY() + this.getProperties().get("y2");

		Graphics2D graph = (Graphics2D) canvas;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
		graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
		graph.setColor(this.getColor());
		graph.draw(new Line2D.Double(startX, startY, endX, endY));
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Shape newShape = factory.createShape("Line");
		newShape = this.cloing("Line", this);
		return newShape;
	}

}
