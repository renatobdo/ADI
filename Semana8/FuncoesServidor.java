/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Semana8_8;

import java.text.DecimalFormat;

/**
 *
 * @author Paulo Henrique Cayres
 */
public class FuncoesServidor {

	public static int calcSomatorio(int x, int y) {
		int soma = 0;
		for (int i = x; i <= y; i++)
			if (i % 2 == 0) {
				soma += i;
			}
		return soma;
	}

	

}
