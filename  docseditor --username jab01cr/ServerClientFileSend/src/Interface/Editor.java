package Interface;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JWindow;
import javax.swing.JEditorPane;
import javax.swing.JToolBar;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

public class Editor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JEditorPane jEditorPane = null;
	/**
	 * This is the default constructor
	 */
	public Editor() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJEditorPane());
		this.setTitle("Editor");
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
		}
		return jEditorPane;
	}

}  //  @jve:decl-index=0:visual-constraint="4,10"
