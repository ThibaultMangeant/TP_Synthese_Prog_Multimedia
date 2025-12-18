package metier;

/**
 * Classe de test pour la fusion d'images avec effet de fondu
 * @author Equipe 5
 */

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.imageio.ImageIO;

public class TestFusion
{
	public static void main(String[] arguments)
	{
		BufferedImage   imageRouge;
		BufferedImage   imageBleue;
		Graphics2D      graphiqueRouge;
		Graphics2D      graphiqueBleue;
		Fusion          fusion1, fusion2, fusion3;

		System.out.println("=== Test de Fusion d'Images avec Fondu ===\n");

		// Création de deux images de couleur unie pour mieux voir le fondu
		System.out.println("Création des images de test...");
		try
		{
			imageRouge        = new BufferedImage(300, 400, BufferedImage.TYPE_INT_RGB);
			graphiqueRouge    = imageRouge.createGraphics();
			graphiqueRouge.setColor(Color.RED);
			graphiqueRouge.fillRect(0, 0, 300, 400);
			graphiqueRouge.dispose();
			ImageIO.write(imageRouge, "png", new java.io.File("../src/images/test_rouge.png"));
			System.out.println("✓ Image rouge créée");

			imageBleue        = new BufferedImage(300, 400, BufferedImage.TYPE_INT_RGB);
			graphiqueBleue    = imageBleue.createGraphics();
			graphiqueBleue.setColor(Color.BLUE);
			graphiqueBleue.fillRect(0, 0, 300, 400);
			graphiqueBleue.dispose();
			ImageIO.write(imageBleue, "png", new java.io.File("../src/images/test_bleue.png"));
			System.out.println("✓ Image bleue créée");
		}
		catch (Exception exception)
		{
			System.out.println("✗ Erreur lors de la création des images : " + exception.getMessage());
			exception.printStackTrace();
			return;
		}

		System.out.println();

		// Test 1 : Fusion avec fondu de 100 pixels
		System.out.println("Test 1 : Fusion rouge-bleue avec fondu de 100 pixels");
		try
		{
			fusion1 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test1.png"
			);
			fusion1.fusionnerAvecFondu(100);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test1.png");
		}
		catch (Exception exception)
		{
			System.out.println("✗ Erreur : " + exception.getMessage());
			exception.printStackTrace();
		}

		System.out.println();

		// Test 2 : Fusion avec fondu de 200 pixels
		System.out.println("Test 2 : Fusion rouge-bleue avec fondu de 200 pixels");
		try
		{
			fusion2 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test2.png"
			);
			fusion2.fusionnerAvecFondu(200);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test2.png");
		}
		catch (Exception exception)
		{
			System.out.println("✗ Erreur : " + exception.getMessage());
			exception.printStackTrace();
		}

		System.out.println();

		// Test 3 : Fusion avec fondu de 50 pixels
		System.out.println("Test 3 : Fusion rouge-bleue avec fondu de 50 pixels");
		try
		{
			fusion3 = new Fusion(
				"../src/images/test_rouge.png",
				"../src/images/test_bleue.png",
				"../src/images/fusion_test3.png"
			);
			fusion3.fusionnerAvecFondu(50);
			System.out.println("✓ Image fusionnée sauvegardée : fusion_test3.png");
		}
		catch (Exception exception)
		{
			System.out.println("✗ Erreur : " + exception.getMessage());
			exception.printStackTrace();
		}

		System.out.println("\n=== Tests terminés ===");
		System.out.println("Vérifiez les images dans ../src/images/");
		System.out.println("- test_rouge.png et test_bleue.png : images sources");
		System.out.println("- fusion_test1.png : fondu de 100 pixels");
		System.out.println("- fusion_test2.png : fondu de 200 pixels");
		System.out.println("- fusion_test3.png : fondu de 50 pixels");
	}
}
