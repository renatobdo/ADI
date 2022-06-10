package br.com.ifsp.carros;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {
	
	@Autowired
	private CarroRepository rep;
	
/*	public Iterable<Carro> getCarros(){
		return rep.findAll();
	}
	*/
	public List<CarroDTO> getCarros(){
		//List<Carro> carros = rep.findAll();
		//List<CarroDTO> list = new ArrayList<>();
		//return list;
		//o findAll retorna uma lista de carros e estamos chamando o método stream
		//para mapear essa lista. Estamos percorrendo carro por carro e criando um CarroDTO
		//e depois geramos uma nova lista de carro DTO.
		//return rep.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
	public Optional<CarroDTO> getCarroById(Long id) {
		//	return rep.findById(id);
		//return rep.findById(id).map(CarroDTO::new);
		return rep.findById(id).map(CarroDTO::create);
	}
	
	//public Iterable<Carro> getCarrosByTipo(String tipo) {
//		return rep.findByTipo(tipo);
//	}
	/*public List<Carro> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo);
	}*/
	public List<CarroDTO> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo).
			//	stream().map(CarroDTO::new).collect(Collectors.toList());
				stream().map(CarroDTO::create).collect(Collectors.toList());
	}
	
	public List<Carro>getCarrosEmMemoria(){
		List<Carro> carros = new ArrayList<>();
		
		carros.add(new Carro(1L, "Fusca"));
		carros.add(new Carro(2L, "Brasília"));
		carros.add(new Carro(3L, "Chevet"));
		return carros;
	}

	//public Carro save(Carro carro) {
	//	return rep.save(carro);
		
	//}
	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
			return CarroDTO.create(rep.save(carro));
			
		}

	public CarroDTO update(Carro carro, Long id) {
		Assert.notNull(id, "não foi possível atualizar o registro");
		
		//busca o carro no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();
			//Copiar as propriedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id "+db.getId());
			
			//Atualiza o carro
			rep.save(db);
			//return db;
			return CarroDTO.create(db);
		}else {
			return null;
			//throw new RuntimeException("Não foi possível atualizar o registro");
		}
	}

	public void delete(Long id) {
		Optional<CarroDTO>carro = getCarroById(id);
		if(carro.isPresent()) {
			rep.deleteById(id);
		}
		
	}
}
