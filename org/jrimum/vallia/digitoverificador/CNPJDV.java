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
 * Created at: 30/03/2008 - 18:22:39
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
 * Criado em: 30/03/2008 - 18:22:39
 * 
 */

package org.jrimum.vallia.digitoverificador;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.Exceptions;
import org.jrimum.utilix.text.Filler;

/**
 * <p>
 * O cÃ¡lculo do dÃ­gito verificador do CNPJ Ã© realizado em duas etapas e Ã©
 * auxiliado pela rotina de mÃ³dulo 11.
 * </p>
 * <p>
 * Abaixo Ã© demonstrado como esse cÃ¡lculo Ã© feito:
 * </p>
 * <h3>Exemplo para um nÃºmero hipotÃ©tico 11.222.333/0001-XX:</h3>
 * <p>
 * Primeiramente obtÃ©m-se um nÃºmero R, calculado atravÃ©s da rotina de mÃ³dulo 11,
 * a partir dos doze primeiros nÃºmeros do CNPJ, nesse caso 112223330001. <br />
 * Para obter o primeiro dÃ­gito verificador deve-se seguir a seguinte lÃ³gica: <br />
 * <br />
 * Se o nÃºmero R for menor que 2, o dÃ­gito terÃ¡ valor 0 (zero); senÃ£o, serÃ¡ a
 * subtraÃ§Ã£o do valor do mÃ³dulo (11) menos o valor do nÃºmero R, ou seja,
 * <code>DV = 11 - R</code>.
 * </p>
 * <p>
 * Para obter o segundo dÃ­gito verificador Ã© da mesma forma do primeiro, porÃ©m
 * deve ser calculado a partir dos treze primeiros nÃºmeros do CNPJ, ou seja,
 * 112223330001 + primeiro dÃ­gito.
 * </p>
 * <p>
 * Obs.: O limite mÃ­nimo e mÃ¡ximo do mÃ³dulo 11 sÃ£o 2 e 9, respectivamente.
 * </p>
 * 
 * @see Modulo
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
public class CNPJDV extends AbstractDigitoVerificador {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4702398145481452503L;

	/**
	 * <p>
	 * Liminte mÃ­nimo do para cÃ¡lculo no mÃ³dulo 11.
	 *</p>
	 */
	private static final int LIMITE_MINIMO = 2;

	/**
	 * <p>
	 * Liminte mÃ¡ximo do para cÃ¡lculo no mÃ³dulo 11.
	 *</p>
	 */
	private static final int LIMITE_MAXIMO = 9;

	/**
	 * <p>
	 * ExpressÃ£o regular para validaÃ§Ã£o dos doze primeiros nÃºmeros do CNPJ sem
	 * formataÃ§Ã£o: <tt>"############"</tt>.
	 * </p>
	 */
	private static final String REGEX_CNPJ_DV = "\\d{12}";

	/**
	 * <p>
	 * ExpressÃ£o regular para validaÃ§Ã£o dos doze primeiros nÃºmeros do CNPJ
	 * formatado: <tt>"##.###.###/####"</tt>.
	 * </p>
	 */
	private static final String REGEX_CNPJ_DV_FORMATTED = "\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}";

	/**
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(long)
	 * @since 0.2
	 */
	@Override
	public int calcule(long numero) {

		return calcule(Filler.ZERO_LEFT.fill(String.valueOf(numero), 12));
	}

	/**
	 * <p>
	 * </p>
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(java.lang.String)
	 * @since 0.2
	 */
	@Override
	public int calcule(String numero) throws IllegalArgumentException {

		int dv1 = 0;
		int dv2 = 0;
		
		numero = removaFormatacao(numero);

		if (isFormatoValido(numero)) {

			dv1 = calculeDigito(numero);
			dv2 = calculeDigito(numero + dv1);
			
		} else {
			
			Exceptions.throwIllegalArgumentException(
				"O CNPJ [ " + numero
				+ " ] deve conter apenas nÃºmeros, sendo eles no formato ##.###.###/#### ou ############ !");
		}

		return Integer.parseInt(dv1 + "" + dv2);
	}

	/**
	 * MÃ©todo null-safe que remove a formataÃ§Ã£o da String, com a intenÃ§Ã£o de deixar
	 * apenas nÃºmeros.
	 * 
	 * @param numero - CNPJ que pode estar formatado.
	 * @return NÃºmero CNPJ sem formataÃ§Ã£o.
	 */
	private String removaFormatacao(String numero) {
		
		numero = StringUtils.replaceChars(numero, ".", "");
		numero = StringUtils.replaceChars(numero, "/", "");
		
		return numero;
	}
	
	/**
	 * <p>
	 * Verifica se o nÃºmero passado estÃ¡ em um formato aceitÃ¡vel para a realizaÃ§Ã£o do cÃ¡lculo,
	 * ou seja:
	 * </p>
	 * <ul>
	 * 	<li>NÃ£o Ã© null</li>
	 * 	<li>NÃ£o Ã© vazio</li>
	 *  <li>Apenas nÃºmeros</li>
	 *  <li>NÃ£o Ã© somente zeros</li>
	 *  <li>EstÃ¡ no formato ##.###.###/#### ou ############</li>
	 * </ul>
	 * 
	 * @param numero - CNPJ para ser validado
	 * @return <code>true</code> caso o nÃºmero esteja em um formato vÃ¡lido; <code>false</code>, 
	 * caso contrÃ¡rio.
	 */
	private boolean isFormatoValido(String numero) {
		
		boolean isValido = false;
		
		if (StringUtils.isNotBlank(numero)) {
			
			boolean formatoValido = (Pattern.matches(REGEX_CNPJ_DV, numero) || Pattern.matches(REGEX_CNPJ_DV_FORMATTED, numero));

			if (formatoValido) {
				
				isValido = Long.parseLong(numero) > 0;
			}
		}
		
		return isValido;
	}

	/**
	 * <p>
	 * MÃ©todo auxiliar para o cÃ¡lculo do dÃ­gito verificador.
	 * </p>
	 * <p>
	 * Calcula os dÃ­gitos separadamente.
	 * </p>
	 * 
	 * @param numero
	 *            - nÃºmero a partir do qual serÃ¡ extraÃ­do o dÃ­gito verificador.
	 * @return Um nÃºmero que faz parte de um dÃ­gito verificador.
	 * @throws IllegalArgumentException
	 *             caso o nÃºmero nÃ£o esteja no formatador desejÃ¡vel.
	 * 
	 * @since 0.2
	 */
	private int calculeDigito(String numero) throws IllegalArgumentException {

		int dv = 0;
		int resto = 0;

		resto = Modulo.calculeMod11(numero, LIMITE_MINIMO, LIMITE_MAXIMO);

		if (resto >= 2) {

			dv = TipoDeModulo.MODULO11.valor() - resto;
		}

		return dv;
	}

	@Override
	public int calcule(String numero, String codigoBanco) throws IllegalArgumentException {
		int dv1 = 0;
		int dv2 = 0;
		
		numero = removaFormatacao(numero);

		if (isFormatoValido(numero)) {

			dv1 = calculeDigito(numero);
			dv2 = calculeDigito(numero + dv1);
			
		} else {
			
			Exceptions.throwIllegalArgumentException(
				"O CNPJ [ " + numero
				+ " ] deve conter apenas nÃºmeros, sendo eles no formato ##.###.###/#### ou ############ !");
		}

		return Integer.parseInt(dv1 + "" + dv2);
	}

}
