package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/** Barre d'outils de l'application
 * @author Equipe 5
 */
public class BarreOutil extends JPanel implements ActionListener
{
	private JButton btnOuvrir        , btnEnregistrer;
	private JButton btnAnnuler       , btnRepeter;
	private JButton btnZoom          , btnDezoom;
	private JButton btnRedimensionner, btnRotation;
	private JButton btnMiroirH       , btnMiroirV;
	private JButton btnPotPeinture;

	public BarreOutil(FramePrincipale frame)
	{
		Dimension dimension;
		ImageIcon iconOuvrir        , iconEnregistrer;
		ImageIcon iconAnnuler       , iconRepeter;
		ImageIcon iconZoom          , iconDezoom;
		ImageIcon iconRedimensionner, iconRotation;
		ImageIcon iconMiroirH       , iconMiroirV;
		ImageIcon iconPotPeinture;


		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(230, 230, 230));


		/* Création des composants */
		// Icones
		iconOuvrir         = new ImageIcon("../src/images/icons/iconOuvrir.svg"        );
		iconEnregistrer    = new ImageIcon("../src/images/icons/iconEnregistrer.svg"   );
		iconAnnuler        = new ImageIcon("../src/images/icons/iconAnnuler.svg"       );
		iconRepeter        = new ImageIcon("../src/images/icons/iconRepeter.svg"       );
		iconZoom           = new ImageIcon("../src/images/icons/iconZoom.svg"          );
		iconDezoom         = new ImageIcon("../src/images/icons/iconDezoom.svg"        );
		iconRedimensionner = new ImageIcon("../src/images/icons/iconRedimensionner.svg");
		iconRotation       = new ImageIcon("../src/images/icons/iconRotation.svg"      );
		iconMiroirH        = new ImageIcon("../src/images/icons/iconMiroirH.svg"       );
		iconMiroirV        = new ImageIcon("../src/images/icons/iconMiroirV.svg"       );
		iconPotPeinture    = new ImageIcon("../src/images/icons/iconPotPeinture.svg"   );

		// Boutons
		this.btnOuvrir         = new JButton(iconOuvrir        );
		this.btnEnregistrer    = new JButton(iconEnregistrer   );
		this.btnAnnuler        = new JButton(iconAnnuler       );
		this.btnRepeter	       = new JButton(iconRepeter       );
		this.btnZoom           = new JButton(iconZoom          );
		this.btnDezoom         = new JButton(iconDezoom        );
		this.btnRedimensionner = new JButton(iconRedimensionner);
		this.btnRotation       = new JButton(iconRotation      );
		this.btnMiroirH        = new JButton(iconMiroirH       );
		this.btnMiroirV        = new JButton(iconMiroirV       );
		this.btnPotPeinture    = new JButton(iconPotPeinture   );

		// Configuration des boutons
		dimension = new Dimension(40, 40);
		this.btnOuvrir        .setPreferredSize(dimension);
		this.btnEnregistrer   .setPreferredSize(dimension);
		this.btnAnnuler       .setPreferredSize(dimension);
		this.btnRepeter       .setPreferredSize(dimension);
		this.btnZoom          .setPreferredSize(dimension);
		this.btnDezoom        .setPreferredSize(dimension);
		this.btnRedimensionner.setPreferredSize(dimension);
		this.btnRotation      .setPreferredSize(dimension);
		this.btnMiroirH       .setPreferredSize(dimension);
		this.btnMiroirV       .setPreferredSize(dimension);
		this.btnPotPeinture   .setPreferredSize(dimension);

		/* Ajout des composants */
		this.add(this.btnOuvrir        );
		this.add(this.btnEnregistrer   );
		this.add(this.btnAnnuler       );
		this.add(this.btnRepeter       );
		this.add(this.btnZoom          );
		this.add(this.btnDezoom        );
		this.add(this.btnRedimensionner);
		this.add(this.btnRotation      );
		this.add(this.btnMiroirH       );
		this.add(this.btnMiroirV       );
		this.add(this.btnPotPeinture   );

		/* Activation des composants */
		this.btnOuvrir        .addActionListener(this);
		this.btnEnregistrer   .addActionListener(this);
		this.btnAnnuler       .addActionListener(this);
		this.btnRepeter       .addActionListener(this);
		this.btnZoom          .addActionListener(this);
		this.btnDezoom        .addActionListener(this);
		this.btnRedimensionner.addActionListener(this);
		this.btnRotation      .addActionListener(this);
		this.btnMiroirH       .addActionListener(this);
		this.btnMiroirV       .addActionListener(this);
		this.btnPotPeinture   .addActionListener(this);
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
		else if (e.getSource() == this.btnRedimensionner)
		{
			System.out.println("Redimensionner !");
		}
		else if (e.getSource() == this.btnRotation)
		{
			System.out.println("Rotation !");
		}
		else if (e.getSource() == this.btnMiroirH)
		{
			System.out.println("Miroir Horizontal !");
		}
		else if (e.getSource() == this.btnMiroirV)
		{
			System.out.println("Miroir Vertical !");
		}
		else if (e.getSource() == this.btnPotPeinture)
		{
			System.out.println("Pot de Peinture !");
		}
	}
}
