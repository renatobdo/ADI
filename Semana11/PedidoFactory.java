package br.com.ifsp.jms;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PedidoFactory {

	public Pedido geraPedidoComValores() {
		Pedido pedido = new Pedido(2459, geraData("29/04/2022"), geraData("29/04/2022"), new BigDecimal("2145.98"));
		
		Item samsungGalaxy = geraItem(23,"A50");
		Item samsungGalaxy2 = geraItem(51,"A31");
		
		pedido.adicionaItem(samsungGalaxy);
		pedido.adicionaItem(samsungGalaxy2);
		
		return pedido;

	}

	private Item geraItem(int id, String nome) {
		return new Item(id,nome);
	}

	private Calendar geraData(String dataComString) {
		try {
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataComString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			return cal;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
}
