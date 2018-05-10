package basicgraphics.finalproject;
import basicgraphics.BasicFrame;
import basicgraphics.images.Picture;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
public class FlipLeftRightPicture extends Picture{
    public FlipLeftRightPicture(String name) {
        super(name);
    }
    public FlipLeftRightPicture(Image image) {
        super(image);
    }
    public Picture flipLeftRight() {
        BufferedImage bufferedImage = (BufferedImage) getImage();
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bi = BasicFrame.createImage(w,h);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        AffineTransform xform = new AffineTransform();
        xform.setToScale(-1, 1);
        xform.translate(-bufferedImage.getWidth(), 0);
        g2.drawImage(bufferedImage, xform, this);
        return new Picture(bi);
    }
}