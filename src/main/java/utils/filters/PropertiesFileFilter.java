package utils.filters;

import java.nio.file.Path;
import java.util.function.Predicate;

public class PropertiesFileFilter implements Predicate<Path>{
	
	@Override
	public boolean test(Path path) {
		return path.toFile().isFile()
				&&
				path.getFileName().toString().endsWith(".properties");
	}
	
}
