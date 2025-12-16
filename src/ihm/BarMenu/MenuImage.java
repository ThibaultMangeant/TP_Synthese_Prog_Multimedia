package ihm.BarMenu;

import controleur.Controleur;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuImage extends JMenu implements ActionListener
{
	private Controleur controleur;

	private JMenuItem itemRotation;
	private JMenuItem itemMiroirH;
	private JMenuItem itemMiroirV;
	private JMenuItem itemRedimensionner;

	public MenuImage(Controleur controleur)
	{
		super("Image");

		this.controleur = controleur;

		/* Création des items */
		this.itemRotation       = new JMenuItem("Rotation");
		this.itemMiroirH        = new JMenuItem("Miroir Horizontal");
		this.itemMiroirV        = new JMenuItem("Miroir Vertical");
		this.itemRedimensionner = new JMenuItem("Redimensionner");

		/* Ajout des items au menu Image */
		this.add(this.itemRedimensionner);
		this.add(this.itemRotation);
		this.addSeparator();
		this.add(this.itemMiroirH);
		this.add(this.itemMiroirV);

		/* Activation des composants */
		this.itemRotation      .addActionListener(this);
		this.itemMiroirH       .addActionListener(this);
		this.itemMiroirV       .addActionListener(this);
		this.itemRedimensionner.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (this.controleur != null)
		{
			if (e.getSource() == this.itemMiroirH)
			{
				this.controleur.miroirHorizontal();
			}

			if (e.getSource() == this.itemMiroirV)
			{
				this.controleur.miroirVertical();
			}

			if (e.getSource() == this.itemRotation)
			{
				int angle = Integer.parseInt(JOptionPane.showInputDialog("Rentrer l'angle : "));

				this.controleur.rotation(angle);
			}

			if (e.getSource() == this.itemRedimensionner)
			{
				int nouvelleLargeur  = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle largeur : "));
				int nouvelleHauteur  = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle hauteur : "));

				this.controleur.redimensionner(nouvelleLargeur, nouvelleHauteur);			}
		}
	}
}
