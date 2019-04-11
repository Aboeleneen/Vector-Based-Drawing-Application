package GUI;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs46_47.Circle;
import eg.edu.alexu.csd.oop.draw.cs46_47.Ellipse;
import eg.edu.alexu.csd.oop.draw.cs46_47.LineSegment;
import eg.edu.alexu.csd.oop.draw.cs46_47.PaintEngine;
import eg.edu.alexu.csd.oop.draw.cs46_47.Rectangle;
import eg.edu.alexu.csd.oop.draw.cs46_47.Square;
import eg.edu.alexu.csd.oop.draw.cs46_47.Triangle;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Point;
import java.awt.Stroke;

import javax.swing.JButton;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.awt.event.ActionEvent;

public class Board extends JPanel {

	private Point drawStart, drawEnd;
	private Shape shape;
	private PaintEngine paintDemo;
	private int currentAction;

	private Color fillColor, strokeColor;
	private boolean Fill;

	private Shape currentShape;
	private Shape updatedShape;

	private int thick;
	private double transparent;

	private int differenceInUpperX;
	private int differenceInUpperY;

	private PaintApp parentFrame;
	private boolean currentShapeDragged;
	private String resize;
	private double differenceInX;
	private double differenceInY;
	private boolean resizeFlag;

	private double oldWidthLeft;
	private double positionXleft;
	private double oldHeightUp;
	private double positionYup;

	public Board(PaintEngine paint, PaintApp frame) {
		this.Fill = false;
		this.currentAction = 0;
		this.strokeColor = Color.BLACK;
		this.fillColor = Color.RED;
		paintDemo = paint;
		shape = null;
		currentShapeDragged = false;
		currentShape = null;
		updatedShape = null;
		differenceInUpperX = 0;
		differenceInUpperY = 0;
		differenceInX = 0;
		differenceInY = 0;
		parentFrame = frame;
		resize = null;
		resizeFlag = false;
		transparent = 0.99;
		thick = 2;
		oldWidthLeft = 0;
		positionXleft = 0;
		oldHeightUp = 0;
		positionYup = 0;
		this.setBackground(Color.WHITE);

		this.addMouseListener(new MouseAdapter() {
			// start
			public void mouseClicked(MouseEvent e) {

				Collections.reverse(paintDemo.shapes);
				Boolean flag = false;
				for (Shape s : paintDemo.shapes) {
					double x1 = s.getPosition().getX();
					double y1 = s.getPosition().getY();
					double x2 = s.getPosition().getX() + s.getProperties().get("width");
					double y2 = s.getPosition().getY() + s.getProperties().get("Height");
					double x = e.getX();
					double y = e.getY();

					if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {

						try {
							currentShape = (Shape) s.clone();
							updatedShape = s;
							paintDemo.setUpdatedShape(s);
							parentFrame.getCopyButton().setEnabled(true);
							parentFrame.getRemoveButton().setEnabled(true);
						} catch (CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Collections.reverse(paintDemo.shapes);
						repaint();
						flag = true;
						break;
					}
				}
				if (!flag) {
					Collections.reverse(paintDemo.shapes);
					currentShape = null;
					updatedShape = null;
					parentFrame.getCopyButton().setEnabled(false);
					parentFrame.getRemoveButton().setEnabled(false);
					repaint();
				} else if (parentFrame.IsFillShpe()) {
					currentShape.setFillColor(fillColor);
					paintDemo.updateShape(updatedShape, currentShape);
					currentAction = 0;
					currentShape = null;
					updatedShape = null;
					parentFrame.getCopyButton().setEnabled(false);
					parentFrame.getRemoveButton().setEnabled(false);
					paintDemo.setUpdatedShape(null);
					parentFrame.setFillShape(false);
					repaint();
				}

			}
			// end

			public void mousePressed(MouseEvent e) {
				if (currentAction != 0) {

					if (currentAction == 2) {
						shape = new LineSegment();
					} else if (currentAction == 1) {
						shape = new Rectangle();
					} else if (currentAction == 3) {
						shape = new Ellipse();
					} else if (currentAction == 4) {
						shape = new Circle();
					} else if (currentAction == 5) {
						shape = new Triangle();
					} else if (currentAction == 6) {
						shape = new Square();
					}

					drawStart = new Point(e.getX(), e.getY());
					drawEnd = drawStart;
					shape.setPosition(drawStart);
					shape.setColor(strokeColor);
					shape.getProperties().put("thickness", (double) thick);
					shape.getProperties().put("transparent", transparent);
					repaint();
				} else if (currentShape != null) {
					// Boolean flag = false;

					double x1 = currentShape.getPosition().getX();
					double y1 = currentShape.getPosition().getY();
					double x2 = currentShape.getPosition().getX() + currentShape.getProperties().get("width");
					double y2 = currentShape.getPosition().getY() + currentShape.getProperties().get("Height");
					double x = e.getX();
					double y = e.getY();
					if (!(x >= x1 && x <= x2 && y >= y1 && y <= y2) && !resizeFlag) {
						currentShape = null;
						updatedShape = null;
						paintDemo.setUpdatedShape(null);

					}
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (currentAction != 0) {
					drawEnd = new Point(e.getX(), e.getY());
					makeProperShape();
					if (shape != null)
						paintDemo.addShape(shape);
					shape = null;
					drawStart = null;
					drawEnd = null;
					repaint();
					currentAction = 0;
				} else if (currentShape != null && currentShapeDragged == true) {
					/*
					 * System.out.println(currentShape.getPosition());
					 * System.out.println(updatedShape.getPosition());
					 */
					paintDemo.updateShape(updatedShape, currentShape);

					currentShape = null;
					updatedShape = null;
					paintDemo.setUpdatedShape(null);
					parentFrame.getCopyButton().setEnabled(false);
					parentFrame.getRemoveButton().setEnabled(false);
					currentShapeDragged = false;
					differenceInUpperY = 0;
					differenceInUpperX = 0;
					differenceInX = 0;
					differenceInY = 0;

					oldWidthLeft = 0;
					positionXleft = 0;
					oldHeightUp = 0;
					positionYup = 0;
					repaint();

				}

			}

		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {

				if (currentAction != 0) {

					drawEnd = new Point(e.getX(), e.getY());
					makeProperShape();
					repaint();
				} else if (currentShape != null && !resizeFlag) {
					currentShapeDragged = true;
					if (differenceInUpperX == 0) {
						differenceInUpperX = (int) (e.getX() - currentShape.getPosition().x);
						differenceInUpperY = (int) (e.getY() - currentShape.getPosition().y);
					}
					currentShape.setPosition(new Point(e.getX() - differenceInUpperX, e.getY() - differenceInUpperY));
					repaint();
				}

				if (resizeFlag) {
					currentShapeDragged = true;
					int type = (int) (double) currentShape.getProperties().get("type");
					if (resize.equals("right")) {
						if (type == 2 || type == 3) {
							currentShape.getProperties().put("width",
									Math.abs(currentShape.getPosition().getX() - e.getX()));
						} else if (type == 4 || type == 5) {
							currentShape.getProperties().put("width",
									Math.abs(currentShape.getPosition().getX() - e.getX()));
							currentShape.getProperties().put("Height",
									Math.abs(currentShape.getPosition().getX() - e.getX()));
						} else if (type == 1 || type == 6) {
							if (currentShape.getProperties().get("x1") != 0) {
								currentShape.getProperties().put("width",
										Math.abs(currentShape.getPosition().getX() - e.getX()));
								currentShape.getProperties().put("x1",
										Math.abs(currentShape.getPosition().getX() - e.getX()));
							}
							if (currentShape.getProperties().get("x2") != 0) {
								currentShape.getProperties().put("width",
										Math.abs(currentShape.getPosition().getX() - e.getX()));
								currentShape.getProperties().put("x2",
										Math.abs(currentShape.getPosition().getX() - e.getX()));
							}
						}

					} else if (resize.equals("left")) {
						if (oldWidthLeft == 0 && positionXleft == 0) {
							oldWidthLeft = currentShape.getProperties().get("width");
							positionXleft = currentShape.getPosition().getX();
						}
						if (type == 2 || type == 3 || type == 4 || type == 5) {
							// currentShape.setPosition(new
							// Point((int)e.getX(),(int)currentShape.getPosition().getY()));
							currentShape.getPosition().x = e.getX();
							currentShape.getProperties().put("width", oldWidthLeft + (positionXleft - e.getX()));
							if (type == 4 || type == 5) {
								currentShape.getProperties().put("Height", oldWidthLeft + (positionXleft - e.getX()));
							}
						} else if (type == 1 || type == 6) {
							currentShape.getPosition().x = e.getX();
							if (currentShape.getProperties().get("x1") != 0) {
								currentShape.getProperties().put("x1", oldWidthLeft + (positionXleft - e.getX()));

							}
							if (currentShape.getProperties().get("x2") != 0) {
								currentShape.getProperties().put("x2", oldWidthLeft + (positionXleft - e.getX()));
							}
							currentShape.getProperties().put("width", oldWidthLeft + (positionXleft - e.getX()));
						}

					} else if (resize.equals("down")) {
						if (type == 2 || type == 3 || type == 4 || type == 5) {
							currentShape.getProperties().put("Height",
									Math.abs(e.getY() - currentShape.getPosition().getY()));
							if (type == 4 || type == 5) {
								currentShape.getProperties().put("width",
										Math.abs(e.getY() - currentShape.getPosition().getY()));
							}
						} else if (type == 1 || type == 6) {
							currentShape.getProperties().put("Height",
									Math.abs(e.getY() - currentShape.getPosition().getY()));
							if (currentShape.getProperties().get("y1") != 0) {
								currentShape.getProperties().put("y1",
										Math.abs(Math.abs(e.getY() - currentShape.getPosition().getY())));
							}
							if (currentShape.getProperties().get("y2") != 0) {
								currentShape.getProperties().put("y2",
										Math.abs(Math.abs(e.getY() - currentShape.getPosition().getY())));
							}
						}

					} else if (resize.equals("up")) {
						if (oldHeightUp == 0 && positionYup == 0) {
							oldHeightUp = currentShape.getProperties().get("Height");
							positionYup = currentShape.getPosition().getY();
						}
						if (type == 2 || type == 3 || type == 4 || type == 5) {
							currentShape.getPosition().y = e.getY();
							currentShape.getProperties().put("Height", oldHeightUp + (positionYup - e.getY()));
							if (type == 4 || type == 5) {
								currentShape.getProperties().put("width", oldHeightUp + (positionYup - e.getY()));
							}
						} else if (type == 1 || type == 6) {
							currentShape.getPosition().y = e.getY();
							if (currentShape.getProperties().get("y1") != 0) {
								currentShape.getProperties().put("y1", oldHeightUp + (positionYup - e.getY()));

							}
							if (currentShape.getProperties().get("y2") != 0) {
								currentShape.getProperties().put("y2", oldHeightUp + (positionYup - e.getY()));
							}
							currentShape.getProperties().put("Height", oldHeightUp + (positionYup - e.getY()));
						}
					}
					repaint();
				}

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (currentShape != null) {

					if ((Math.abs(e.getX() - (currentShape.getPosition().x - 6)) <= 3)
							&& e.getY() >= currentShape.getPosition().y
							&& e.getY() <= currentShape.getProperties().get("Height") + currentShape.getPosition().y) {
						resize = "left";
						resizeFlag = true;
						setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					} else if ((Math.abs(e.getX()
							- (currentShape.getPosition().x + 10 + currentShape.getProperties().get("width"))) <= 10)
							&& e.getY() >= currentShape.getPosition().y
							&& e.getY() <= currentShape.getProperties().get("Height") + currentShape.getPosition().y) {
						resize = "right";
						resizeFlag = true;
						setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
					} else if ((Math.abs(e.getY() - (currentShape.getPosition().y - 6)) <= 3)
							&& e.getX() <= currentShape.getProperties().get("width") + currentShape.getPosition().x
							&& e.getX() >= currentShape.getPosition().x) {
						resize = "up";
						resizeFlag = true;
						setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					} else if ((Math.abs(e.getY()
							- (currentShape.getPosition().y + 10 + currentShape.getProperties().get("Height"))) <= 8)) {
						resize = "down";
						resizeFlag = true;
						setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));

					} else if (!currentShapeDragged) {
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
						resizeFlag = false;
					}
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					resizeFlag = false;
				}
			}

		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDemo.refresh(g);
		if (drawStart != null && drawEnd != null) {
			shape.draw(g);

		}

		if (currentShape != null) {
			currentShape.draw(g);
			Graphics2D c = (Graphics2D) g;
			float dashPhase = 0f;
			float dash[] = { 5.0f, 5.0f };
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 1.5f, dash, dashPhase);
			c.setStroke(dashed);
			c.setColor(Color.BLUE);
			double upperX = currentShape.getPosition().x - 5;
			double upperY = currentShape.getPosition().y - 5;
			double width = currentShape.getProperties().get("width") + 10;
			double height = currentShape.getProperties().get("Height") + 10;
			c.draw(new Rectangle2D.Double(upperX, upperY, width, height));

		}
	}

	private void makeProperShape() {

		if (Fill) {
			shape.setFillColor(fillColor);
		} else {
			shape.setFillColor(null);
		}
		if (currentAction == 1 || currentAction == 3) {// ellipse and rectangle
			shape.setPosition(new Point((int) Math.min(drawStart.getX(), drawEnd.getX()),
					(int) Math.min(drawStart.getY(), drawEnd.getY())));
			shape.getProperties().put("width", Math.abs(drawStart.getX() - drawEnd.getX()));
			shape.getProperties().put("Height", Math.abs(drawStart.getY() - drawEnd.getY()));
		} else if (currentAction == 4) {// circle
			double radius = Math.sqrt(Math.pow(Math.abs(drawStart.getX() - drawEnd.getX()), 2)
					+ Math.pow(Math.abs(drawStart.getY() - drawEnd.getY()), 2));
			shape.setPosition(new Point((int) (drawStart.getX() - radius), (int) (drawStart.getY() - radius)));
			shape.getProperties().put("width", 2 * radius);
			shape.getProperties().put("Height", 2 * radius);
		} else if (currentAction == 6) {// square
			if (drawEnd.getY() > drawStart.getY()) {
				shape.setPosition(new Point((int) Math.min(drawStart.getX(), drawEnd.getX()),
						(int) Math.min(drawStart.getY(), drawEnd.getY())));
			} else {
				shape.setPosition(new Point((int) Math.min(drawStart.getX(), drawEnd.getX()),
						(int) (drawStart.getY() - Math.abs(drawStart.getX() - drawEnd.getX()))));
			}
			shape.getProperties().put("width", Math.abs(drawStart.getX() - drawEnd.getX()));
			shape.getProperties().put("Height", Math.abs(drawStart.getX() - drawEnd.getX()));
		} else if (currentAction == 2 || currentAction == 5) {// line triangle
			shape.setPosition(new Point((int) Math.min(drawStart.getX(), drawEnd.getX()),
					(int) Math.min(drawStart.getY(), drawEnd.getY())));
			shape.getProperties().put("width", Math.abs(drawStart.getX() - drawEnd.getX()));
			shape.getProperties().put("Height", Math.abs(drawStart.getY() - drawEnd.getY()));
			shape.getProperties().put("x1", Math.abs(shape.getPosition().getX() - drawStart.getX()));
			shape.getProperties().put("y1", Math.abs(shape.getPosition().getY() - drawStart.getY()));
			shape.getProperties().put("x2", Math.abs(shape.getPosition().getX() - drawEnd.getX()));
			shape.getProperties().put("y2", Math.abs(shape.getPosition().getY() - drawEnd.getY()));
		}

	}

	public void setCurrentAction(int currentAction) {
		this.currentAction = currentAction;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public boolean isFill() {
		return Fill;
	}

	public void setFill(boolean fill) {
		Fill = fill;
	}

	public Shape getCurrentShape() {
		return this.currentShape;
	}

	public void setCurrentShape(Shape currentShape) {
		this.currentShape = currentShape;
	}

	public Shape getUpdatedShape() {
		return updatedShape;
	}

	public void setUpdatedShape(Shape updatedShape) {
		this.updatedShape = updatedShape;
	}

	public double getTransparent() {
		return transparent;
	}

	public void setTransparent(double transparent) {
		this.transparent = transparent;
	}

	public int getThick() {
		return thick;
	}

	public void setThick(int thick) {
		this.thick = thick;
	}
}
