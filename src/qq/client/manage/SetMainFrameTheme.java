package qq.client.manage;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class SetMainFrameTheme {
	
	public static ImageIcon setClip(ImageIcon tobecut){
		BufferedImage bf = toBufferedImage(tobecut.getImage());
		return new ImageIcon(setClip(bf, 50));
		
	}
	
	
	public static BufferedImage setClip(BufferedImage srcImage, int radius){
	        int width = srcImage.getWidth();
	        int height = srcImage.getHeight();
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D gs = image.createGraphics();

	        gs.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        gs.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
	        gs.drawImage(srcImage, 0, 0, null);
	        gs.dispose();
	        return image;
	}
	public static BufferedImage toBufferedImage(Image image) {  
	    if (image instanceof BufferedImage) {  
	        return (BufferedImage)image;  
	     }  
	    
	    // This code ensures that all the pixels in the image are loaded  
	     image = new ImageIcon(image).getImage();  
	    
	    // Determine if the image has transparent pixels; for this method's  
	    // implementation, see e661 Determining If an Image Has Transparent Pixels  
	    //boolean hasAlpha = hasAlpha(image);  
	    
	    // Create a buffered image with a format that's compatible with the screen  
	     BufferedImage bimage = null;  
	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
	    try {  
	        // Determine the type of transparency of the new buffered image  
	        int transparency = Transparency.OPAQUE;  
	       /* if (hasAlpha) { 
	         transparency = Transparency.BITMASK; 
	         }*/  
	    
	        // Create the buffered image  
	         GraphicsDevice gs = ge.getDefaultScreenDevice();  
	         GraphicsConfiguration gc = gs.getDefaultConfiguration();  
	         bimage = gc.createCompatibleImage(  
	         image.getWidth(null), image.getHeight(null), transparency);  
	     } catch (HeadlessException e) {  
	        // The system does not have a screen  
	     }  
	    
	    if (bimage == null) {  
	        // Create a buffered image using the default color model  
	        int type = BufferedImage.TYPE_INT_RGB;  
	        //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang  
	        /*if (hasAlpha) { 
	         type = BufferedImage.TYPE_INT_ARGB; 
	         }*/  
	         bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);  
	     }  
	    
	    // Copy image to buffered image  
	     Graphics g = bimage.createGraphics();  
	    
	    // Paint the image onto the buffered image  
	     g.drawImage(image, 0, 0, null);  
	     g.dispose();  
	    
	    return bimage;  
	}

}
