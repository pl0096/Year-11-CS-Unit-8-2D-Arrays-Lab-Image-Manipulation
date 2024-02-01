package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        String fileName = "cyberpunk2077.jpg";
        APImage image = new APImage("cyberpunk2077.jpg");
        grayScale(fileName);
        blackAndWhite(fileName);
        edgeDetection(fileName, 20);
        reflectImage(fileName);
        rotateImage(fileName);

    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int xMax = image.getWidth();
        int yMax = image.getHeight();
        int val;
        for(int y = 0; y < yMax; y++){
            for(int x = 0; x < xMax; x++){
                Pixel p = image.getPixel(x, y);
                val = getAverageColour(p);
                p.setRed(val);
                p.setGreen(val);
                p.setBlue(val);
                image.setPixel(x, y, p);


            }
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getBlue() + pixel.getGreen() + pixel.getRed()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int xMax = image.getWidth();
        int yMax = image.getHeight();
        int val;
        for(int y = 0; y < yMax; y++){
            for(int x = 0; x < xMax; x++){
                Pixel p = image.getPixel(x, y);
                val = getAverageColour(p);
                if(val >= 128){
                    val = 255;
                }
                else{
                    val = 0;
                }
                p.setRed(val);
                p.setGreen(val);
                p.setBlue(val);
                image.setPixel(x, y, p);


            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        APImage origin = new APImage(pathToFile);
        int xMax = image.getWidth();
        int yMax = image.getHeight();
        int val;
        int leftVal;
        int bottomVal;
        boolean left = true;
        boolean bottom = true;
        for(int y = 0; y < yMax; y++){
            if(y == yMax -1){
                bottom = false;
            }
            left = false;
            for(int x = 0; x < xMax; x++){
                Pixel lp = null;
                Pixel bp = null;
                if(left){
                    lp = origin.getPixel(x-1, y);
                }
                if(bottom){
                    bp = origin.getPixel(x, y+1);
                }
                Pixel p = image.getPixel(x, y);
                val = getAverageColour(p);
                if(left){
                    leftVal = getAverageColour(lp);
                }
                else{
                    leftVal = val;
                }
                if(bottom){
                    bottomVal = getAverageColour(bp);
                }
                else{
                    bottomVal = val;
                }
                if(Math.abs(val - leftVal) >threshold || Math.abs(val - bottomVal) > threshold){
                    val = 0;
                }
                else{
                    val = 255;
                }
                p.setRed(val);
                p.setGreen(val);
                p.setBlue(val);
                image.setPixel(x, y, p);
                left = true;


            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage original = new APImage(pathToFile);
        int xMax = image.getWidth();
        int yMax = image.getHeight();
        int val;

        for(int x = 0; x < xMax; x++){
            for(int y = 0; y < yMax; y++){
                Pixel pp = original.getPixel(xMax - x - 1, y);
                Pixel p = image.getPixel(x, y);
                p.setRed(pp.getRed());
                p.setGreen(pp.getGreen());
                p.setBlue(pp.getBlue());
                image.setPixel(x, y, p);


            }
        }
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {

        APImage original = new APImage(pathToFile);
        int xMax = original.getWidth();
        int yMax = original.getHeight();
        APImage image = new APImage(yMax, xMax);
        int x = 0;
        int y = 0;

        for(int imageX = yMax - 1; imageX > 0; imageX--){

            for(int imageY = 0; imageY < xMax; imageY++){
                Pixel p = original.getPixel(x, y);
                image.setPixel(imageX, imageY, p);
                x++;
            }
            y++;
            x = 0;
        }
        image.draw();
    }

}
