package unittests;
import java.awt.Color;

import renderer.*;
import org.junit.jupiter.api.Test;

class ImageWriterTest {

	@Test
	void test() {
		
		ImageWriter imageWriter= new ImageWriter("background",1600,1000,800,500);
		double nx= imageWriter.getNx();
		double ny= imageWriter.getNy();
		for(int i=0;i<ny; i++)
			for(int j=0;j<nx;j++)
				imageWriter.writePixel(j, i, i%50==0 || j%50==0 ? Color.BLUE: Color.RED);
		
		imageWriter.writeToImage();
	}

}
