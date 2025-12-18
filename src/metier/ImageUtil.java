package metier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Classe utilitaire pour les images
 * @author Equipe 5
 */
public class ImageUtil
{
	private BufferedImage img;
	private String        cheminImage;
	private int           largeur;
	private int           hauteur;
	private int           x0, y0;

	/**
	 * Constructeur
	 * 
	 * @param srcImg
	 */
	public ImageUtil(String srcImg)
	{
		this(srcImg, 0, 0);
	}

	public ImageUtil(String srcImg, int x0, int y0)
	{
		this.cheminImage = srcImg;
		
		try 
		{
			this.img = ImageIO.read(new File(this.cheminImage));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.largeur = this.img.getWidth();
		this.hauteur = this.img.getHeight();
		this.x0      = x0;
		this.y0      = y0;
	}

	public boolean contient(int x, int y)
	{
		return (x >= this.x0 && x < this.largeur + this.x0 && y >= this.y0 && y < this.hauteur + this.y0);
	}

	public int            getX0()          { return this.x0;            }
	public int            getY0()          { return this.y0;            }
	public void           setX0(int x0)    { this.x0 = x0;              }
	public void           setY0(int y0)    { this.y0 = y0;              }
	public int            getLargeur()     { return this.largeur;       }
	public int            getHauteur()     { return this.hauteur;       }
	public String         getCheminImage() { return this.cheminImage;   }
	public BufferedImage  getImage()       { return this.img;           }
	public void           setImage(BufferedImage img) { this.img = img; }

	/**
	 * Sauvegarder l'image au format PNG
	 * @param destImg
	 */
	public void sauvegarderImage(String destImg)
	{
		this.sauvegarderImage(destImg, "png");
	}

	/**
	 * Sauvegarder l'image
	 * 
	 * @param destImg
	 * @param format  Format de l'image (png, jpg, bmp, etc.)
	 */
	private void sauvegarderImage(String destImg, String format)
	{
		try
		{
			ImageIO.write(this.img, format, new File(destImg));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Afficher le détail des couleurs d'un pixel
	 * 
	 * @param couleur
	 * @return List<Integer> des composantes RGB
	 */
	public List<Integer> afficherDetailCouleur(int couleur)
	{
		List<Integer> rgb;
		int           rouge, vert, bleu;

		rgb = new ArrayList<>();
		
		rouge = couleur / (256*256); 
		vert  = couleur / 256 % 256;
		bleu  = couleur % 256;

		rgb.add(rouge);
		rgb.add(vert);
		rgb.add(bleu);

		return rgb;
	}

	/**
	 * Calculer la luminance d'une couleur (ITU-R BT.601)
	 * 
	 * @param c La couleur
	 * @return int luminance (0-255)
	 */
	public static int luminance(Color c)
	{
		int rouge, vert, bleu;

		rouge = c.getRed();
		vert  = c.getGreen();
		bleu  = c.getBlue();

		return (rouge * 299 + vert * 587 + bleu * 114) / 1000;
	}
}
