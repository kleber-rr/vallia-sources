/* 
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created at: 15/06/2008 - 12:00:00
 *
 * ================================================================================
 *
 * Direitos autorais 2008 JRimum Project
 *
 * Licenciado sob a LicenÃ§a Apache, VersÃ£o 2.0 ("LICENÃ‡A"); vocÃª nÃ£o pode 
 * usar esse arquivo exceto em conformidade com a esta LICENÃ‡A. VocÃª pode obter uma 
 * cÃ³pia desta LICENÃ‡A em http://www.apache.org/licenses/LICENSE-2.0 A menos que 
 * haja exigÃªncia legal ou acordo por escrito, a distribuiÃ§Ã£o de software sob esta 
 * LICENÃ‡A se darÃ¡ â€œCOMO ESTÃ�â€�, SEM GARANTIAS OU CONDIÃ‡Ã•ES DE QUALQUER TIPO, sejam 
 * expressas ou tÃ¡citas. Veja a LICENÃ‡A para a redaÃ§Ã£o especÃ­fica a reger permissÃµes 
 * e limitaÃ§Ãµes sob esta LICENÃ‡A.
 * 
 * Criado em: 15/06/2008 - 12:00:00
 * 
 */

package org.jrimum.vallia.digitoverificador;

import org.apache.commons.lang.StringUtils;
import org.jrimum.utilix.Exceptions;

/**
 * <p>
 * CÃ¡lculo para o dÃ­gito verificador do cÃ³digo de compensaÃ§Ã£o dos bancos
 * supervisionados pelo <a href="http://www.bcb.gov.br/?CHEQUESCOMPE">BACEN</a>
 * </p>
 * 
 * @author <a href="http://gilmatryx.googlepages.com">Gilmar P.S.L.</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a>
 * @author <a href="mailto:romulomail@gmail.com">RÃ´mulo Augusto</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public class CodigoDeCompensacaoBancosBACENDV extends AbstractDigitoVerificador {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5250684561237486022L;
	
	/**
	 * Valor mÃ­nimo do cÃ³digo de compensaÃ§Ã£o
	 */
	public static final int LIMITE_MINIMO = 1;
	
	/**
	 * Valor mÃ¡ximo do cÃ³digo de compensaÃ§Ã£o
	 */
	public static final int LIMITE_MAXIMO = 999; 

	/**
	 * <p>
	 * Mesagem padrÃ£o para erro.
	 * </p>
	 */
	private static final String MSG = "O cÃ³digo de compensaÃ§Ã£o do banco deve ser um nÃºmero entre 1 e 999.";

	/**
	 * <p>
	 * Calcula o dÃ­gito verificador para cÃ³digo de compensaÃ§Ã£o passado.
	 * </p>
	 * 
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(java.lang.String)
	 * 
	 * @param numero - CÃ³digo de compensaÃ§Ã£o
	 * @return DÃ­gito verificador que foi calculado
	 * 
	 * @throws IllegalArgumentException Caso nÃ£o seja um cÃ³digo vÃ¡lido
	 * 
	 * @since 0.2
	 */
	@Override
	public int calcule(String numero) {
		
		if (!isCodigoValido(numero)) {
			Exceptions.throwIllegalArgumentException(MSG);
		}
		
		return calcule(Integer.valueOf(numero.trim()));
	}

	/**
	 * <p>
	 * Calcula o dÃ­gito verificador para o cÃ³digo de compensaÃ§Ã£o passado.
	 * </p>
	 * 
	 * @param numero - CÃ³digo de compensaÃ§Ã£o
	 * 
	 * @return DÃ­gito verificador que foi calculado
	 * 
	 * @since 0.2
	 */
	public int calcule(int numero) {

		return calcule((long) numero);
	}

	/**
	 * <p>
	 * Calcula o dÃ­gito verificador para o cÃ³digo de compensaÃ§Ã£o passado.
	 * </p>
	 * 
	 * @param numero - CÃ³digo de compensaÃ§Ã£o
	 * 
	 * @return DÃ­gito verificador que foi calculado
	 * 
	 * @since 0.2
	 * 
	 * @see org.jrimum.vallia.digitoverificador.AbstractDigitoVerificador#calcule(long)
	 */
	@Override
	public int calcule(long numero) {

		int dv = -1;

		if (!isCodigoValido(numero)) {
			Exceptions.throwIllegalArgumentException(MSG);
		}

		int soma = Modulo.calculeSomaSequencialMod11(
				String.valueOf(numero), 2, 9);

		soma *= 10;

		dv = soma % 11;

		dv = (dv == 10) ? 0 : dv;

		return dv;
	}

	/**
	 * <p>
	 * Retorna se um cÃ³digo de compensaÃ§Ã£o passado Ã© vÃ¡lido, ou seja, se estÃ¡ entre os 
	 * valores inteiros de 1 a 999.
	 * </p>
	 * 
	 * @param codigo - CÃ³digo de compensaÃ§Ã£o
	 * @return true se for nÃºmerio entre 1 e 999; false caso contrÃ¡rio
	 * 
	 * @since 0.2
	 */
	public boolean isCodigoValido(String codigo) {

		boolean codigoValido = false;
		
		if (StringUtils.isNotBlank(codigo) && StringUtils.isNumeric(codigo)) {
			codigoValido = isCodigoValido(Integer.valueOf(codigo.trim()));
		}
		
		return codigoValido;
	}

	/**
	 * <p>
	 * Retorna se um cÃ³digo de compensaÃ§Ã£o passado Ã© vÃ¡lido.
	 * </p>
	 * 
	 * @param codigo - CÃ³digo de compensaÃ§Ã£o
	 * @return true se entre 0 (nÃ£o incluso) e 999; false caso contrÃ¡rio
	 * 
	 * @since 0.2
	 */
	public boolean isCodigoValido(int codigo) {
		
		return isCodigoValido((long) codigo);
	}
	
	/**
	 * <p>
	 * Retorna se um cÃ³digo de compensaÃ§Ã£o passado Ã© vÃ¡lido.
	 * </p>
	 * 
	 * @param codigo - CÃ³digo de compensaÃ§Ã£o
	 * @return true se entre 0 (nÃ£o incluso) e 999; false caso contrÃ¡rio
	 * 
	 * @since 0.2
	 */
	public boolean isCodigoValido(long codigo) {
		
		return (codigo >= LIMITE_MINIMO) && (codigo <= LIMITE_MAXIMO);
	}

	@Override
	public int calcule(String numero, String codigoBanco) throws IllegalArgumentException {
		if (!isCodigoValido(numero)) {
			Exceptions.throwIllegalArgumentException(MSG);
		}
		
		return calcule(Integer.valueOf(numero.trim()));
	}
}