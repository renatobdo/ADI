package br.com.ifsp.carros;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	
	@Autowired
	private CarroService service;
	//private CarroService service = new CarroService();
	
	/*
	 * @GetMapping() 
	 * public Iterable<Carro> get() { 
	 * 		return service.getCarros(); 
	 * }
	 */
	
	/*@GetMapping()
	public ResponseEntity<Iterable<Carro>> get(){
		return ResponseEntity.ok(service.getCarros());
		//return new ResponseEntity<>(service.getCarros(),HttpStatus.OK);
	}*/
	@GetMapping()
	public ResponseEntity<List<CarroDTO>> get(){
		return ResponseEntity.ok(service.getCarros());
		//return new ResponseEntity<>(service.getCarros(),HttpStatus.OK);
	}
	
	
	/*@GetMapping("/{id}")
	public Optional<Carro> get(@PathVariable("id") Long id) {
		return service.getCarroById(id);
	}*/
	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable("id") Long id) {
		Optional<CarroDTO> carro = service.getCarroById(id);
		if (carro.isPresent()) {
			return ResponseEntity.ok(carro.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
//	@GetMapping("/tipo/{tipo}")
//	public Iterable<Carro> getCarrosByTipo(@PathVariable("tipo") String tipo) {
//		return service.getCarrosByTipo(tipo);
//	}
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity getCarrosByTipo(@PathVariable("tipo") String tipo) {
		//List<Carro> carros = service.getCarrosByTipo(tipo);
		List<CarroDTO> carros = service.getCarrosByTipo(tipo);
				return carros.isEmpty() ? ResponseEntity.noContent().build():
			ResponseEntity.ok(carros);
	}
	@PostMapping
	//public String post(@RequestBody Carro carro) {
	public ResponseEntity post(@RequestBody Carro carro) {
		try {
			CarroDTO c = service.insert(carro);
			URI location = getUri(c.getId());
			return ResponseEntity.created(location).build();
		}catch(Exception e){
			return ResponseEntity.badRequest().build();
		}
		//Carro c = service.save(carro);
		//return "Carro salvo com sucesso: "+c.getId();
	}
	
	
	private URI getUri(Long id) {
		// TODO Auto-generated method stub
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();
	}


	@PutMapping("/{id}")
	public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		carro.setId(id);
		CarroDTO c = service.update(carro,id);
	//	return "Carro atualizado com sucesso: "+c.getId();
		return c!= null ? ResponseEntity.ok(c):
						  ResponseEntity.notFound().build();
	}
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete(id);
		return "Carro deletado com sucesso: ";
	}
	
}
