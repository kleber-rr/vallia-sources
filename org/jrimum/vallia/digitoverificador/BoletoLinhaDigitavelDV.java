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
 * Created at: 30/03/2008 - 18:22:21
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
 * Criado em: 30/03/2008 - 18:22:21
 * 
 */

package org.jrimum.vallia.digitoverificador;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * Segundo o padrÃ£o FEBRABAN a linha digitÃ¡vel possui cinco campos, dos quais
 * apenas os trÃªs primeiros necessitam de validaÃ§Ã£o com dÃ­gito verificador.
 * </p>
 * <p>
 * Para fins de validaÃ§Ã£o Ã© preciso saber o seguinte:
 * <ul>
 * <li>O primerio campo tem tamanho 9 (nove) mais o dÃ­gito verificador.</li>
 * <li>O segundo campo tem tamanho 10 (dez) mais o dÃ­gito verificador.</li>
 * <li>O terceiro campo tem tamanho 10 (dez) mais o dÃ­gito verificador.</li>
 * </ul>
 * </p>
 * <p>
 * Obs1.: Todos os campos listados podem vir com um ponto (.) de separaÃ§Ã£o
 * exatamente apÃ³s o dÃ­gito da 5Âª posiÃ§Ã£o. <br />
 * Exemplo de linha digitÃ¡vel:<br />
 * <code>99997.77213 30530.150082 18975.000003 1 10010000035000</code>
 * </p>
 * <p>
 * O cÃ¡lculo do dÃ­gito verificador Ã© descrito atravÃ©s da expressÃ£o
 * <code>DV = 11 - R</code>, onde R Ã© o resultado do cÃ¡lculo do mÃ³dulo.<br />
 * Obs1.: O dÃ­gito verificador serÃ¡ 0 (zero) se o resto (resultado do mÃ³dulo)
 * for 0 (zero). <br />
 * Obs2.: A rotina de mÃ³dulo utilizada Ã© a mÃ³dulo 10.
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">RÃ´mulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento
 *         Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class BoletoLinhaDigitavelDV extends AbstractDigitoVerificador {

	private static final long serialVersionUID = -9177413216786384292L;

	/**
	 *<p>
	 * MÃ³dulo 10 utilizado no cÃ¡lculo.
	 * </p>
	 */
	private static final Modulo modulo10 = new Modulo(TipoDeModulo.MODULO10);

	/**
	 * <p>
	 * ExpressÃ£o regular para validaÃ§Ã£o do campo da linha digitÃ¡vel, aceita os
	 * seguintes formatos:
	 * </p>
	 * <ul>
	 * 	<li>#########</li>
	 * 	<li>#####.####</li>
	 * 	<li>##########</li>
	 * 	<li>#####.#####</li>
	 * </ul>
	 */
	private static final String REGEX_CAMPO = "(\\d{9})|(\\d{10})|(\\d{5})\\.(\\d{4})|(\\d{5})\\.(\\d{5})";

	/**
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(java.lang.String)
	 * @since 0.2
	 */
	@Override
	public int calcule(long numero) {

		return calcule(Filler.ZERO_LEFT.fill(String.valueOf(numero), 10));
	}

	/**
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(java.lang.String)
	 * @since 0.2
	 */
	@Override
	public int calcule(String numero) throws IllegalArgumentException {

		int dv = 0;
		int resto = 0;

		if (StringUtils.isNotBlank(numero)
				&& Pattern.matches(REGEX_CAMPO, numero)) {

			numero = StringUtils.replaceChars(numero, ".", "");

			resto = modulo10.calcule(numero);

			if (resto != 0) {
				dv = modulo10.valor() - resto;
			}
		} else {
			Exceptions.throwIllegalArgumentException("O campo [" + numero 
					+ "] da linha digitÃ¡vel deve conter apenas nÃºmeros com 9 ou 10 dÃ­gitos " +
							"ou nos formatos [#####.####, #####.#####]");
		}

		return dv;
	}

	@Override
	public int calcule(String numero, String codigoBanco) throws IllegalArgumentException {
		int dv = 0;
		int resto = 0;

		if (StringUtils.isNotBlank(numero)
				&& Pattern.matches(REGEX_CAMPO, numero)) {

			numero = StringUtils.replaceChars(numero, ".", "");

			resto = modulo10.calcule(numero);

			if (resto != 0) {
				dv = modulo10.valor() - resto;
			}
		} else {
			Exceptions.throwIllegalArgumentException("O campo [" + numero 
					+ "] da linha digitÃ¡vel deve conter apenas nÃºmeros com 9 ou 10 dÃ­gitos " +
							"ou nos formatos [#####.####, #####.#####]");
		}

		return dv;
	}
}
