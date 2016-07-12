package lifeExplorer;

import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;
import java.awt.Color.*;

/**
 * Contains the functionality to generate a gaussian filter kernel and apply it
 * to an image.
 *
 * @author Simon Horne.
 */
public class GaussianKernel {

	/**
	 * Default no-args constructor.
	 * 
	 * @return
	 */
	public void GaussianSmooth() {
	}

	/**
	 * Calculates the discrete value at x,y of the 2D gaussian distribution.
	 *
	 * @param theta
	 *            the theta value for the gaussian distribution
	 * @param x
	 *            the point at which to calculate the discrete value
	 * @param y
	 *            the point at which to calculate the discrete value
	 * @return the discrete gaussian value
	 */
	public static double gaussianDiscrete2D(double theta, int x, int y) {
		double g = 0;
		for (double ySubPixel = y - 0.5; ySubPixel < y + 0.55; ySubPixel += 0.1) {
			for (double xSubPixel = x - 0.5; xSubPixel < x + 0.55; xSubPixel += 0.1) {
				g = g
						+ ((1 / (2 * Math.PI * theta * theta)) * Math.pow(
								Math.E, -(xSubPixel * xSubPixel + ySubPixel
										* ySubPixel)
										/ (2 * theta * theta)));
			}
		}
		g = g / 121;
		// System.out.println(g);
		return g;
	}

	/**
	 * Calculates several discrete values of the 2D gaussian distribution.
	 *
	 * @param theta
	 *            the theta value for the gaussian distribution
	 * @param size
	 *            the number of discrete values to calculate (pixels)
	 * @return 2Darray (size*size) containing the calculated discrete values
	 */
	public static double[][] gaussian2D(double theta, int size) {
		double[][] kernel = new double[size][size];
		for (int j = 0; j < size; ++j) {
			for (int i = 0; i < size; ++i) {
				kernel[i][j] = gaussianDiscrete2D(theta, i - (size / 2), j
						- (size / 2));
			}
		}

		double sum = 0;
		for (int j = 0; j < size; ++j) {
			for (int i = 0; i < size; ++i) {
				sum = sum + kernel[i][j];

			}
		}

		return kernel;
	}

	public static double[][] kernel2scale(int size, int max, double theta, int coldOrHot){
		double [][] kernel = GaussianKernel.gaussian2D(theta, size);
		coldOrHot = (coldOrHot < 0) ? -1 : 1; 
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				kernel[i][j] = (coldOrHot*size*size*max*kernel[i][j]);
			}
		}
		return kernel;
	}
	
	public static double[][] heatmap(int x, int y, double theta, int seeds, int clusterSize){
		double [][] map = new double[x][y];
		double [][] temp = new double[clusterSize][clusterSize];
		for(int i=0;i<seeds;i++){
			int spawnx, spawny;
			spawnx = Common.randomWithRange(0, x);
			spawny = Common.randomWithRange(0, y);
			temp = GaussianKernel.kernel2scale(clusterSize, Common.randomWithRange(0, 10), theta, Common.randomWithRange(-10, 10));
			Common.printTempMap(temp);
			for(int w=0;w<clusterSize;w++){
				for(int z=0;z<clusterSize;z++){
					int leftx = spawnx - clusterSize/2;
					int lefty = spawny - clusterSize/2;
					if(leftx + w >= 0 && leftx + w < x && lefty + z >= 0 && lefty + z < y){
							map[leftx+w][lefty+z] = temp[w][z];
					}
				}
			}
		}
		return map;
	}
	
	

}
