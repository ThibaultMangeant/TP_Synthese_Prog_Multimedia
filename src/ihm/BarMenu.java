package ihm;

import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controleur.Controleur;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class BarMenu extends JMenuBar implements ActionListener
{
	private JMenu menuFichier;
	private JMenu menuEdition;

	private JMenuItem itemQuitter;
	private JMenuItem itemOuvrir;

	private JMenuItem itemRotation;
	private JMenuItem itemMiroirH;
	private JMenuItem itemMiroirV;
	private JMenuItem itemRedimensionner;
	private JMenuItem itemPeinture;
	private JMenuItem itemTeinte;
	private JMenuItem itemAntiAliasing;
	private JMenuItem itemContraste;
	private JMenuItem itemSuperposition;
	private JMenuItem itemTexteImage;

    private Controleur controleur;

	public BarMenu(Controleur controleur)
	{
		this.controleur = controleur;
		/* Création des menus */
		this.menuFichier = new JMenu("Fichier");
		this.menuEdition = new JMenu("Édition");
		
		this.itemOuvrir = new JMenuItem("Ouvrir");
		this.itemQuitter = new JMenuItem("Quitter");

		this.itemRotation = new JMenuItem("Rotation");
		this.itemMiroirH = new JMenuItem("Miroir Horizontal");
		this.itemMiroirV = new JMenuItem("Miroir Vertical");
		this.itemRedimensionner = new JMenuItem("Redimensionner");
		this.itemPeinture = new JMenuItem("Effet Peinture");
		this.itemTeinte = new JMenuItem("Ajuster Teinte");
		this.itemAntiAliasing = new JMenuItem("Anti-Aliasing");
		this.itemContraste = new JMenuItem("Ajuster Contraste");
		this.itemSuperposition = new JMenuItem("Superposition d'Images");
		this.itemTexteImage = new JMenuItem("Créer une Image à partir de Texte");

		/* Ajout des items au menu Fichier */
		menuFichier.add(itemOuvrir);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);

		/* Ajout des items au menu Édition */
		menuEdition.add(itemRotation);
		menuEdition.add(itemMiroirH);
		menuEdition.add(itemMiroirV);
		menuEdition.add(itemRedimensionner);
		menuEdition.add(itemPeinture);
		menuEdition.add(itemTeinte);
		menuEdition.add(itemAntiAliasing);
		menuEdition.add(itemContraste);
		menuEdition.add(itemSuperposition);
		menuEdition.add(itemTexteImage);


		/* Positionnement des composants */
		this.add(menuFichier);
		this.add(menuEdition);


		/* Activation des composants */
		this.itemQuitter.addActionListener(this);
		this.itemOuvrir.addActionListener(this);

		this.itemRotation.addActionListener(this);
		this.itemMiroirH.addActionListener(this);
		this.itemMiroirV.addActionListener(this);
		this.itemRedimensionner.addActionListener(this);
		this.itemPeinture.addActionListener(this);
		this.itemTeinte.addActionListener(this);
		this.itemAntiAliasing.addActionListener(this);
		this.itemContraste.addActionListener(this);
		this.itemSuperposition.addActionListener(this);
		this.itemTexteImage.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.itemQuitter)
		{
			System.exit(0);
		}

		if (e.getSource() == this.itemOuvrir)
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Images (png)", "png"));
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				java.io.File selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				if (path.toLowerCase().endsWith(".png")) {
					System.out.println(path);
					if (this.controleur != null)
					{
						this.controleur.ouvrirImage(path);
					}
				} else {
					javax.swing.JOptionPane.showMessageDialog(null, "Veuillez sélectionner un fichier PNG.", "Format invalide", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}