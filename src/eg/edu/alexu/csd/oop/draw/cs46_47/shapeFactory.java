package eg.edu.alexu.csd.oop.draw.cs46_47;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.abstractFactory;

public class shapeFactory implements abstractFactory {

	@Override
	public Shape createShape(String shapeType) {
		// TODO Auto-generated method stub
		if(shapeType == "Rectangle") return new Rectangle();
		if(shapeType == "Circle") return new Circle();
		if(shapeType == "Line") return new LineSegment();
		if(shapeType == "Square") return new Square();
		if(shapeType == "Ellipse") return new Ellipse();
		if(shapeType == "Triangle")return new Triangle();
		else {
			System.out.println(shapeType);
			return null ;
		}
	}

}
