package es.urjc.computadores;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Service
@Configuration
public class ImageService implements WebMvcConfigurer {

	private static final Path FILES_FOLDER = Paths.get("./src/main/resources/static/img");
	private static final Path SEARCH_DIR = Paths.get("/img");
	private static final int IMAGE_WIDTH = 900;
	private static final int IMAGE_HEIGHT = 650;


	private Path createFilePath(long id, Path folder) {
		return folder.resolve("image-" + id + ".png");
	}

	public String saveImage(String folderName, long id, MultipartFile image) throws IOException {
		Path folder = FILES_FOLDER.resolve(folderName);
		Path searchFolder = SEARCH_DIR.resolve(folderName);
		if (!Files.exists(folder)) {
			Files.createDirectories(folder);
		}
		Path newFile = createFilePath(id, folder);
		Path newSearch = createFilePath(id, searchFolder);
		image.transferTo(newFile);
		System.out.println("imagen guardada en : " + newFile);
		System.out.println("imagen guardada en  el file : " + newFile.toString());
		System.out.println("imagen buscada en   : " + newSearch.toString());
		

		return newSearch.toString();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/images/**")
	.addResourceLocations("file:" + FILES_FOLDER.toAbsolutePath().toString() + "/");
	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath)
            throws IOException {

    	String cadena = "./src/main/resources/static" + inputImagePath;

        // reads input image
        File inputFile = new File(cadena);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(IMAGE_WIDTH,
        		IMAGE_HEIGHT, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = cadena.substring(cadena
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(cadena));
    }

}
