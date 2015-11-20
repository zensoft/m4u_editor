package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import utils.filters.CountryFilter;
import utils.filters.NotNullStringFilter;
import utils.filters.PropertiesFileFilter;
import utils.mappers.LineMatchMapper;


public class FilesPaths {

	private String searchedKey = "rwd.product.tag.name=";
	private String newLineKey = "rwd.product.tag.hepltext";
	
	private Map<String, String> i18nMap = new HashMap<String, String>();
	
	public FilesPaths() {
		super();
		try {
			parseI18N();
			getCountriesStream()
			.forEach(countryCode -> {
				searchProperties(countryCode.getCode());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseI18N() throws Exception{
		i18nMap.clear();
		Path i18nFile = Paths.get("/home/tomek/Pulpit/i18n.pom");
		
		List<String> i18nLines = Files.readAllLines(i18nFile);
		
		getCountriesStream()
		.forEach(countryCode -> {
			i18nLines
			.stream()
			.map(new LineMatchMapper(countryCode.getCode()))
			.filter(new NotNullStringFilter())
			.forEach(i18nLine -> {
				i18nMap.put(countryCode.getCode(), i18nLine);
			});
		});
		
		for (Entry<String, String> e : i18nMap.entrySet()) {
			System.out.println(e.getKey() + " => " + e.getValue());
		}
		
	}
	
	private Stream<Code> getCountriesStream() {
		return Arrays.stream(Code.values())
				.filter(new CountryFilter(new Code[] {Code.PL}));
	}
	
	private void searchProperties(String countryCode) {
		try {
			Path start = Paths.get("/home/tomek/mounted_devjava/branches/bonprix-platform/bonprix-webapp-" + countryCode + "/src/main/");
			int maxDepth = 20;
			try (Stream<Path> stream = Files.walk(start, maxDepth)) {			
			    stream
			        .filter(new PropertiesFileFilter())
			        .filter(path -> {
			        	return (path.getFileName().toString().equals("shop-rwd.properties"));
			        })
			        .filter(line -> {
						return i18nMap.get(countryCode) != null;
					})
			        .sorted()
			        .forEach(path -> {
			        	printProperties(path, countryCode);
			        });
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void printProperties(Path path, String countryCode) {
		try {
			
			System.out.println("======== " + countryCode + " -> " + path.getFileName() + " ========");
			
			String newLineValue = i18nMap.get(countryCode);
			String newLine = "\n" + newLineKey + "=" + newLineValue;
			
			List<String> lines = Files.readAllLines(path);
			
			List<String> newLines = lines.stream()
			.filter(line -> {
				return !line.startsWith(newLineKey + "=");//wykluczam powtorzenia
			})		
			.map(line -> {
				if (line.startsWith(searchedKey)) {
					System.out.println("Adding " + newLine);
					return line + newLine;
				}
				return line;
			})
			.collect(Collectors.toList());
			
			Files.write(path, newLines);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
