import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = -1324363758675184283L;

	Window() {
		
		add(new Panel());
		setTitle("Snake game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
}
