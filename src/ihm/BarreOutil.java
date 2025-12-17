package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import java.awt.event.ActionEvent;

/** Barre d'outils de l'application
 * @author Equipe 5
 */
public class BarreOutil extends JPanel implements ActionListener
{
	private JButton btnOuvrir , btnEnregistrer;
	private JButton btnAnnuler, btnRepeter;
	private JButton btnZoom   , btnDezoom;

	public BarreOutil(FramePrincipale frame)
	{
		Dimension dimension;
		ImageIcon iconOuvrir, iconEnregistrer;
		ImageIcon iconAnnuler, iconRepeter;
		ImageIcon iconZoom, iconDezoom;


		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(230, 230, 230));


		/* Création des composants */
		// Icones
		iconOuvrir      = new ImageIcon("../src/images/icons/iconOuvrir.png");
		iconEnregistrer = new ImageIcon("../src/images/icons/iconEnregistrer.png");
		iconAnnuler     = new ImageIcon("../src/images/icons/iconAnnuler.png");
		iconRepeter     = new ImageIcon("../src/images/icons/iconRepeter.png");
		iconZoom        = new ImageIcon("../src/images/icons/iconZoom.png");
		iconDezoom      = new ImageIcon("../src/images/icons/iconDezoom.png");

		// Boutons
		this.btnOuvrir      = new JButton(iconOuvrir);
		this.btnEnregistrer = new JButton(iconEnregistrer);
		this.btnAnnuler     = new JButton(iconAnnuler);
		this.btnRepeter	    = new JButton(iconRepeter);
		this.btnZoom        = new JButton(iconZoom);
		this.btnDezoom      = new JButton(iconDezoom);

		// Configuration des boutons
		dimension = new Dimension(40, 40);
		this.btnOuvrir     .setPreferredSize(dimension);
		this.btnEnregistrer.setPreferredSize(dimension);
		this.btnAnnuler    .setPreferredSize(dimension);
		this.btnRepeter    .setPreferredSize(dimension);
		this.btnZoom       .setPreferredSize(dimension);
		this.btnDezoom     .setPreferredSize(dimension);

		/* Ajout des composants */
		this.add(this.btnOuvrir);
		this.add(this.btnEnregistrer);
		this.add(this.btnAnnuler);
		this.add(this.btnRepeter);
		this.add(this.btnZoom);
		this.add(this.btnDezoom);

		/* Activation des composants */
		this.btnOuvrir     .addActionListener(this);
		this.btnEnregistrer.addActionListener(this);
		this.btnAnnuler    .addActionListener(this);
		this.btnRepeter    .addActionListener(this);
		this.btnZoom       .addActionListener(this);
		this.btnDezoom     .addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnZoom)
		{
			System.out.println("Zoom !");
		}
		else if (e.getSource() == this.btnDezoom)
		{
			System.out.println("Dézoom !");
		}
		else if (e.getSource() == this.btnOuvrir)
		{
			System.out.println("Ouvrir !");
		}
		else if (e.getSource() == this.btnEnregistrer)
		{
			System.out.println("Enregistrer !");
		}
		else if (e.getSource() == this.btnAnnuler)
		{
			System.out.println("Annuler !");
		}
		else if (e.getSource() == this.btnRepeter)
		{
			System.out.println("Répéter !");
		}
	}
}
