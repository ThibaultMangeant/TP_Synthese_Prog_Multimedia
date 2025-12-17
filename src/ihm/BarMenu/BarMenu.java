package ihm.BarMenu;

import javax.swing.JMenuBar;

import controleur.Controleur;
 
public class BarMenu extends JMenuBar
{
	public BarMenu(Controleur controleur)
	{
		MenuFichier    menuFichier;
		MenuEdition    menuEdition;
		MenuAffichage  menuAffichage;
		MenuImage      menuImage;
		MenuAjustement menuAjustement;


		/* Création des menus */
		menuFichier    = new MenuFichier   (controleur);
		menuEdition    = new MenuEdition   (controleur);
		menuAffichage  = new MenuAffichage (controleur);
		menuImage      = new MenuImage     (controleur);
		menuAjustement = new MenuAjustement(controleur);

		/* Positionnement des composants */
		this.add(menuFichier);
		this.add(menuEdition);
		this.add(menuAffichage);
		this.add(menuImage);
		this.add(menuAjustement);
	}
}