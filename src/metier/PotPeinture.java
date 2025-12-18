package metier;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Classe pour l'outil pot de peinture (remplissage et suppression de fond)
 * @author Equipe 5
 */
public class PotPeinture
{
	/**
	 * Retire une couleur et ses voisines proches de l'image (rend transparent)
	 * @param imgUtil ImageUtil contenant l'image
	 * @param position Position du clic
	 * @param tolerance Tolerance de couleur (0-255)
	 */
	public static void retirerCouleur(ImageUtil imgUtil, Point position, int tolerance)
	{
		Color couleurCible;

		if (position.x() < 0 || position.x() >= imgUtil.getLargeur() || position.y() < 0 || position.y() >= imgUtil.getHauteur())
		{
			return;
		}
		
		// Recuperer la couleur cliquee
		couleurCible = new Color(imgUtil.getImage().getRGB(position.x(), position.y()));
		
		// Algorithme de remplissage pour retirer la couleur
		PotPeinture.retirerCouleurIteratif(imgUtil, position.x(), position.y(), couleurCible, tolerance);
	}
	
	/**
	 * Retire une couleur et ses voisines proches de l'image (rend transparent)
	 * @param imgUtil ImageUtil contenant l'image
	 * @param x Position X du clic
	 * @param y Position Y du clic
	 * @param tolerance Tolerance de couleur (0-255)
	 */
	public static void retirerCouleur(ImageUtil imgUtil, int x, int y, int tolerance)
	{
		PotPeinture.retirerCouleur(imgUtil, new Point(x, y), tolerance);
	}
	
	/**
	 * Algorithme iteratif pour retirer une zone de couleur
	 */
	private static void retirerCouleurIteratif(ImageUtil imgUtil, int x, int y, Color couleurCible, int tolerance)
	{
		Queue<Point> file;
		boolean[][]  visite;
		Point        p;
		Color        couleurActuelle;
		int          rgb, transparent;

		file   = new LinkedList<>();
		visite = new boolean[imgUtil.getLargeur()][imgUtil.getHauteur()];
		
		file.add(new Point(x, y));
		
		while (!file.isEmpty())
		{
			p = file.poll();
			
			// Verifier les limites
			if (p.x() < 0 || p.x() >= imgUtil.getLargeur() || p.y() < 0 || p.y() >= imgUtil.getHauteur()) { continue; }
			
			// Si deja visite, passer
			if (visite[p.x()][p.y()]) { continue; }
			
			couleurActuelle = new Color(imgUtil.getImage().getRGB(p.x(), p.y()), true);
			
			// Verifier si la couleur est proche de la couleur cible
			if (!PotPeinture.couleurProche(couleurActuelle, couleurCible, tolerance)) { continue; }
			
			// Marquer comme visite
			visite[p.x()][p.y()] = true;
			
			// Rendre transparent (Alpha = 0)
			rgb         = imgUtil.getImage().getRGB(p.x(), p.y());
			transparent = rgb & 0x00FFFFFF; // Garde RGB, met Alpha a 0
			imgUtil.getImage().setRGB(p.x(), p.y(), transparent);
			
			// Ajouter les 4 voisins (haut, bas, gauche, droite)
			file.add(new Point(p.x() + 1, p.y()    ));
			file.add(new Point(p.x() - 1, p.y()    ));
			file.add(new Point(p.x()    , p.y() + 1));
			file.add(new Point(p.x()    , p.y() - 1));
		}
	}
	
	/**
	 * Remplit une zone avec une couleur
	 * @param imgUtil ImageUtil contenant l'image
	 * @param position Position du clic
	 * @param nouvelleCouleur Couleur de remplissage
	 * @param tolerance Tolerance
	 */
	public static void remplir(ImageUtil imgUtil, Point position, Color nouvelleCouleur, int tolerance)
	{
		Color couleurCible;

		if (position.x() < 0 || position.x() >= imgUtil.getLargeur() || position.y() < 0 || position.y() >= imgUtil.getHauteur()) { return; }
		
		couleurCible = new Color(imgUtil.getImage().getRGB(position.x(), position.y()));
		
		// Si meme couleur, ne rien faire
		if (PotPeinture.couleurProche(couleurCible, nouvelleCouleur, 0)) { return; }
		
		PotPeinture.remplirIteratif(imgUtil, position.x(), position.y(), couleurCible, nouvelleCouleur, tolerance);
	}
	
	/**
	 * Remplit une zone avec une couleur
	 * @param imgUtil ImageUtil contenant l'image
	 * @param x Position X du clic
	 * @param y Position Y du clic
	 * @param nouvelleCouleur Couleur de remplissage
	 * @param tolerance Tolerance
	 */
	public static void remplir(ImageUtil imgUtil, int x, int y, Color nouvelleCouleur, int tolerance)
	{
		PotPeinture.remplir(imgUtil, new Point(x, y), nouvelleCouleur, tolerance);
	}
	
	/**
	 * Algorithme iteratif de remplissage
	 */
	private static void remplirIteratif(ImageUtil imgUtil, int x, int y, Color couleurCible, Color nouvelleCouleur, int tolerance)
	{
		Queue<Point> file;
		boolean[][]  visite;
		Point        p;
		Color        couleurActuelle;
		int          argb;

		file   = new LinkedList<>();
		visite = new boolean[imgUtil.getLargeur()][imgUtil.getHauteur()];
		
		file.add(new Point(x, y));
		
		while (!file.isEmpty())
		{
			p = file.poll();
			
			if (p.x() < 0 || p.x() >= imgUtil.getLargeur() || p.y() < 0 || p.y() >= imgUtil.getHauteur()) { continue; }
			
			if (visite[p.x()][p.y()]) { continue; }
			
			couleurActuelle = new Color(imgUtil.getImage().getRGB(p.x(), p.y()), true);
			
			if (!PotPeinture.couleurProche(couleurActuelle, couleurCible, tolerance)) { continue; }
			
			visite[p.x()][p.y()] = true;
			
			// Construire ARGB avec alpha opaque
			argb = (255 << 24) | (nouvelleCouleur.getRGB() & 0x00FFFFFF);
			imgUtil.getImage().setRGB(p.x(), p.y(), argb);
			
			// Ajouter les voisins
			file.add(new Point(p.x() + 1, p.y()    ));
			file.add(new Point(p.x() - 1, p.y()    ));
			file.add(new Point(p.x()    , p.y() + 1));
			file.add(new Point(p.x()    , p.y() - 1));
		}
	}
	
	/**
	 * Verifie si deux couleurs sont proches selon une tolerance
	 */
	private static boolean couleurProche(Color c1, Color c2, int tolerance)
	{
		int diffRouge, diffVert, diffBleu;

		diffRouge = Math.abs(c1.getRed  () - c2.getRed  ());
		diffVert  = Math.abs(c1.getGreen() - c2.getGreen());
		diffBleu  = Math.abs(c1.getBlue () - c2.getBlue ());
		
		return diffRouge <= tolerance && diffVert <= tolerance && diffBleu <= tolerance;
	}
}
