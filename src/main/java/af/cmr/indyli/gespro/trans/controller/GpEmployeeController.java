package af.cmr.indyli.gespro.trans.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import af.cmr.indyli.gespro.business.dto.GpEmployeeBasicDTO;
import af.cmr.indyli.gespro.business.dto.GpEmployeeFullDTO;
import af.cmr.indyli.gespro.business.exception.GesproBusinessException;
import af.cmr.indyli.gespro.business.service.IGpEmployeeService;
import af.cmr.indyli.gespro.business.utils.GesproConstantes.GesproConstantesService;
import af.cmr.indyli.gespro.trans.urlbase.GesproUrlBase;

@CrossOrigin(origins = GesproUrlBase.url, maxAge = GesproUrlBase.maxAge)
@RestController
@RequestMapping("/employee")
public class GpEmployeeController {

	@Resource(name = GesproConstantesService.GP_EMPLOYEE_SERVICE_KEY)
	private IGpEmployeeService gpEmployeeService;

	/**
	 * Create GpEmployee in the database
	 * 
	 **/
	@PostMapping
	public ResponseEntity<?> createEmployee(@Valid @RequestBody GpEmployeeFullDTO gpEmployee)
			throws GesproBusinessException {

		return ResponseEntity.ok(gpEmployeeService.create(gpEmployee));
	}

	/**
	 * return the list of all address
	 * 
	 **/
	@GetMapping
	public List<GpEmployeeBasicDTO> getAllAdress() {

		return gpEmployeeService.findAll();
	}

	/**
	 * return Employee by id
	 * 
	 * @throws GesproBusinessException
	 **/
	@GetMapping("/{id}")
	public GpEmployeeFullDTO getOneEmployee(@PathVariable(value = "id") int id) throws GesproBusinessException {
		return this.gpEmployeeService.findById(id);
	}

	/**
	 * modify a given Employee in the database
	 * 
	 * @throws GesproBusinessException
	 * @throws AccessDeniedException
	 */

	@PutMapping("/{id}")
	public ResponseEntity<GpEmployeeBasicDTO> getUpdateGpEmployee(@PathVariable(value = "id") int idGpEmployee,
			@Valid @RequestBody GpEmployeeFullDTO employeeDetails)
			throws GesproBusinessException, AccessDeniedException {

		GpEmployeeFullDTO empFull = gpEmployeeService.findById(idGpEmployee);
		if (empFull == null) {
			return ResponseEntity.notFound().build();
		}

		GpEmployeeBasicDTO updateEmp = gpEmployeeService.update(employeeDetails);
		return ResponseEntity.ok().body(updateEmp);
	}

	/**
	 * Delete a given address in the database
	 * 
	 * @throws GesproBusinessException
	 * @throws AccessDeniedException
	 **/
	@DeleteMapping("/{id}")
	public ResponseEntity<GpEmployeeBasicDTO> deleteEmployee(@PathVariable(value = "id") int idEmployee)
			throws AccessDeniedException, GesproBusinessException {
		gpEmployeeService.deleteById(idEmployee);
		return ResponseEntity.ok().build();

	}
}
