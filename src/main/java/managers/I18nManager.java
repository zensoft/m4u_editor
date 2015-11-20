package managers;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import model.I18nLine;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import utils.Code;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Multimap;
import com.google.common.collect.Range;

public class I18nManager {

	private Map<Integer, Code> counryIndexMap = new HashMap<>();
	private Multimap<Code, I18nLine> i18nMap = ArrayListMultimap.create(); 
	
	public I18nManager() {
		super();
	}

	public void parseCsv() {
		try (
				Reader in = new FileReader("/home/tomek/Pulpit/i18n.csv");
				CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			) {
			
			counryIndexMap.clear();
			i18nMap.clear();
			
			Iterator<CSVRecord> iter = parser.iterator();
			getCsvStream(iter)
			.forEach(record -> {
				if (record.getRecordNumber() == 1) {
					createCountryRowIndexes(record);
				} else {
					parseRecord(record);
				}
			});
			
			/*i18nMap.keySet().stream()
			.forEach(c -> {
				System.out.println(c);
				i18nMap.get(c).stream()
				.forEach(line -> {
					System.out.println("\t" + line);
				});
				
			});*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void parseRecord(CSVRecord record) {
		String msgKey = null;
		for (int i : ContiguousSet.create(Range.closed(0, record.size()-1), DiscreteDomain.integers()) ){
			if (StringUtils.isNotBlank(record.get(i))) {
				if (i == 0) {
					msgKey = record.get(i);
				} else {
					Code c = counryIndexMap.get(i);
					if (StringUtils.isNotBlank(msgKey) && c != null) {
						i18nMap.put(c, new I18nLine(msgKey, record.get(i)));
					}
				}
			}
		}
	}

	private void createCountryRowIndexes(CSVRecord record) {
		for (int i : ContiguousSet.create(Range.closed(0, record.size()-1), DiscreteDomain.integers()) ){
			if (StringUtils.isNotBlank(record.get(i))) {
				String txt = record.get(i);
				Code c = Code.getFromString(txt);
				if (c != null) {
					counryIndexMap.put(i, c);
				}
			}
		}
		//System.out.println(counryIndexMap);
	}
	
	private Stream<CSVRecord> getCsvStream(Iterator<CSVRecord> iter) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iter, Spliterator.ORDERED), false).filter(record -> {
			return isNotEmptyLine(record);
		});
	}

	private boolean isNotEmptyLine(CSVRecord record) {
		for (int i : ContiguousSet.create(Range.closed(0, record.size()-1), DiscreteDomain.integers()) ){
			if (StringUtils.isNotBlank(record.get(i))) {
				return true;
			}
		}
		return false;
	}

	public Multimap<Code, I18nLine> getI18nMap() {
		return i18nMap;
	}
	
	public String getLinesForCountry(Code code) {
		StringBuilder sb = new StringBuilder();
		i18nMap.get(code)
		.stream()
		.map(line -> {
			return "\n" + line.getLine();
		})
		.forEach(line -> {
			sb.append(line);
		});
		return sb.toString();
	}
	
}
