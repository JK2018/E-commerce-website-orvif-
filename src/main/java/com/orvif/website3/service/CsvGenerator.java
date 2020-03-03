package com.orvif.website3.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvGenerator {
	private List<Object> objects;

	public CsvGenerator(List<Object> objects) {
		this.objects = objects;
	}

	public String generateStringCSVFile() throws IllegalAccessException {
		if (this.objects.size() > 0) {
			StringBuilder generatedCsv = new StringBuilder();
			int cpt = 0;
			for (Object obj : this.objects) {
				Class<? extends Object> c1 = obj.getClass();
				Map<String, Object> map = new HashMap<>();
				Field[] fields = c1.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();

					fields[i].setAccessible(true);
					Object value = fields[i].get(obj);
					map.put(name, value);
				}
				if (cpt == 0) {
					int sepCpt = 0;
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						generatedCsv.append(sepCpt == 0 ? entry.getKey() : ";" + entry.getKey());
						sepCpt++;
					}
					generatedCsv.append("\n");
				}
				int sepCpt = 0;
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					generatedCsv.append(sepCpt == 0 ? entry.getValue() : ";" + entry.getValue());
					sepCpt++;
				}
				generatedCsv.append("\n");
				cpt++;
			}
			System.out.println(generatedCsv.toString());
			return generatedCsv.toString();
		} else {
			return "NO DATA";
		}
	}

}
