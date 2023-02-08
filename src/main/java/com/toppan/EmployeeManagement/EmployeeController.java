package com.toppan.EmployeeManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin("*")
@Controller
@RequestMapping("/users")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

		if (CSVHelper.hasCSVFormat(file)) {
			try {
				
				String reason = employeeService.save(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message, reason));
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,e.getMessage()));
			}
		}

		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,"check your file detail or format"));
	}

	@GetMapping("/lists")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		try {
			List<Employee> employees = employeeService.getAllEmployees();

			if (employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public synchronized ResponseEntity<?> filteredEmployees(
			@RequestParam(name = "minSalary") Optional<Double> minSalary, @RequestParam(name = "maxSalary") Optional<Double> maxSalary,
			@RequestParam(name = "offset") Optional<Integer> offset, @RequestParam(name = "limit") Optional<Integer> limit,
			@RequestParam(name = "sort")String sort) {
		List<Employee> employeeList=new ArrayList<Employee>();
		FilteredEmployeeResponse response=new FilteredEmployeeResponse();
		try {
			employeeList = employeeService.filteredEmployees(minSalary.orElse(0.0).doubleValue(),
					maxSalary.orElse(100000.0).doubleValue(), offset.orElse(0), limit.orElse(5000),sort);
			if (employeeList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			response.setCountOfEmployees(employeeService.filteredCountOfEmployees(minSalary.orElse(0.0).doubleValue(),
					maxSalary.orElse(100000.0).doubleValue()));
			response.setEmployees(employeeList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> retrivedEmployee(@RequestParam(name="id") String employeeId) {
		try {
			Optional<Employee> employee = employeeService.findEmployeeById(employeeId);

			return new ResponseEntity<>(employee,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> createEmployee(@RequestBody String employeeJsonString) {
		try {
			JSONObject employeeJson= new JSONObject(employeeJsonString);
			
			Employee employee = new Employee(employeeJson.getString("id"),employeeJson.getString("login"),employeeJson.getString("name"),
					Double.parseDouble(employeeJson.getString("salary")));

			return new ResponseEntity<>(employeeService.saveEmployee(employee)!=null?employeeJsonString:null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@PatchMapping( path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> updateEmployee(@RequestBody String employeeJsonString) {
		try {
			JSONObject employeeJson= new JSONObject(employeeJsonString);
			
			Employee employee = new Employee(employeeJson.getString("id"),employeeJson.getString("login"),employeeJson.getString("name"),
					Double.parseDouble(employeeJson.getString("salary")));
			return new ResponseEntity<>(employeeService.checkAndUpdateEmployee(employee)!=null?employeeJsonString:null, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE)
	public synchronized ResponseEntity<?> deleteEmployee(@RequestBody String employeeId) {
		try {
			JSONObject employeeJson= new JSONObject(employeeId);
			employeeService.deleteEmployee(employeeJson.getString("id"));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
