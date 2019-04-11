package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.draw.RoundRectangle;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.test.DummyShape;

public class JSON {
	private ArrayList<Shape> shapes;
	private Shape shape;

	public JSON() {
		shapes = new ArrayList<>();
		shape = null;
	}

	public void writeJson(String path) {
		File fileJson = new File(path);
		try {
			PrintWriter JsonWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileJson)));
			JsonWriter.println("{");
			JsonWriter.println("  \"shapes\": [");
			for (Shape s : shapes) {
				JsonWriter.println("    {");
				if (s.getClass().getName().equals("eg.edu.alexu.csd.oop.draw.RoundRectangle")) {
					s.getProperties().put("type", 7.0);
					s.getProperties().put("width", s.getProperties().get("Width"));
					s.getProperties().put("Height", s.getProperties().get("Length"));
					s.getProperties().put("x1", s.getProperties().get("ArcWidth"));
					s.getProperties().put("y1", s.getProperties().get("ArcLength"));
				}
				if (s.getProperties() != null) {
					JsonWriter.println("       \"type\": " + s.getProperties().get("type") + ",");
					JsonWriter.println("       \"width\": " + s.getProperties().get("width") + ",");
					JsonWriter.println("       \"Height\": " + s.getProperties().get("Height") + ",");
					JsonWriter.println("       \"X\": " + s.getProperties().get("x1") + ",");
					JsonWriter.println("       \"x\": " + s.getProperties().get("x2") + ",");
					JsonWriter.println("       \"Y\": " + s.getProperties().get("y1") + ",");
					JsonWriter.println("       \"y\": " + s.getProperties().get("y2") + ",");
					JsonWriter.println("       \"thickness\": " + s.getProperties().get("thickness") + ",");
					JsonWriter.println("       \"transparent\": " + s.getProperties().get("transparent") + ",");
				} else if (s.getProperties() == null) {
					JsonWriter.println("       \"type\":-1,");// dummy shape
				}
				if (s.getPosition() != null) {
					JsonWriter.println("       \"positionX\": " + s.getPosition().getX() + ",");
					JsonWriter.println("       \"positionY\": " + s.getPosition().getY() + ",");
				}
				if (s.getColor() != null) {
					JsonWriter.println("       \"strokeColor\": " + s.getColor().getRGB() + ",");
				}
				if (s.getFillColor() != null)
					JsonWriter.println("       \"fillColor\": " + s.getFillColor().getRGB());
				if (!s.equals(shapes.get(shapes.size() - 1)))
					JsonWriter.println("    },");
				else
					JsonWriter.println("    }");
			}
			JsonWriter.println("  ]");
			JsonWriter.println("}");
			JsonWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readJson(String path) throws IOException {
		shapes.clear();
		shape = null;
		File fileJson = new File(path);
		try {
			BufferedReader JsonReader = new BufferedReader(new FileReader(fileJson));
			Pattern namePattern = Pattern.compile("(?<=\\\")\\w+(?=\\\"\\s?:)");
			Pattern valuePattern = Pattern.compile("(?<=:\\s{0,5})\\d+\\.\\d+|-?\\d+");
			Matcher nameMatch;
			Matcher valueMatch;
			String line = JsonReader.readLine();
			boolean flagStart = false;
			boolean flagShape = false;
			while (line != null) {
				nameMatch = namePattern.matcher(line);
				valueMatch = valuePattern.matcher(line);
				if (nameMatch.find()) {
					String name = nameMatch.group();
					if (name.equals("shapes")) {
						flagStart = true;
						line = JsonReader.readLine();
						continue;
					}
					if (flagStart == true && valueMatch.find()) {
						String value = valueMatch.group();
						double Dvalue = Double.parseDouble(value);
						if (name.equals("type")) {
							if (shape != null) {
								shapes.add(shape);
							}
							if (value.equals("1.0"))
								shape = new LineSegment();
							else if (value.equals("2.0"))
								shape = new Rectangle();
							else if (value.equals("3.0"))
								shape = new Ellipse();
							else if (value.equals("4.0"))
								shape = new Circle();
							else if (value.equals("5.0"))
								shape = new Square();
							else if (value.equals("6.0"))
								shape = new Triangle();
							else if (value.equals("-1")) {
								shape = new DummyShape();
								line = JsonReader.readLine();
								continue;
							} else if (value.equals("7.0")) {
								shape = new RoundRectangle();
							}
							shape.getProperties().put("type", Dvalue);
						} else if (name.equals("width")) {
							shape.getProperties().put("width", Dvalue);
						} else if (name.equals("Height")) {
							shape.getProperties().put("Height", Dvalue);
						} else if (name.equals("X")) {
							shape.getProperties().put("x1", Dvalue);
						} else if (name.equals("Y")) {
							shape.getProperties().put("y1", Dvalue);
						} else if (name.equals("x")) {
							shape.getProperties().put("x2", Dvalue);
						} else if (name.equals("y")) {
							shape.getProperties().put("y2", Dvalue);
						} else if (name.equals("thickness")) {
							shape.getProperties().put("thickness", Dvalue);
						} else if (name.equals("transparent")) {
							shape.getProperties().put("transparent", Dvalue);
						} else if (name.equals("positionX")) {
							shape.getPosition().x = (int) Dvalue;
						} else if (name.equals("positionY")) {
							shape.getPosition().y = (int) Dvalue;
						} else if (name.equals("strokeColor")) {
							shape.setColor(new Color((int) Dvalue));
						} else if (name.equals("fillColor")) {
							shape.setFillColor(new Color((int) Dvalue));
						}
					}
				}
				line = JsonReader.readLine();
			}
			if (shape != null) {
				shapes.add(shape);
				shape = null;
			}
			JsonReader.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
}
