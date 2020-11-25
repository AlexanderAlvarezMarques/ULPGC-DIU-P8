package beans;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

/**
 * @author Alexander Álvarez Marques
 * @date 2020-11-17
 * @version 0.1
 */
public class BackgroundImage extends JPanel {

    // Attributes
    private BufferedImage bgImage;
    Mat mat;
    private int width = 0;
    private int height = 0;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, width, height, null);
    }

    public void clear() {
        bgImage = null;
        mat = null;
        width = 0;
        height = 0;
        repaint();
    }

    public Mat applyFilter(Mat img, int value) {
        Mat umb = umbralizar(img, value);
        return umb;
    }

    public void setImage(BufferedImage img) {
        bgImage = img;
    }

    public void setMat(Mat mat) {
        this.mat = mat;
    }

    public Mat getMat() {
        return mat;
    }

    public void paintImage(int width, int height) {
        this.width = width;
        this.height = height;
        repaint();
    }

    public boolean isUmbralized(Mat imagen_original) {
        try {
            Mat imagenGris = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
            Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);

            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private Mat umbralizar(Mat imagen_original, Integer umbral) {

        // crear dos imágenes en niveles de gris con el mismo
        // tamaño que la original
        Mat imagenGris = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);
        Mat imagenUmbralizada = new Mat(imagen_original.rows(), imagen_original.cols(), CvType.CV_8U);

        // convierte a niveles de grises la imagen original
        Imgproc.cvtColor(imagen_original, imagenGris, Imgproc.COLOR_BGR2GRAY);

        // umbraliza la imagen:
        // - píxeles con nivel de gris > umbral se ponen a 1
        // - píxeles con nivel de gris <= umbra se ponen a 0
        Imgproc.threshold(imagenGris, imagenUmbralizada, umbral, 255, Imgproc.THRESH_BINARY);

        // se devuelve la imagen umbralizada
        return imagenUmbralizada;
    }

}
