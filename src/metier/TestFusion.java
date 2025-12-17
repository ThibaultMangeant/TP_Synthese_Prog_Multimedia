package metier;

/**
 * Classe de test pour la fusion d'images avec effet de fondu
 * @author Equipe 5
 */
public class TestFusion
{
	public static void main(String[] args)
	{
		System.out.println("=== Test de Fusion d'Images avec Fondu ===\n");

		// Création de deux images de couleur unie pour mieux voir le fondu
		System.out.println("Création des images de test...");
		try
		{
			// Image rouge (300x400)
			java.awt.image.BufferedImage imgRouge = new java.awt.image.BufferedImage(300, 400, java.awt.image.BufferedImage.TYPE_INT_RGB);
			java.awt.Graphics2D g1 = imgRouge.createGraphics();
			g1.setColor(java.awt.Color.RED);
			g1.fillRect(0, 0, 300, 400);
			g1.dispose();
			javax.imageio.ImageIO.write(imgRouge, "png", new java.io.File("../src/images/test_rouge.png"));
			System.out.println("✓ Image rouge créée");

			// Image bleue (300x400)
			java.awt.image.BufferedImage imgBleue = new java.awt.image.BufferedImage(300, 400, java.awt.image.BufferedImage.TYPE_INT_RGB);
			java.awt.Graphics2D g2 = imgBleue.createGraphics();
			g2.setColor(java.awt.Color.BLUE);
			g2.fillRect(0, 0, 300, 400);
			g2.dispose();
			javax.imageio.ImageIO.write(imgBleue, "png", new java.io.File("../src/images/test_bleue.png"));
			System.out.println("✓ Image bleue créée");
		}
		catch (Exception e)
		{
			System.out.println("✗ Erreur lors de la création des images : " + e.getMessage());
			e.printStackTrace();
			return;
		}

		System.out.println();

		// Test 1 : Fusion avec fondu de 100 pixels
		System.out.println("Test 1 : Fusion rouge-bleue avec fondu de 100 pixels");
		try
		{
			Fusion fusion1 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test1.png"
			);
			fusion1.fusionnerAvecFondu(100);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test1.png");
		}
		catch (Exception e)
		{
			System.out.println("✗ Erreur : " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println();

		// Test 2 : Fusion avec fondu de 200 pixels
		System.out.println("Test 2 : Fusion rouge-bleue avec fondu de 200 pixels");
		try
		{
			Fusion fusion2 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test2.png"
			);
			fusion2.fusionnerAvecFondu(200);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test2.png");
		}
		catch (Exception e)
		{
			System.out.println("✗ Erreur : " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println();

		// Test 3 : Fusion avec fondu de 50 pixels
		System.out.println("Test 3 : Fusion rouge-bleue avec fondu de 50 pixels");
		try
		{
			Fusion fusion3 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test3.png"
			);
			fusion3.fusionnerAvecFondu(50);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test3.png");
		}
		catch (Exception e)
		{
			System.out.println("✗ Erreur : " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("\n=== Tests terminés ===");
		System.out.println("Vérifiez les images dans ../src/images/");
		System.out.println("- test_rouge.png et test_bleue.png : images sources");
		System.out.println("- fusion_test1.png : fondu de 100 pixels");
		System.out.println("- fusion_test2.png : fondu de 200 pixels");
		System.out.println("- fusion_test3.png : fondu de 50 pixels");
	}
}
