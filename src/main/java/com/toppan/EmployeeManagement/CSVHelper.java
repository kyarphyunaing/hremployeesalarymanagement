package com.toppan.EmployeeManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Login", "Name", "Salary" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (TYPE.equals(file.getContentType()) || file.getContentType().equals("application/vnd.ms-excel")) {
			return true;
		}

		return false;
	}

	public static List<Employee> csvToEmployees(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader()
						.withIgnoreHeaderCase().withTrim().withIgnoreEmptyLines().withCommentMarker('#'));) {

			List<Employee> Employees = new ArrayList<Employee>();

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {

				Employee Employee = new Employee(csvRecord.get("Id"), csvRecord.get("Login"), csvRecord.get("Name"),
						Double.parseDouble(csvRecord.get("Salary")));

				Employees.add(Employee);
			}

			return Employees;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}