package br.com.stefanini.clients.api.util;

import java.time.LocalDate;

import br.com.stefanini.clients.api.data.exception.NegocioException;

public class ClienteUtil {

	public static void isDataNascimentoValid(LocalDate dataNascimento) {

		LocalDate dataAtual = LocalDate.now();

		int compareValue = dataAtual.compareTo(dataNascimento);

		if (compareValue < 0) {
			throw new NegocioException("Data de Nascimento é Inválida.");
		}
	}

}