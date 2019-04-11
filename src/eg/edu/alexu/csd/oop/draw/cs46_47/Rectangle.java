package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.border.StrokeBorder;

import eg.edu.alexu.csd.oop.draw.Shape;

public class Rectangle extends Shapes2D {

	private shapeFactory factory;

	public Rectangle() {
		super();
		factory = new shapeFactory();
		this.getProperties().put("type", 2.0);
	}

	@Override
	public void draw(Graphics canvas) {

		SurroundingRectangle();

		Graphics2D graph = (Graphics2D) canvas;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph.setStroke(new BasicStroke((float)(double)this.getProperties().get("thickness")));
		//this.setFillColor(new Color(21, 51,31,225));
		graph.setColor(this.getColor());
		graph.setComposite(AlphaComposite.SrcOver.derive((float)(double)this.getProperties().get("transparent")));
		graph.draw(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.height));

		if (this.getFillColor() != null) {
			graph.setColor(this.getFillColor());
			graph.fill(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.height));

		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newShape = factory.createShape("Rectangle");
		newShape = this.cloing("Rectangle", this);
		return newShape;
	}

}
