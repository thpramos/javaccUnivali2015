package br.univali;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class MainWindow {

	private JFrame frame;
	private JTextArea editorTA;
	private JTextArea logTA;
	//Create a file chooser
	JFileChooser fc = new JFileChooser();
	private JLabel lblLinha;
	private JLabel lblColumn;
	private HashMap<Object, Action> actions;
	
	private boolean editSaved = true;
	

	//undo helpers
	private UndoAction undoAction;
	private UndoManager undo = new UndoManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					window.frame.setTitle("Compilador");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
	
	   	 FileNameExtensionFilter filter = new FileNameExtensionFilter(
				    "Arquivo Codigo Monstro", "acm");
				
	   	 
		 // set default type
		 fc.setFileFilter(filter);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 521);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		 
		frame.addWindowListener( new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	if(!editSaved){
			        JFrame frame = (JFrame)e.getSource();
			 
			        int result = JOptionPane.showOptionDialog(frame, "Você deseja salvar antes de sair?", "Salvar antes de sair.", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Sim","Não","Cancelar"} , "Sim");	 
			        		
			 
			        switch(result){
			        case 0:
			        	saveButton();
			        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        	break;
			        case 1:
			        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        	break;
			        case 2:
			        	break;
			        
			        }
		    	}else{
		    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		    		
		    	}
		    }
		});
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 100));
		panel.setMinimumSize(new Dimension(10, 100));
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar_1 = new JToolBar();
		panel.add(toolBar_1, BorderLayout.SOUTH);
		
		lblColumn = new JLabel("Coluna:");
		toolBar_1.add(lblColumn);
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(12, 32767));
		separator.setPreferredSize(new Dimension(12, 12));
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar_1.add(separator);
		
		lblLinha = new JLabel("Linha:");
		toolBar_1.add(lblLinha);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		logTA = new JTextArea();
		logTA.setEditable(false);
		scrollPane_1.setViewportView(logTA);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_1.add(toolBar, BorderLayout.NORTH);
		
		
		
		
		///EDITOR
		
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		editorTA = new JTextArea();
		editorTA.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				int dot = e.getDot();
			    int line;
				try {
					editSaved = false;
					line = getLineOfOffset(editorTA, dot);
				    int positionInLine = dot - getLineStartOffset(editorTA, line);
				    lblColumn.setText("Coluna:"+(positionInLine+1));
				    lblLinha.setText("Linha:"+(line+1));
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(editorTA);
		
		editorTA.getDocument().addUndoableEditListener(new UndoableEditListener() {
			
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undo.addEdit(e.getEdit());
	            undoAction.updateUndoState();
				
			}
		});
		
		
		createActionTable(editorTA);
		
		
		
		
		
	
		
		///ICONS
		
		//NEW
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newDocument();
			}
		});
		button.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/016.png")));
		toolBar.add(button);
		
		
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openButton();
			}
		});
		
		
		btnNewButton.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/020.png")));
		toolBar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButton();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/076.png")));
		toolBar.add(btnNewButton_1);
		
		
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setVisible(false);
		separator_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		separator_1.setMaximumSize(new Dimension(11, 32767));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator_1);
		
		
		//CUT
		
		
		Action cutAction = getActionByName(DefaultEditorKit.cutAction);
		
		JButton button_1 = new JButton(cutAction);
		button_1.setText("");
		button_1.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/095.png")));
		toolBar.add(button_1);
		
		//COPY
		
		Action copyAction = getActionByName(DefaultEditorKit.copyAction);
		JButton button_2 = new JButton("");
		button_2.setText("");
		button_2.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/096.png")));
		toolBar.add(button_2);
		
		//PASTE
		
		
		Action pasteAction = getActionByName(DefaultEditorKit.pasteAction);
		JButton btnNewButton_2 = new JButton(pasteAction);
		btnNewButton_2.setText("");
		btnNewButton_2.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/093.png")));
		toolBar.add(btnNewButton_2);
		
		
		//DELETE
		
		Action deleteAction = getActionByName(DefaultEditorKit.cutAction);
		JButton button_3 = new JButton(deleteAction);
		button_3.setText("");
		button_3.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/022.png")));
		toolBar.add(button_3);
		
		
		//UNDO
		undoAction = new UndoAction();
		JButton button_4 = new JButton(undoAction);
		button_4.setText("");
		button_4.setIcon(new ImageIcon(MainWindow.class.getResource("/br/univali/202.png")));
		toolBar.add(button_4);
		
		
		editorTA.getActionMap().put("Undo", undoAction);

		
		// Bind the undo action to ctl-Z (or command-Z on mac)
		  editorTA.getInputMap().put(
		    KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");


		
		
		//CENTER


		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnArquivo = new JMenu("Arquivo");
		menuBar.add(mnArquivo);
		
		JMenuItem mntmNovo = new JMenuItem("Novo");
		mnArquivo.add(mntmNovo);
		mntmNovo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newDocument();
			}
		});
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mnArquivo.add(mntmAbrir);
		mntmAbrir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openButton();
			}
		});
		
		JMenuItem mntmSalvar = new JMenuItem("Salvar");
		mnArquivo.add(mntmSalvar);
		mntmSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveButton();
			}
		});
		
		JMenuItem mntmSalvarComo = new JMenuItem("Salvar como");
		mnArquivo.add(mntmSalvarComo);
		
		JMenuItem mntmSair = new JMenuItem("Sair");
		mnArquivo.add(mntmSair);
		
		JMenu mnEdio = new JMenu("Edi\u00E7\u00E3o");
		menuBar.add(mnEdio);
		
		JMenuItem mntmCopiar = new JMenuItem("Copiar");
		mnEdio.add(mntmCopiar);
		
		JMenuItem mntmColar = new JMenuItem("Colar");
		mnEdio.add(mntmColar);
		
		JMenuItem mntmRecortar = new JMenuItem("Recortar");
		mnEdio.add(mntmRecortar);
		
		JMenu mnC = new JMenu("Compila\u00E7\u00E3o");
		menuBar.add(mnC);
		
		JMenuItem mntmCompilar = new JMenuItem("Compilar");
		mnC.add(mntmCompilar);
		mntmCompilar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compile();
			}
		});
		
		JMenuItem mntmExecutar = new JMenuItem("Executar");
		mnC.add(mntmExecutar);
		
		
	}

	
	
	protected void compile() {
		logTA.setText("");
		Reader reader = new BufferedReader(new StringReader(editorTA.getText()));
		UNIVALI1 parser = new UNIVALI1(reader);
		try {
			logTA.append(parser.input());
		} catch (TokenMgrError e) {
			logTA.append(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			logTA.append(e.getMessage());
			e.printStackTrace();
		}
	}
	
	protected void openButton(){
		int returnVal = fc.showOpenDialog(frame);
		 
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
            try {
				openDocument(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            //logTA.append("Open command cancelled by user." + newline);
        }
        //logTA.setCaretPosition(log.getDocument().getLength());

	}
	
	protected void saveButton(){
		 int returnVal = fc.showSaveDialog(frame);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
        	 
            File fileToBeSaved = fc.getSelectedFile();

            if(!fc.getSelectedFile().getAbsolutePath().endsWith(".acm")){
                fileToBeSaved = new File(fc.getSelectedFile() + ".acm");
            }
            
            frame.setTitle("Compilador - "+fileToBeSaved.getName());
             try {
				saveFile(fileToBeSaved);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         } else {
//             log.append("Save command cancelled by user." + newline);
         }
//         log.setCaretPosition(log.getDocument().getLength());
     }
	
	
	protected void openDocument(File file) throws IOException{
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		editorTA.setText("");
		String s;
		while ((s = br.readLine()) != null) {
			editorTA.append(s+"\n");
		}
		br.close();
	}
	
	protected void saveFile(File file) throws IOException{
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(editorTA.getText());
		bw.close();
		editSaved = true;
	}

	
	private void newDocument() {
		editorTA.setText("");
		logTA.setText("");
	}

	static int getLineOfOffset(JTextComponent comp, int offset) throws BadLocationException {
	    Document doc = comp.getDocument();
	    if (offset < 0) {
	        throw new BadLocationException("Can't translate offset to line", -1);
	    } else if (offset > doc.getLength()) {
	        throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
	    } else {
	        Element map = doc.getDefaultRootElement();
	        return map.getElementIndex(offset);
	    }
	}

	static int getLineStartOffset(JTextComponent comp, int line) throws BadLocationException {
	    Element map = comp.getDocument().getDefaultRootElement();
	    if (line < 0) {
	        throw new BadLocationException("Negative line", -1);
	    } else if (line >= map.getElementCount()) {
	        throw new BadLocationException("No such line", comp.getDocument().getLength() + 1);
	    } else {
	        Element lineElem = map.getElement(line);
	        return lineElem.getStartOffset();
	    }
	}
	
	
	
	private HashMap<Object, Action> createActionTable(JTextComponent textComponent) {
        actions = new HashMap<Object, Action>();
        Action[] actionsArray = textComponent.getActions();
        for (int i = 0; i < actionsArray.length; i++) {
            Action a = actionsArray[i];
            actions.put(a.getValue(Action.NAME), a);
        }
        return actions;
    }
	
	private Action getActionByName(String name) {
	    return actions.get(name);
	}
	
	
	
	class UndoAction extends AbstractAction {
        public UndoAction() {
            super("Undo");
            setEnabled(false);
        }
 
        public void actionPerformed(ActionEvent e) {
            try {
                undo.undo();
            } catch (CannotUndoException ex) {
                System.out.println("Unable to undo: " + ex);
                ex.printStackTrace();
            }
            updateUndoState();
        }
 
        protected void updateUndoState() {
            if (undo.canUndo()) {
                setEnabled(true);
                //putValue(Action.NAME, undo.getUndoPresentationName());
            } else {
                setEnabled(false);
                //putValue(Action.NAME, "Undo");
            }
        }
    }
	
}



/*

program[
define[   
   constant boolean is teste. 
    integer is um_inteiro.
  not constant boolean is _verdade.
]
execute[
  get[teste].
  inspect 2+3
  is true[set 2 to um_inteiro].
  set 2 to um.
]
]
*/