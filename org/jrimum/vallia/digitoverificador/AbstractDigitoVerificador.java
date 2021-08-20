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
 * Created at: 30/03/2008 - 18:21:41
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
 * Criado em: 30/03/2008 - 18:21:41
 * 
 */

package org.jrimum.vallia.digitoverificador;

/**
 * <p>
 * Define o comportamento de classes que implementam uma lÃ³gica de cÃ¡lculo de 
 * um dÃ­gito verificador.
 * </p>
 * 
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
public abstract class AbstractDigitoVerificador {

	/**
	 * <p>
	 * Calcula o dÃ­gito verificador de um nÃºmero de acordo com uma lÃ³gica
	 * especÃ­fica.
	 * </p>
	 * 
	 * @param numero
	 *            - nÃºmero que serÃ¡ calculado o dÃ­gito verificador.
	 * @return dÃ­gito verificador.
	 * @throws IllegalArgumentException
	 *             caso a String nÃ£o esteja em um formato aceitÃ¡vel. (O
	 *             formato Ã© definido nas subclasses implementadoras).
	 * @since 0.2
	 */
	public abstract int calcule(String numero) throws IllegalArgumentException;
	
	public abstract int calcule(String numero, String codigoBanco) throws IllegalArgumentException;

	/**
	 * <p>
	 * Calcula o dÃ­gito verificador de um nÃºmero de acordo com uma lÃ³gica
	 * especÃ­fica.
	 * </p>
	 * <p>
	 * Se nÃ£o sobrescrito o retorno Ã© sempre igual a 0 (zero).
	 * </p>
	 * 
	 * @param numero
	 *            - nÃºmero que serÃ¡ calculado o dÃ­gito verificador.
	 * @return dÃ­gito verificador
	 * @since 0.2
	 */
	public int calcule(long numero) {

		return 0;
	}

}
