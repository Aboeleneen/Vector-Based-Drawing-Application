package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.Shape;

public abstract class Shapes2D implements Shape {

	protected Point position;
	private Color StrokeColor;
	private Color fillColor;
	private Map<String, Double> properities ;
	private String name;
	private shapeFactory factory;

	protected double width;
	protected double height;
	
	public Shapes2D() {
		
		position = new Point(0,0);
		StrokeColor = null ;
		fillColor = null;
		properities = new HashMap<>();
		properities.put("width", 0.0);
		properities.put("Height",0.0) ;
		properities.put("x1",0.0);
		properities.put("y1",0.0);
		properities.put("x2",0.0);
		properities.put("y2",0.0);
		properities.put("thickness",2.0);
		properities.put("transparent",1.0);
		factory = new shapeFactory();
	}
	//************** position********************//
	@Override
	public void setPosition(Point position) {
		this.position = position;
	}

	@Override
	public Point getPosition() {
		return this.position;
	}


	@Override
	public void setProperties(Map<String, Double> properties) {
		 this.properities = properities;
	}

	@Override
	public Map<String, Double> getProperties() {
		return this.properities;
	}
	//**************** color of border of shapes********//
	@Override
	public void setColor(Color color) {
		this.StrokeColor = color;
	}

	@Override
	public Color getColor() {
		return this.StrokeColor;
	}

	//**************** color inside shapes********//
	@Override
	public void setFillColor(Color color) {
		this.fillColor = color;
		
	}

	@Override
	public Color getFillColor() {
		
		return this.fillColor;
	}


	abstract public Object clone() throws CloneNotSupportedException ;
	
	protected Shape cloing(String shapeType,Shape oldShape) {

		Shape newShape = factory.createShape(shapeType);
		newShape.setColor(oldShape.getColor());
		newShape.setFillColor(oldShape.getFillColor());
		newShape.setPosition(oldShape.getPosition());
		for(Map.Entry<String, Double> p : oldShape.getProperties().entrySet()) {
			newShape.getProperties().put(p.getKey(), p.getValue());
		}
		return newShape;
	}
	

	/**
	 * to determine width and height of rectangle that surround each shape
	 *  and may be used to draw some shapes
	 * @param width
	 * @param height
	 */
	protected void SurroundingRectangle() {
		this.width = this.getProperties().get("width");
		this.height = this.getProperties().get("Height");
		

	}
}
