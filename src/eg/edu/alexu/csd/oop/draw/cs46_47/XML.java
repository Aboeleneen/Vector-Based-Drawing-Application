package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.Color;
import java.awt.Point;
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

import eg.edu.alexu.csd.oop.draw.Shape;


public class XML {
	private ArrayList<Shape> shapes;
	private Shape shape;

	public XML() {
		shapes = new ArrayList<>();
	}

	public void writeXml(String path) {
		File newXml = new File(path);
		try {
			PrintWriter saveShapes = new PrintWriter(new BufferedWriter(new FileWriter(newXml)));
			saveShapes.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			saveShapes.println("<shapes>");
			int i = 1;
			for (Shape s : shapes) {
				saveShapes.println("  <shape" + i + ">");
				if (s.getProperties() != null) {
					saveShapes.println("    <properities>");
					saveShapes.println("      <type>" + s.getProperties().get("type") + "</type>");
					saveShapes.println("      <width>" + s.getProperties().get("width") + "</width>");
					saveShapes.println("      <Height>" + s.getProperties().get("Height") + "</Height>");
					saveShapes.println("      <x1>" + s.getProperties().get("x1") + "</x1>");
					saveShapes.println("      <y1>" + s.getProperties().get("y1") + "</y1>");
					saveShapes.println("      <x2>" + s.getProperties().get("x2") + "</x2>");
					saveShapes.println("      <y2>" + s.getProperties().get("y2") + "</y2>");
					saveShapes.println("      <thickness>" + s.getProperties().get("thickness") + "</thickness>");
					saveShapes.println("      <transparent>" + s.getProperties().get("transparent") + "</transparent>");
					saveShapes.println("    </properities");
				}else if(s.getProperties()==null) {
					saveShapes.println("    <type>20.0</type>");
				}
				if (s.getPosition() != null) {
					saveShapes.println(
							"    <position>" + s.getPosition().getX() + "," + s.getPosition().getY() + "</position>");
				}
				if (s.getFillColor() != null)
					saveShapes.println("    <fillColor>" + s.getFillColor().toString() + "</fillColor>");
				if (s.getColor() != null)
					saveShapes.println("    <strokeColor>" + s.getColor().toString() + "</strokeColor>");
				saveShapes.println("  </shape" + i + ">");
				i++;
			}
			saveShapes.println("</shapes>");
			saveShapes.flush();
			saveShapes.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void readXml(String path) throws IOException {
		shapes.clear();
		File fileXml = new File(path);
		try {
			BufferedReader XmlReader = new BufferedReader(new FileReader(fileXml));
			String line = XmlReader.readLine();
			Pattern p1 = Pattern.compile("(?<=<)\\/?\\w+(?=>)");
			Pattern p2 = Pattern.compile("(?<=<\\w{1,15}>)\\d+\\.\\d+(?=<\\/\\w+>)");
			Pattern p3 = Pattern.compile("(?<=<position>)\\d+\\.\\d+(?=,)");
			Pattern p4 = Pattern.compile("(?<=,)\\d+\\.\\d+(?=<\\/position>)");
			Pattern p5 = Pattern.compile("(?<=\\=)\\d{1,3}");
			Matcher m1;// pattern for tags
			Matcher m2;// pattern for properities
			Matcher m3;// pattern for position x
			Matcher m4;// pattern for position y
			Matcher m5;// pattern for colors
			boolean flagStart = false;
			boolean flagShape = false;
			int i = 1;
			while (line != null) {
				m1 = p1.matcher(line);

				if (m1.find()) {
					String tag = m1.group();
					if (tag.equals("shapes")) {
						flagStart = true;
						line = XmlReader.readLine();
						continue;
					}
					if (flagStart == true) {
						if (tag.equals("/shapes")) {
							flagStart = false;
							line = XmlReader.readLine();
							break;
						}
						if (tag.equals("shape" + i)) {
							flagShape = true;
							line = XmlReader.readLine();
							continue;
						}
						if (tag.equals("/shape" + i)) {
							flagShape = false;
							i++;
							shapes.add(shape);
							line = XmlReader.readLine();
							continue;
						}
						if (flagShape == true) {
							m2 = p2.matcher(line);
							m3 = p3.matcher(line);
							m4 = p4.matcher(line);
							m5 = p5.matcher(line);

							/**
							 * all properities
							 */
							if (m2.find()) {
								String value = m2.group();
								double Dvalue = Double.parseDouble(value);
								if (tag.equals("type")) {
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
									
									shape.getProperties().put("type", Dvalue);

								} else if (tag.equals("width"))
									shape.getProperties().put("width", Dvalue);
								else if (tag.equals("Height"))
									shape.getProperties().put("Height", Dvalue);
								else if (tag.equals("x1"))
									shape.getProperties().put("x1", Dvalue);
								else if (tag.equals("y1"))
									shape.getProperties().put("y1", Dvalue);
								else if (tag.equals("x2"))
									shape.getProperties().put("x2", Dvalue);
								else if (tag.equals("y2"))
									shape.getProperties().put("y2", Dvalue);
								else if (tag.equals("thickness"))
									shape.getProperties().put("thickness", Dvalue);
								else if (tag.equals("transparent"))
									shape.getProperties().put("transparent", Dvalue);

							}
							if (m3.find() && m4.find()) {
								double x = Double.parseDouble(m3.group());
								double y = Double.parseDouble(m4.group());
								shape.setPosition(new Point((int) x, (int) y));
							}
							int r = 0, g = 0, b = 0;
							int j = 1;
							while (m5.find()) {
								if (j == 1)
									r = Integer.parseInt(m5.group());
								else if (j == 2)
									g = Integer.parseInt(m5.group());
								else if (j == 3)
									b = Integer.parseInt(m5.group());
								j++;
							}
							if (tag.equals("strokeColor"))
								shape.setColor(new Color(r, g, b));
							else if (tag.equals("fillColor"))
								shape.setFillColor(new Color(r, g, b));
						}
					}

				}
				line = XmlReader.readLine();

			}
			XmlReader.close();

		} catch (FileNotFoundException e) {
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
