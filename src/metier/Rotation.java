package metier;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;

import metier.ImageUtil;


/**
 * Classe pour faire des rotations d'images
 */
public class Rotation
{

	public ImageUtil imageUtil;
	String fichierSource;
	String fichierDest;


	/**
	 * Constructeur
	 * 
	 * @param fichierSource Chemin de l'image source
	 * @param fichierDest Chemin du fichier destination
	 */
	public Rotation(String fichierSource, String fichierDest)
	{
		this.fichierSource = fichierSource;
		this.fichierDest   = fichierDest;
		this.imageUtil     = new ImageUtil(fichierSource);
	}
    
	/**
	 * Effectue une rotation de l'image d'un angle en degrés (horaire),
	 * en centrant la rotation et en ajustant la taille de sortie pour
	 * éviter le découpage.
	 * @param degrees Angle en degrés
	 */
	public void rotation(double degrees)
	{
		BufferedImage src = this.imageUtil.getImage();
		int w = src.getWidth();
		int h = src.getHeight();

		double radians = Math.toRadians(degrees);
		double cos = Math.abs(Math.cos(radians));
		double sin = Math.abs(Math.sin(radians));
		int newW = (int)Math.floor(w * cos + h * sin);
		int newH = (int)Math.floor(h * cos + w * sin);

		int type = (src.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : src.getType();
		BufferedImage out = new BufferedImage(newW, newH, type);
		Graphics2D g2d = out.createGraphics();
		try {
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			AffineTransform at = new AffineTransform();
			at.translate(newW / 2.0, newH / 2.0);
			at.rotate(radians);
			at.translate(-w / 2.0, -h / 2.0);

			g2d.drawImage(src, at, null);
		} finally {
			g2d.dispose();
		}

		this.imageUtil.setImage(out);
		this.imageUtil.sauvegarderImage(this.fichierDest);
	}

	/**
	 * Rotation 90° (horaire)
	 */
	public void rotation90()
	{
		rotation(90.0);
	}

	/**
	 * Rotation 180°
	 */
	public void rotation180()
	{
		rotation(180.0);
	}

	/**
	 * Rotation 270° (horaire)
	 */
	public void rotation270()
	{
		rotation(270.0);
	}

}
