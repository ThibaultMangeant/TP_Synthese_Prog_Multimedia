package ihm;

import controleur.Controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

/** Barre d'outils de l'application
 * @author Equipe 5
 */
public class BarreOutil extends JPanel implements ActionListener
{
	private Controleur controleur;

	private JButton btnOuvrir        , btnEnregistrer;
	private JButton btnAnnuler       , btnRepeter;
	private JButton btnZoom          , btnDezoom;
	private JButton btnRedimensionner, btnRotation;
	private JButton btnMiroirH       , btnMiroirV;
	private JButton btnPotPeinture;

	public BarreOutil(Controleur controleur)
	{
		Dimension dimension;
		ImageIcon iconOuvrir, iconEnregistrer;
		ImageIcon iconAnnuler, iconRepeter;
		ImageIcon iconZoom, iconDezoom;
		ImageIcon iconRedimensionner, iconRotation;
		ImageIcon iconMiroirH, iconMiroirV;
		ImageIcon iconPotPeinture;

		this.controleur = controleur;

		/* Configuration du JPanel */
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(230, 230, 230));

		/* Création des composants */
		// Icones
		iconOuvrir         = new ImageIcon("../src/images/icons/iconOuvrir.png"        );
		iconEnregistrer    = new ImageIcon("../src/images/icons/iconEnregistrer.png"   );
		iconAnnuler        = new ImageIcon("../src/images/icons/iconAnnuler.png"       );
		iconRepeter        = new ImageIcon("../src/images/icons/iconRepeter.png"       );
		iconZoom           = new ImageIcon("../src/images/icons/iconZoom.png"          );
		iconDezoom         = new ImageIcon("../src/images/icons/iconDezoom.png"        );
		iconRedimensionner = new ImageIcon("../src/images/icons/iconRedimensionner.png");
		iconRotation       = new ImageIcon("../src/images/icons/iconRotation.png"      );
		iconMiroirH        = new ImageIcon("../src/images/icons/iconMiroirH.png"       );
		iconMiroirV        = new ImageIcon("../src/images/icons/iconMiroirV.png"       );
		iconPotPeinture    = new ImageIcon("../src/images/icons/iconPotPeinture.png"   );

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

		// Infobulles
		this.btnOuvrir        .setToolTipText("Ouvrir une image"                   );
		this.btnEnregistrer   .setToolTipText("Enregistrer l'image"                );
		this.btnAnnuler       .setToolTipText("Annuler la dernière action"         );
		this.btnRepeter       .setToolTipText("Rétablir la dernière action annulée");
		this.btnZoom          .setToolTipText("Zoom avant"                         );
		this.btnDezoom        .setToolTipText("Zoom arrière"                       );
		this.btnRedimensionner.setToolTipText("Redimensionner l'image"             );
		this.btnRotation      .setToolTipText("Faire une rotation"                 );
		this.btnMiroirH       .setToolTipText("Miroir horizontal"                  );
		this.btnMiroirV       .setToolTipText("Miroir vertical"                    );
		this.btnPotPeinture   .setToolTipText("Activer le pot de peinture"         );

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
			this.controleur.zoomAvant();
		}
		else if (e.getSource() == this.btnDezoom)
		{
			this.controleur.zoomArriere();
		}
		else if (e.getSource() == this.btnOuvrir)
		{
			JFileChooser selecteurFichier;
			int          result;
			File         fichierChoisi;
			String       chemin;

			selecteurFichier = new JFileChooser();
			selecteurFichier.setAcceptAllFileFilterUsed(false);
			selecteurFichier.setFileFilter(new FileNameExtensionFilter("Images (png)", "png"));

			result = selecteurFichier.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION)
			{
				fichierChoisi = selecteurFichier.getSelectedFile();
				chemin = fichierChoisi.getAbsolutePath();
				if (chemin.toLowerCase().endsWith(".png"))
				{
					System.out.println(chemin);
					if (this.controleur != null)
					{
						this.controleur.ouvrirImage(chemin);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier PNG.", "Format invalide", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (result == JFileChooser.CANCEL_OPTION)
			{
				System.out.println("Opération annulée par l'utilisateur.");
			}
			else if (result == JFileChooser.ERROR_OPTION)
			{
				System.out.println("Une erreur est survenue lors de la sélection du fichier.");
			}
		}
		else if (e.getSource() == this.btnEnregistrer)
		{
			this.controleur.sauvegarderImage();
		}
		else if (e.getSource() == this.btnAnnuler)
		{
			this.controleur.annuler();
		}
		else if (e.getSource() == this.btnRepeter)
		{
			this.controleur.refaire();
		}
		else if (e.getSource() == this.btnRedimensionner)
		{
			int nouvelleLargeur, nouvelleHauteur;
			
			nouvelleLargeur = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle largeur : "));
			nouvelleHauteur = Integer.parseInt(JOptionPane.showInputDialog("Rentrer la nouvelle hauteur : "));

			this.controleur.redimensionner(nouvelleLargeur, nouvelleHauteur);
		}
		else if (e.getSource() == this.btnRotation)
		{
			int angle;
			
			angle = Integer.parseInt(JOptionPane.showInputDialog("Rentrer l'angle : "));

			this.controleur.rotation(angle);
		}
		else if (e.getSource() == this.btnMiroirH)
		{
			this.controleur.miroirHorizontal();
		}
		else if (e.getSource() == this.btnMiroirV)
		{
			this.controleur.miroirVertical();
		}
		else if (e.getSource() == this.btnPotPeinture)
		{
			this.controleur.activerModeRemplirCouleur();
		}
	}
}
