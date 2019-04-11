package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.cs46_47.PaintEngine;

import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.SystemColor;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPopupMenu;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JRadioButton;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.Panel;

public class PaintApp extends JFrame {

	private JPanel contentPane;
	private JButton RecButton, LineButton, EllipseButton, CircleButton, TriangleButton, SquareButton;
	private ButtonGroup buttonGroup;
	private Color fillColor, strokeColor;
	private PaintEngine PaintDemo;
	private JPanel ShapesPanel;
	private JPanel SidePanel;
	private Board DrawingBoard;
	private JButton RedoButton;
	private JRadioButton Fill;
	private JRadioButton NoFill;
	private JButton StrokeButton;
	private JButton fillButton;
	JFileChooser fc;
	private Shape copiedShape;
	private JButton PasteButton;
	private JButton CopyButton;
	private JButton removeButton;
	private JButton FillWithColorButton;
	private boolean fillShape;
	private JPanel DrawingPanel;
	private JPanel copyPanel;
	private JPanel pastePanel;
	private JPanel SavePanel;
	private JPanel removePanel;
	private JPanel FillPanel;
	private JButton SaveButton;
	private JButton LoadButton;
	private JPanel strokePanel;
	private JPanel FillColorPanel;
	private boolean isFound ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaintApp frame = new PaintApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PaintApp() {

		PaintDemo = new PaintEngine();
		DrawingBoard = new Board(PaintDemo, this);
		DrawingBoard.setBackground(new Color(255, 255, 224));
		copiedShape = null;
		fillShape = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setSize(1000,700);
		this.setMinimumSize(new Dimension(1100, 700));
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel MainPanel = new JPanel();
		MainPanel.setBackground(new Color(0, 0, 255));
		contentPane.add(MainPanel, BorderLayout.NORTH);

		JPanel operationPanel = new JPanel();
		operationPanel.setBackground(new Color(67, 79, 91));

		JPanel NewPanel = new JPanel();
		operationPanel.add(NewPanel);
		NewPanel.setLayout(new BorderLayout(0, 0));

		JButton NewButton = new JButton("");
		NewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaintDemo.shapes.clear();
				PaintDemo.getRedoStack().clear();
				PaintDemo.getUndoStack().clear();
				PaintDemo.setRedoTimes(0);
				PaintDemo.setUndoTimes(0);
				repaint();
			}
		});
		NewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(NewButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(NewButton);
			}
		});
		NewButton.setBackground(new Color(67, 79, 91));
		NewButton.setContentAreaFilled(false);
		NewButton.setOpaque(true);
		NewButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_File_30px.png")));
		NewPanel.add(NewButton, BorderLayout.CENTER);

		Label label_13 = new Label("New ");
		label_13.setForeground(new Color(255, 255, 224));
		label_13.setBackground(new Color(67, 79, 91));
		label_13.setAlignment(Label.CENTER);
		NewPanel.add(label_13, BorderLayout.SOUTH);

		SavePanel = new JPanel();
		operationPanel.add(SavePanel);
		SavePanel.setLayout(new BorderLayout(0, 0));

		SaveButton = new JButton("");
		SaveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(SaveButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(SaveButton);
			}
		});
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc = new JFileChooser();
				fc.setDialogTitle("save file ");
				FileFilter filter = new FileFilter() {

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "XML and json files (*.xml)(*.json)";
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						} else {
							String fileName = f.getName().toLowerCase();
							return fileName.matches(".*\\.[Xx][Mm][Ll]") || fileName.matches(".*\\.[Jj][Ss][Oo][Nn]");
						}
					}
				};
				fc.setFileFilter(filter);
				fc.addChoosableFileFilter(filter);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int res = fc.showSaveDialog(PaintApp.this);
				if (res == JFileChooser.APPROVE_OPTION) {
					try {
						String filePath = fc.getSelectedFile().getCanonicalPath().toString();
						if (!(filePath.matches(".*\\.[Jj][Ss][Oo][Nn]") || filePath.matches(".*\\.[Xx][Mm][Ll]"))) {
							JOptionPane.showMessageDialog(PaintApp.this, "please enter xml or json file only", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else
							PaintDemo.save(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		SaveButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Save_30px.png")));
		SaveButton.setBackground(new Color(67, 79, 91));
		SaveButton.setContentAreaFilled(false);
		SaveButton.setOpaque(true);
		SavePanel.add(SaveButton, BorderLayout.CENTER);

		Label SaveLabel = new Label("Save");
		SaveLabel.setForeground(new Color(255, 255, 224));
		SaveLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
		SaveLabel.setAlignment(Label.CENTER);
		SaveLabel.setBackground(new Color(67, 79, 91));
		SavePanel.add(SaveLabel, BorderLayout.SOUTH);

		JPanel snapshotPanel = new JPanel();
		operationPanel.add(snapshotPanel);
		snapshotPanel.setLayout(new BorderLayout(0, 0));

		JButton screenButton = new JButton("");
		screenButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(screenButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(screenButton);
			}
		});
		screenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DrawingBoard.setBorder(BorderFactory.createTitledBorder("Bonus Painter"));
				BufferedImage img = new BufferedImage(DrawingBoard.getWidth(), DrawingBoard.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = img.createGraphics();
				DrawingBoard.paint(g);
				JFileChooser screenPath = new JFileChooser();
				screenPath.setDialogTitle("save file ");
				FileFilter filter = new FileFilter() {

					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return "jpg and png images (*.jpg)(*.png)";
					}

					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return true;
						} else {
							String fileName = f.getName().toLowerCase();
							return fileName.matches(".*\\.[Xx][Mm][Ll]") || fileName.matches(".*\\.[Jj][Ss][Oo][Nn]");
						}
					}
				};
				screenPath.setFileFilter(filter);
				screenPath.addChoosableFileFilter(filter);
				screenPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int res = screenPath.showSaveDialog(PaintApp.this);
				if (res == JFileChooser.APPROVE_OPTION) {
					try {
						String filePath = screenPath.getSelectedFile().getCanonicalPath().toString();
						if (!(filePath.matches(".*\\.[Jj][Pp][Gg]") || filePath.matches(".*\\.[Pp][Nn][Gg]"))) {
							JOptionPane.showMessageDialog(PaintApp.this, "please enter jpg or png image only", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							ImageIO.write(img, "jpg", screenPath.getSelectedFile());

							DrawingBoard.setBorder(null);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		screenButton.setBackground(new Color(67, 79, 91));
		screenButton.setContentAreaFilled(false);
		screenButton.setOpaque(true);
		screenButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Screenshot_30px.png")));
		snapshotPanel.add(screenButton, BorderLayout.CENTER);

		Label label_12 = new Label("screen shot");
		label_12.setBackground(new Color(67, 79, 91));
		label_12.setForeground(new Color(255, 255, 224));
		label_12.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_12.setAlignment(Label.CENTER);
		snapshotPanel.add(label_12, BorderLayout.SOUTH);

		Panel loadPanel = new Panel();
		operationPanel.add(loadPanel);
		loadPanel.setLayout(new BorderLayout(0, 0));

		LoadButton = new JButton("");
		LoadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(LoadButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(LoadButton);
			}
		});
		LoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc = new JFileChooser();
				fc.setDialogTitle("open xml or json file");
				int res = fc.showOpenDialog(PaintApp.this);
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (res == JFileChooser.APPROVE_OPTION) {
					try {
						String filePath = fc.getSelectedFile().getCanonicalPath().toString();
						if (!(filePath.matches(".*\\.[Jj][Ss][Oo][Nn]") || filePath.matches(".*\\.[Xx][Mm][Ll]"))) {
							JOptionPane.showMessageDialog(PaintApp.this, "Select xml or json file only", "Invalid",
									JOptionPane.ERROR_MESSAGE);
						} else
							PaintDemo.load(filePath);
						repaint();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		LoadButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Download_From_FTP_30px_2.png")));
		LoadButton.setBackground(new Color(67, 79, 91));
		LoadButton.setContentAreaFilled(false);
		LoadButton.setOpaque(true);
		loadPanel.add(LoadButton, BorderLayout.CENTER);

		Label label_11 = new Label("Load file");
		label_11.setFont(new Font("Arial", Font.BOLD, 12));
		label_11.setForeground(new Color(255, 255, 224));
		label_11.setBackground(new Color(67, 79, 91));
		label_11.setAlignment(Label.CENTER);
		loadPanel.add(label_11, BorderLayout.SOUTH);

		copyPanel = new JPanel();
		copyPanel.setBackground(new Color(67, 79, 91));
		operationPanel.add(copyPanel);

		CopyButton = new JButton("");
		CopyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(CopyButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(CopyButton);
			}
		});

		CopyButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Copy_30px.png")));
		CopyButton.setBackground(new Color(67, 79, 91));
		CopyButton.setForeground(new Color(255, 255, 255));
		CopyButton.setContentAreaFilled(false);
		CopyButton.setOpaque(true);
		CopyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					copiedShape = (Shape) DrawingBoard.getUpdatedShape().clone();

				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				copiedShape.setPosition(new Point(DrawingBoard.getCurrentShape().getPosition().x + 20,
						DrawingBoard.getCurrentShape().getPosition().y + 20));

				PasteButton.setEnabled(true);
				repaint();
			}
		});

		copyPanel.setLayout(new BorderLayout(0, 0));
		copyPanel.add(CopyButton, BorderLayout.CENTER);
		CopyButton.setEnabled(false);

		Label label_6 = new Label("Copy");
		label_6.setFont(new Font("Lucida Calligraphy", Font.BOLD, 12));
		label_6.setAlignment(Label.CENTER);
		label_6.setForeground(new Color(255, 255, 224));
		label_6.setBackground(new Color(67, 79, 91));
		copyPanel.add(label_6, BorderLayout.SOUTH);
		pastePanel = new JPanel();
		operationPanel.add(pastePanel);

		PasteButton = new JButton("");
		PasteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(PasteButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(PasteButton);
			}
		});
		PasteButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Paste_30px_1.png")));
		PasteButton.setBackground(new Color(67, 79, 91));
		PasteButton.setForeground(new Color(255, 255, 255));
		PasteButton.setContentAreaFilled(false);
		PasteButton.setOpaque(true);
		PasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaintDemo.addShape(copiedShape);
				DrawingBoard.setCurrentShape(null);
				PaintDemo.setUpdatedShape(null);
				removeButton.setEnabled(false);
				CopyButton.setEnabled(false);
				PasteButton.setEnabled(false);

				repaint();
			}
		});
		pastePanel.setLayout(new BorderLayout(0, 0));
		pastePanel.add(PasteButton);
		PasteButton.setEnabled(false);

		Label label_7 = new Label("Paste");
		label_7.setAlignment(Label.CENTER);
		label_7.setBackground(new Color(67, 79, 91));
		label_7.setForeground(new Color(255, 255, 224));
		pastePanel.add(label_7, BorderLayout.SOUTH);

		removePanel = new JPanel();
		operationPanel.add(removePanel);

		FillPanel = new JPanel();
		operationPanel.add(FillPanel);

		removeButton = new JButton("");
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(removeButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(removeButton);
			}
		});
		removeButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Remove_30px.png")));
		removeButton.setBackground(new Color(67, 79, 91));
		removeButton.setForeground(new Color(255, 255, 255));
		removeButton.setContentAreaFilled(false);
		removeButton.setOpaque(true);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				PaintDemo.removeShape(DrawingBoard.getUpdatedShape());
				DrawingBoard.setCurrentShape(null);
				DrawingBoard.setUpdatedShape(null);
				PaintDemo.setUpdatedShape(null);
				CopyButton.setEnabled(false);
				PasteButton.setEnabled(false);
				removeButton.setEnabled(false);
				if (PaintDemo.getShapes().length == 0)
					FillWithColorButton.setEnabled(false);
				repaint();

			}
		});
		FillPanel.setLayout(new BorderLayout(0, 0));

		FillWithColorButton = new JButton("");
		FillWithColorButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(FillWithColorButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(FillWithColorButton);
			}
		});
		FillWithColorButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Fill_Color_30px_1.png")));
		FillWithColorButton.setBackground(new Color(67, 79, 91));
		FillWithColorButton.setForeground(new Color(255, 255, 224));
		FillWithColorButton.setContentAreaFilled(false);
		FillWithColorButton.setOpaque(true);
		FillWithColorButton.setEnabled(false);
		FillPanel.add(FillWithColorButton);

		Label label_10 = new Label("Fill With color");
		label_10.setAlignment(Label.CENTER);
		label_10.setBackground(new Color(67, 79, 91));
		label_10.setForeground(new Color(255, 255, 224));
		FillPanel.add(label_10, BorderLayout.SOUTH);
		FillWithColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillShape = true;
			}
		});
		removePanel.setLayout(new BorderLayout(0, 0));
		removePanel.add(removeButton);
		removeButton.setEnabled(false);

		Label label_9 = new Label("Remove");
		label_9.setAlignment(Label.CENTER);
		label_9.setBackground(new Color(67, 79, 91));
		label_9.setForeground(new Color(255, 255, 224));
		removePanel.add(label_9, BorderLayout.SOUTH);
		JPanel UndoRedoPanel = new JPanel();
		UndoRedoPanel.setBackground(new Color(67, 79, 91));
		UndoRedoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		UndoRedoPanel.setLayout(new BoxLayout(UndoRedoPanel, BoxLayout.X_AXIS));

		RedoButton = new JButton("");
		RedoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(RedoButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(RedoButton);
			}
		});
		RedoButton.setForeground(new Color(147, 112, 219));
		RedoButton.setBackground(new Color(67, 79, 91));
		RedoButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Redo_30px.png")));
		RedoButton.setContentAreaFilled(false);
		RedoButton.setOpaque(true);
		RedoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaintDemo.redo();
				repaint();
			}
		});

		JPanel panel_4 = new JPanel();
		UndoRedoPanel.add(panel_4);
		panel_4.setBackground(new Color(67, 79, 91));
		panel_4.setLayout(new GridLayout(3, 1, 0, 0));

		Label label_4 = new Label("Fill Shape");
		label_4.setForeground(new Color(255, 255, 224));
		label_4.setBackground(new Color(67, 79, 91));
		label_4.setAlignment(Label.CENTER);
		panel_4.add(label_4);
		NoFill = new JRadioButton("No Fill");
		NoFill.setForeground(new Color(255, 255, 224));
		NoFill.setBackground(new Color(67, 79, 91));
		NoFill.setSelected(true);
		Fill = new JRadioButton("Fill solid Color");
		Fill.setForeground(new Color(255, 255, 224));
		Fill.setBackground(new Color(67, 79, 91));
		buttonGroup = new ButtonGroup();
		buttonGroup.add(NoFill);
		buttonGroup.add(Fill);
		panel_4.add(NoFill);
		panel_4.add(Fill);
		RedoButton.setToolTipText("Redo");
		UndoRedoPanel.add(RedoButton);

		fillColor = Color.RED;
		strokeColor = Color.BLACK;
		strokePanel = new JPanel();
		operationPanel.add(strokePanel);
		strokePanel.setLayout(new BorderLayout(0, 0));
		FillColorPanel = new JPanel();
		operationPanel.add(FillColorPanel);
		StrokeButton = new JButton("");
		StrokeButton.setBorder(new MatteBorder(0, 7, 0, 7, (Color) new Color(67, 79, 91)));
		StrokeButton.setBackground(strokeColor);
		StrokeButton.setToolTipText("Choose Stroke Color");
		StrokeButton.setContentAreaFilled(false);
		StrokeButton.setOpaque(true);

		fillButton = new JButton("");
		fillButton.setBorder(new MatteBorder(0, 7, 0, 7, (Color) new Color(67, 79, 91)));
		fillButton.setBackground(fillColor);
		fillButton.setForeground(fillColor);
		fillButton.setContentAreaFilled(false);
		fillButton.setOpaque(true);
		fillButton.setToolTipText("Choose FillColor");
		fillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				fillColor = JColorChooser.showDialog(null, "Fill Color", fillColor);
				fillButton.setBackground(fillColor);
				fillButton.setForeground(fillColor);
				DrawingBoard.setFillColor(fillColor);
			}
		});
		FillColorPanel.setLayout(new BorderLayout(0, 0));
		FillColorPanel.add(fillButton);

		Label label_15 = new Label("fill color");
		label_15.setForeground(new Color(255, 255, 224));
		label_15.setBackground(new Color(67, 79, 91));
		label_15.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_15.setAlignment(Label.CENTER);
		FillColorPanel.add(label_15, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(67, 79, 91));
		FillColorPanel.add(panel_1, BorderLayout.NORTH);
		StrokeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				strokeColor = JColorChooser.showDialog(null, "stroke Color", strokeColor);
				StrokeButton.setBackground(strokeColor);
				DrawingBoard.setStrokeColor(strokeColor);
			}
		});
		strokePanel.add(StrokeButton, BorderLayout.CENTER);

		Label label_14 = new Label("stroke ");
		label_14.setForeground(new Color(255, 255, 224));
		label_14.setFont(new Font("Arial Black", Font.BOLD, 12));
		label_14.setBackground(new Color(67, 79, 91));
		label_14.setAlignment(Label.CENTER);
		strokePanel.add(label_14, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(67, 79, 91));
		strokePanel.add(panel, BorderLayout.NORTH);
		JButton UndoButton = new JButton("");
		UndoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(UndoButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(UndoButton);
			}
		});
		UndoButton.setBackground(new Color(218, 112, 214));
		UndoButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Undo_30px.png")));
		UndoButton.setOpaque(true);
		UndoButton.setContentAreaFilled(false);
		UndoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PaintDemo.undo();
				repaint();
			}
		});
		UndoRedoPanel.add(UndoButton);
		UndoButton.setToolTipText("Undo");

		Label label_2 = new Label("Stroke Color");
		label_2.setForeground(new Color(255, 255, 224));

		Label label_3 = new Label("Fill Color");
		label_3.setForeground(new Color(255, 255, 224));

		ButtonGroup buttonGroup = new ButtonGroup();
		MainPanel.setLayout(new BorderLayout(0, 0));
		MainPanel.add(operationPanel, BorderLayout.CENTER);
		operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.X_AXIS));
		operationPanel.add(UndoRedoPanel);

		SidePanel = new JPanel();
		SidePanel.setBackground(Color.GRAY);
		contentPane.add(SidePanel, BorderLayout.EAST);
		SidePanel.setLayout(new CardLayout(0, 0));

		DrawingPanel = new JPanel();
		SidePanel.add(DrawingPanel, "name_204235931198092");

		/**
		 * panel that contains all shapes
		 */
		DrawingPanel.setLayout(new BorderLayout(0, 0));
		ShapesPanel = new JPanel();
		ShapesPanel.setBorder(new MatteBorder(2, 0, 1, 0, (Color) new Color(0, 0, 0)));
		ShapesPanel.setBackground(new Color(67, 79, 91));
		DrawingPanel.add(ShapesPanel, BorderLayout.CENTER);
		ShapesPanel.setLayout(new GridLayout(7, 2, 0, 0));
		// **************shapes panel component **************//
		Label label = new Label("2D");
		label.setForeground(new Color(255, 255, 224));
		label.setFont(new Font("Dialog", Font.ITALIC, 15));
		label.setAlignment(Label.CENTER);
		ShapesPanel.add(label);

		Label label_1 = new Label("Shapes");

		label_1.setForeground(new Color(255, 255, 224));
		label_1.setFont(new Font("Dialog", Font.ITALIC, 15));
		ShapesPanel.add(label_1);

		RecButton =new JButton();
		RecButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Rectangle_50px.png")));
		RecButton.setBackground(new Color(67, 79, 91));
		RecButton.setContentAreaFilled(false);
		RecButton.setOpaque(true);
		RecButton.setToolTipText("Rectangle");
		RecButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(1);
			}
		});
		RecButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(RecButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(RecButton);
			}
		});

		ShapesPanel.add(RecButton);

		LineButton = new JButton();
		LineButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Line_50px.png")));
		LineButton.setBackground(new Color(67, 79, 91));
		LineButton.setContentAreaFilled(false);
		LineButton.setOpaque(true);
		LineButton.setToolTipText("Line ");
		LineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(2);
			}
		});
		LineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(LineButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(LineButton);
			}
		});
		
		ShapesPanel.add(LineButton);

		EllipseButton = new JButton();
		EllipseButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Oval_50px.png")));
		EllipseButton.setBackground(new Color(67, 79, 91));
		EllipseButton.setContentAreaFilled(false);
		EllipseButton.setOpaque(true);
		EllipseButton.setToolTipText("Ellipse ");
		EllipseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(3);
			}
		});
		EllipseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(EllipseButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(EllipseButton);
			}
		});
		
		ShapesPanel.add(EllipseButton);

		CircleButton = new JButton();
		CircleButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Circle_50px.png")));
		CircleButton.setBackground(new Color(67, 79, 91));
		CircleButton.setContentAreaFilled(false);
		CircleButton.setOpaque(true);
		CircleButton.setToolTipText("Circle ");
		CircleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(4);
			}
		});
		CircleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(CircleButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(CircleButton);
			}
		});
		
		ShapesPanel.add(CircleButton);

		TriangleButton = new JButton();
		TriangleButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Triangle_50px.png")));
		TriangleButton.setBackground(new Color(67, 79, 91));
		TriangleButton.setContentAreaFilled(false);
		TriangleButton.setOpaque(true);
		TriangleButton.setToolTipText("Triangle ");
		TriangleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(5);
			}
		});
		TriangleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(TriangleButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(TriangleButton);
			}
		});
		
		ShapesPanel.add(TriangleButton);

		SquareButton = new JButton();
		SquareButton.setIcon(new ImageIcon(PaintApp.class.getResource("/icons/icons8_Unchecked_Checkbox_50px.png")));
		SquareButton.setBackground(new Color(67, 79, 91));
		SquareButton.setContentAreaFilled(false);
		SquareButton.setOpaque(true);
		SquareButton.setToolTipText("Sqaure 2D ");
		SquareButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// currentAction = action;
				if (Fill.isSelected()) {
					DrawingBoard.setFill(true);
				} else if (NoFill.isSelected()) {
					DrawingBoard.setFill(false);

				}
				FillWithColorButton.setEnabled(true);
				DrawingBoard.setCurrentAction(6);
			}
		});
		SquareButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				changeButtonBackgroud(SquareButton);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				returnButtonBackgroud(SquareButton);
			}
		});
		
		ShapesPanel.add(SquareButton);

		JPanel panel_6 = new JPanel();
		DrawingPanel.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new MatteBorder(2, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel_6.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 1, 0, 0));

		Label label_5 = new Label("Thickness");
		label_5.setForeground(new Color(250, 235, 215));
		label_5.setFont(new Font("Arial", Font.BOLD, 12));
		label_5.setBackground(new Color(67, 79, 91));
		label_5.setAlignment(Label.CENTER);
		panel_7.add(label_5);

		JSlider thicknessSlider = new JSlider(1, 20, 2);
		thicknessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				DrawingBoard.setThick(thicknessSlider.getValue());
			}
		});
		thicknessSlider.setPaintTicks(true);
		thicknessSlider.setPaintLabels(true);
		thicknessSlider.setMinorTickSpacing(1);
		thicknessSlider.setMajorTickSpacing(2);
		thicknessSlider.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		thicknessSlider.setBackground(new Color(67, 79, 91));
		panel_7.add(thicknessSlider);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		panel_6.add(panel_8);
		panel_8.setLayout(new GridLayout(2, 1, 0, 0));

		Label label_8 = new Label("Transparent");
		label_8.setForeground(new Color(250, 235, 215));
		label_8.setFont(new Font("Arial", Font.BOLD, 12));
		label_8.setBackground(new Color(67, 79, 91));
		label_8.setAlignment(Label.CENTER);
		panel_8.add(label_8);

		JSlider tranSlider = new JSlider(1, 99, 99);
		tranSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				DrawingBoard.setTransparent(tranSlider.getValue() * 0.01);
			}
		});
		tranSlider.setPaintTicks(true);
		tranSlider.setPaintLabels(true);
		tranSlider.setMinorTickSpacing(1);
		tranSlider.setMajorTickSpacing(10);
		tranSlider.setBackground(new Color(67, 79, 91));
		panel_8.add(tranSlider);
		// *****************************************************************//
		JPanel ShapeInfoPanel = new JPanel();
		SidePanel.add(ShapeInfoPanel, "name_241087561516324");

		contentPane.add(DrawingBoard, BorderLayout.CENTER);
	}

	public JButton getRemoveButton() {
		return removeButton;
	}

	public JButton getCopyButton() {
		return CopyButton;
	}

	public boolean IsFillShpe() {
		return fillShape;
	}

	public void setFillShape(boolean fill) {
		this.fillShape = fill;
	}

	private void changeButtonBackgroud(JButton b) {
		b.setBackground(Color.DARK_GRAY);
		b.setContentAreaFilled(false);
		b.setOpaque(true);
	}

	private void returnButtonBackgroud(JButton b) {
		b.setBackground(new Color(67, 79, 91));
		b.setContentAreaFilled(false);
		b.setOpaque(true);
	}

}