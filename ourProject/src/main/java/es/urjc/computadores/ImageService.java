package es.urjc.computadores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



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
		System.out.println("imagen guardada en : " + newFile.toString());

		return newSearch.toString();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/images/**")
	.addResourceLocations("file:" + FILES_FOLDER.toAbsolutePath().toString() + "/");
	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

}
