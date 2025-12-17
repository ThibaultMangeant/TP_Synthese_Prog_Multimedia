package ihm;

import controleur.Controleur;
import ihm.BarMenu.BarMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class FramePrincipale extends JFrame
{
	private PanelPrincipal panelPrincipal;
	private Controleur controleur;

	public FramePrincipale(Controleur ctrl)
	{
		this.controleur = ctrl;
		
		this.panelPrincipal = new PanelPrincipal(this);
		
		this.setTitle("Application Multimedia");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocation(200, 200);
		
		this.initMenu();
		this.initRaccourcisClavier();

		this.add(panelPrincipal);
		this.setVisible(true);
	}

	private void initMenu()
	{
		this.setJMenuBar(new BarMenu(this.controleur));
	}

	private void initRaccourcisClavier()
	{
		// Raccourcis Ctrl+Z et Ctrl+Y pour undo/redo
		InputMap inputMap = this.panelPrincipal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.panelPrincipal.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redo");

		actionMap.put("undo", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controleur.annuler();
			}
		});

		actionMap.put("redo", new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				controleur.refaire();
			}
		});
	}

	public boolean contient(int x, int y)
	{
		return this.controleur.contient(x, y);
	}

	public int getPosX()
	{
		return this.controleur.getPosX();
	}

	public int getPosY()
	{
		return this.controleur.getPosY();
	}

	public void setPosX(int x)
	{
		this.controleur.setPosX(x);
	}

	public void setPosY(int y)
	{
		this.controleur.setPosY(y);
	}

	public BufferedImage getImage()
	{
		return this.controleur.getImage();
	}

	public void afficherImage(String path)
	{
		this.panelPrincipal.chargerImage();
	}

	public void afficherImage(BufferedImage image)
	{
		this.panelPrincipal.setImage(image);
	}

	public BufferedImage getCalqueTexte()
	{
		return this.controleur.getCalqueTexte();
	}

	public int getCalqueTexteX()
	{
		return this.controleur.getCalqueTexteX();
	}

	public int getCalqueTexteY()
	{
		return this.controleur.getCalqueTexteY();
	}

	public void setCalqueTexteX(int x)
	{
		this.controleur.setCalqueTexteX(x);
	}

	public void setCalqueTexteY(int y)
	{
		this.controleur.setCalqueTexteY(y);
	}

	public boolean contientCalqueTexte(int x, int y)
	{
		return this.controleur.contientCalqueTexte(x, y);
	}

	public void fusionnerCalqueTexte()
	{
		this.controleur.fusionnerCalqueTexte();
	}

	public BufferedImage getCalqueSuperposition()
	{
		return this.controleur.getCalqueSuperposition();
	}

	public int getCalqueSuperpositionX()
	{
		return this.controleur.getCalqueSuperpositionX();
	}

	public int getCalqueSuperpositionY()
	{
		return this.controleur.getCalqueSuperpositionY();
	}

	public void setCalqueSuperpositionX(int x)
	{
		this.controleur.setCalqueSuperpositionX(x);
	}

	public void setCalqueSuperpositionY(int y)
	{
		this.controleur.setCalqueSuperpositionY(y);
	}

	public boolean contientCalqueSuperposition(int x, int y)
	{
		return this.controleur.contientCalqueSuperposition(x, y);
	}

	public void fusionnerCalqueSuperposition()
	{
		this.controleur.fusionnerCalqueSuperposition();
	}

	public void majIHM()
	{
		this.panelPrincipal.majIHM();
	}
}