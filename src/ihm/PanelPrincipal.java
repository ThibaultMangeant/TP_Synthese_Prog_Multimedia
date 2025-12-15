package ihm;

import javax.swing.*;

import java.awt.BorderLayout;

public class PanelPrincipal extends JPanel
{
	private JPanel panelImage;
	private JLabel image;

	public PanelPrincipal()
	{
		this.panelImage = new JPanel(new BorderLayout());

		this.image = new JLabel(new ImageIcon("images/david_tennant.png"));
		this.image.setOpaque(false);
		this.panelImage.add(this.image, BorderLayout.CENTER);
		this.panelImage.setOpaque(false);
		
		this.add(panelImage, BorderLayout.CENTER);
	}
}