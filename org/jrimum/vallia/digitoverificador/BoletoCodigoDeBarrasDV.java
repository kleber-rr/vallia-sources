/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:22:11
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a LicenÃ§a Apache, VersÃ£o 2.0 ("LICENÃ‡A"); vocÃª nÃ£o pode usar
 * esse arquivo exceto em conformidade com a esta LICENÃ‡A. VocÃª pode obter uma
 * cÃ³pia desta LICENÃ‡A em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigÃªncia legal ou acordo por escrito, a distribuiÃ§Ã£o de software sob
 * esta LICENÃ‡A se darÃ¡ â€œCOMO ESTÃ�â€�, SEM GARANTIAS OU CONDIÃ‡Ã•ES DE QUALQUER
 * TIPO, sejam expressas ou tÃ¡citas. Veja a LICENÃ‡A para a redaÃ§Ã£o especÃ­fica a
 * reger permissÃµes e limitaÃ§Ãµes sob esta LICENÃ‡A.
 * 
 * Criado em: 30/03/2008 - 18:22:11
 * 
 * Alterado dia 29/11/2019 - Kleber Cardoso
 */

package org.jrimum.vallia.digitoverificador;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.Exceptions;

import br.com.cygnus.gvbras.core.ModuloJrimun;

/**
 * <p>
 * LÃ³gica de cÃ¡lculo do dÃ­gito verificador do cÃ³digo de barras de um boleto.<br />
 * A lÃ³gica funciona da seguinte forma:
 * </p>
 * <p>
 * Utilizando-se o mÃ³dulo 11, considerando-se os 43 dÃ­gitos que compÃµem o cÃ³digo
 * de barras, jÃ¡ excluÃ­da a 5Âª posiÃ§Ã£o (posiÃ§Ã£o do dÃ­gito verificador), segue-se
 * o procedimento abaixo:
 * </p>
 * <p>
 * Calcula-se o dÃ­gito verificador atravÃ©s da expressÃ£o <code>DV = 11 - R</code>
 * , onde R Ã© o resultado do cÃ¡lculo do mÃ³dulo.<br />
 * ObservaÃ§Ã£o: O dÃ­gito verificador serÃ¡ 1 para os restos (resultado do mÃ³dulo):
 * 0 , 10 ou 1 (zero, dez, um).
 * </p>
 * <p>
 * Obs.: A rotina de mÃ³dulo utilizada Ã© o mÃ³dulo 11.
 * </p>
 * 
 * @see Modulo
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">RÃ´mulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * Alterado dia 29/11/2019 - Kleber Cardoso
 * @version 0.2
 * 
 * @since 0.2
 */
public class BoletoCodigoDeBarrasDV extends AbstractDigitoVerificador {

	private static final long serialVersionUID = 7977220668336110040L;

	private static final int TAMANHO_SEM_DV = 43;

	private static final Modulo modulo11 = new Modulo(TipoDeModulo.MODULO11);

	/**
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(String)
	 * @since 0.2
	 */
	@Override
	public int calcule(String numero, String codigoBanco) throws IllegalArgumentException {

		int dv = 0;
		int resto = 0;

		if (StringUtils.isNotBlank(numero) && StringUtils.isNumeric(numero)
				&& (numero.length() == TAMANHO_SEM_DV)) {

			// Realizando o cÃ¡lculo do dÃ­gito verificador utilizando mÃ³dulo 11.
			// Obtendo o resto da divisÃ£o por 11.
			
			if(codigoBanco.equals("033")) {
				int somatoria = ModuloJrimun.calculeSomaSequencialMod11(numero, 2, 9);
				System.out.println("LINHA DIGITAVEL: " + numero);
				System.out.println("SOMATORIA: " + somatoria);
				int produto = somatoria*10;
				System.out.println("produto: " + produto);
				resto = produto % 11;
				System.out.println("resto: " + resto);
//				resto = (11 - resto);
				if (resto == 10 || resto == 0 || resto == 1) {
					dv = 1;
//				} else if (resto == 0 || resto == 1){
//					dv = 0;
				}else{
					dv = resto;
				}
				System.out.println("DV: " + dv);
			} else {
				resto = modulo11.calcule(numero);
				// Seguindo as especificaÃ§Ãµes da FEBRABAN, caso o resto seja
				// (0), (1) ou (10), serÃ¡ atribuÃ­do (1) ao digito verificador.
				if ((resto == 0) || (resto == 1) || (resto == 10)) {
					dv = 1;
				// Caso contrÃ¡rio, dv = 11 - resto.
				} else {
					dv = modulo11.valor() - resto;
				}
			}

		} else {
			Exceptions.throwIllegalArgumentException("O cÃ³digo de barras " + "[ "
					+ numero + " ] deve conter apenas nÃºmeros e "
					+ TAMANHO_SEM_DV + " dÃ­gitos.");
		}

		return dv;
	}

	@Override
	public int calcule(String numero) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

}
