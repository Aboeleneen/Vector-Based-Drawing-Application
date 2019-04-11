/**
 * 
 */
package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import eg.edu.alexu.csd.oop.draw.Shape;

/**
 * @author mahmo
 *
 */
public class Square extends Shapes2D {

	public Square() {
		super();
		this.getProperties().put("type", 5.0);
	}

	@Override
	public void draw(Graphics canvas) {
		SurroundingRectangle();

		Graphics2D graph = (Graphics2D) canvas;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graph.setStroke(new BasicStroke((float) (double) this.getProperties().get("thickness")));
		graph.setComposite(AlphaComposite.SrcOver.derive((float) (double) this.getProperties().get("transparent")));
		graph.setColor(this.getColor());
		graph.draw(
				new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width, this.width));

		if (this.getFillColor() != null) {
			graph.setColor(this.getFillColor());
			graph.fill(new Rectangle2D.Double(this.getPosition().getX(), this.getPosition().getY(), this.width,
					this.width));

		}

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Shape newShape = this.cloing("Square", this);
		return newShape;
	}

}
