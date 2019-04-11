package eg.edu.alexu.csd.oop.draw.cs46_47;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import eg.edu.alexu.csd.oop.draw.DrawingEngine;
import eg.edu.alexu.csd.oop.draw.Shape;

public class PaintEngine implements DrawingEngine {

	public ArrayList<Shape> shapes;
	private Stack<Entry<Shape, String>> undoStack;
	private Stack<Entry<Shape, String>> redoStack;
	private List<Class<? extends Shape>> shapesList;
	private XML XmlFile;
	private JSON JsonFile;
	private int undoTimes, redoTimes;
	private Shape UpdatedShape;

	public Stack<Entry<Shape, String>> getUndoStack() {
		return undoStack;
	}

	public void setUndoStack(Stack<Entry<Shape, String>> undoStack) {
		this.undoStack = undoStack;
	}

	public Stack<Entry<Shape, String>> getRedoStack() {
		return redoStack;
	}

	public void setRedoStack(Stack<Entry<Shape, String>> redoStack) {
		this.redoStack = redoStack;
	}

	public int getUndoTimes() {
		return undoTimes;
	}

	public void setUndoTimes(int undoTimes) {
		this.undoTimes = undoTimes;
	}

	public int getRedoTimes() {
		return redoTimes;
	}

	public void setRedoTimes(int redoTimes) {
		this.redoTimes = redoTimes;
	}

	public PaintEngine() {
		shapes = new ArrayList<>();
		undoStack = new Stack<>();
		redoStack = new Stack<>();
		shapesList = new ArrayList<>();
		shapesList.add(Circle.class);
		shapesList.add(Ellipse.class);
		shapesList.add(LineSegment.class);
		shapesList.add(Rectangle.class);
		shapesList.add(Square.class);
		shapesList.add(Triangle.class);
		XmlFile = new XML();
		JsonFile = new JSON();
		undoTimes = 0;
		redoTimes = 0;
		UpdatedShape = null;

		installPluginShape("src\\RoundRectangle.jar");
	}

	public Shape getUpdatedShape() {
		return UpdatedShape;
	}

	public void setUpdatedShape(Shape updatedShape) {
		UpdatedShape = updatedShape;
	}

	@Override
	public void refresh(Graphics canvas) {
		// TODO Auto-generated method stub
		shapes.forEach(shape -> {
			if (!shape.equals(UpdatedShape))
				shape.draw(canvas);
		});
	}

	@Override
	public void addShape(Shape shape) {
		shapes.add(shape);
		if (undoTimes == 20)
			undoTimes--;
		redoTimes = 0;
		redoStack.clear();
		undoStack.add(createEntry(shape, "add"));
	}

	@Override
	public void removeShape(Shape shape) {
		shapes.remove(shape);
		undoTimes = 0;
		redoTimes = 0;
		redoStack.clear();
		undoStack.add(createEntry(shape, "remove"));

	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i).equals(oldShape)) {
				shapes.remove(oldShape);
				shapes.add(i, newShape);
				break;
			}
		}
		undoStack.add(createEntry(oldShape, "oldVersion"));
		undoStack.add(createEntry(newShape, "newVersion"));

	}

	@Override
	public Shape[] getShapes() {
		// TODO Auto-generated method stub
		int numOfShapes = this.getNumShapes();
		Shape[] arrShapes = new Shape[numOfShapes];
		for (int i = 0; i < numOfShapes; i++) {
			arrShapes[i] = shapes.get(i);
		}
		return arrShapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		return shapesList;
	}

	@Override
	public void undo() {
		if (!undoStack.isEmpty() && undoTimes < 20) {
			undoTimes++;
			String action = undoStack.peek().getValue();
			Shape shape = undoStack.peek().getKey();
			undoStack.pop();
			if (action.equals("add")) {
				this.shapes.remove(shape);
				redoStack.add(createEntry(shape, "remove"));
			} else if (action.equals("remove")) {
				this.shapes.add(shape);
				redoStack.add(createEntry(shape, "add"));
			} else if (action.equals("newVersion")) {
				Shape oldShape = undoStack.peek().getKey();
				undoStack.pop();
				for (int i = 0; i < shapes.size(); i++) {
					if (shapes.get(i).equals(shape)) {
						shapes.remove(shape);
						shapes.add(i, oldShape);
						break;
					}
				}
				redoStack.add(createEntry(shape, "newVerion"));
				redoStack.add(createEntry(oldShape, "oldVersion"));
			}
		}

	}

	@Override
	public void redo() {
		if (!redoStack.isEmpty() && redoTimes < 20) {
			redoTimes++;
			undoTimes--;
			String action = redoStack.peek().getValue();
			Shape shape = redoStack.peek().getKey();
			redoStack.pop();
			if (action.equals("add")) {
				this.shapes.remove(shape);
				undoStack.add(createEntry(shape, "remove"));
			} else if (action.equals("remove")) {
				this.shapes.add(shape);
				undoStack.add(createEntry(shape, "add"));
			} else if (action.equals("oldVersion")) {
				Shape newShape = redoStack.peek().getKey();
				redoStack.pop();
				for (int i = 0; i < shapes.size(); i++) {
					if (shapes.get(i).equals(shape)) {
						shapes.remove(shape);
						shapes.add(i, newShape);
						break;
					}
				}
				undoStack.add(createEntry(shape, "oldVersion"));
				undoStack.add(createEntry(newShape, "newVersion"));

			}
		}
	}

	@Override
	public void save(String path) {
		if (path.matches(".*\\.[Xx][Mm][Ll]")) {
			XmlFile.setShapes(shapes);
			XmlFile.writeXml(path);
		} else if (path.matches(".*\\.[Jj][Ss][Oo][Nn]")) {
			JsonFile.setShapes(shapes);
			JsonFile.writeJson(path);
		}

	}

	@Override
	public void load(String path) {
		try {
			if (path.matches(".*\\.[Xx][Mm][Ll]")) {
				XmlFile.readXml(path);
				this.shapes = XmlFile.getShapes();
			} else if (path.matches(".*\\.[Jj][Ss][Oo][Nn]")) {
				JsonFile.readJson(path);
				shapes = JsonFile.getShapes();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * @param Shape and String
	 * 
	 * @return pair of Shape and String
	 */
	private Entry<Shape, String> createEntry(Shape shape, String string) {
		// TODO Auto-generated method stub
		Entry<Shape, String> pair = new SimpleEntry<>(shape, string);
		return pair;
	}

	/*
	 * @return num of shapes in the ArrayList Shape
	 */
	private int getNumShapes() {
		return this.shapes.size();
	}

	@Override
	public void installPluginShape(String jarPath) {
		// TODO Auto-generated method stub
		try {
			JarFile jar = new JarFile(jarPath);
			Enumeration<JarEntry> e = jar.entries();
			URL[] urls = { new URL("jar:file:" + jarPath + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);
			while (e.hasMoreElements()) {
				JarEntry je = e.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class")) {
					continue;
				}
				String className = je.getName().substring(0, je.getName().length() - 6);
				className = className.replace('/', '.');
				Class c = cl.loadClass(className);
				if (c.newInstance() instanceof Shape)
					shapesList.add(c);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
