package ihm;

import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controleur.Controleur;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class BarMenu extends JMenuBar implements ActionListener
{
	private JMenuItem itemQuitter;
	private JMenuItem itemOuvrir;
    private Controleur controleur;

	public BarMenu(Controleur controleur)
	{
		this.controleur = controleur;
		/* Création des menus */
		JMenu menuFichier = new JMenu("Fichier");
		JMenu menuEdition = new JMenu("Édition");
		
		this.itemOuvrir = new JMenuItem("Ouvrir");
		this.itemQuitter = new JMenuItem("Quitter");


		/* Ajout des items au menu Fichier */
		menuFichier.add(itemOuvrir);
		menuFichier.addSeparator();
		menuFichier.add(itemQuitter);

		/* Ajout des items au menu Édition */
		menuEdition.add(new JMenuItem("Paramètres"));


		/* Positionnement des composants */
		this.add(menuFichier);
		this.add(menuEdition);


		/* Activation des composants */
		this.itemQuitter.addActionListener(this);
		this.itemOuvrir.addActionListener(this);
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
			fileChooser.setAcceptAllFileFilterUsed(true);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Images (png)", "png"));
			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				java.io.File selectedFile = fileChooser.getSelectedFile();
				String path = selectedFile.getAbsolutePath();
				System.out.println(path);
				if (this.controleur != null)
				{
					this.controleur.ouvrirImage(path);
				}
			}
		}
	}
}
