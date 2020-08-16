package br.com.stefanini.clients.conveter;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.stefanini.clients.api.conveter.DozerConverter;
import br.com.stefanini.clients.api.data.model.Cliente;
import br.com.stefanini.clients.api.data.vo.ClienteVO;
import br.com.stefanini.clients.api.enums.SexoEnum;
import br.com.stefanini.clients.conveter.mocks.MockCliente;
public class DozerConverterTest {

	MockCliente inputObject;

	@BeforeEach
	public void setUp() {
		inputObject = new MockCliente();
	}

	@Test
	public void parseEntityToVOTest() {
		ClienteVO output = DozerConverter.parseObject(inputObject.mockEntity(), ClienteVO.class);
		Assertions.assertEquals(Long.valueOf(0L), output.getId());
		Assertions.assertEquals("MOCK DA SILVA_0", output.getNome());
		Assertions.assertEquals("BRASILEIRO", output.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, output.getSexo());
	}

	@Test
	public void parseEntityListToVOListTest() {
		List<ClienteVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), ClienteVO.class);
		ClienteVO outputZero = outputList.get(0);
		
		Assertions.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assertions.assertEquals("MOCK DA SILVA_0", outputZero.getNome());
		Assertions.assertEquals("BRASILEIRO", outputZero.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputZero.getSexo());

		ClienteVO outputSete = outputList.get(7);

		Assertions.assertEquals(Long.valueOf(7L), outputSete.getId());
		Assertions.assertEquals("MOCK DA SILVA_7", outputSete.getNome());
		Assertions.assertEquals("BRASILEIRO", outputSete.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputSete.getSexo());
		
		
		ClienteVO outputDoze = outputList.get(12);

		Assertions.assertEquals(Long.valueOf(12L), outputDoze.getId());
		Assertions.assertEquals("MOCK DA SILVA_12", outputDoze.getNome());
		Assertions.assertEquals("BRASILEIRO", outputDoze.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputDoze.getSexo());
	}

	@Test
	public void parseVOToEntityTest() {
		Cliente output = DozerConverter.parseObject(inputObject.mockVO(), Cliente.class);
		
		Assertions.assertEquals(Long.valueOf(0L), output.getId());
		Assertions.assertEquals("MOCK DA SILVA_0", output.getNome());
		Assertions.assertEquals("BRASILEIRO", output.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, output.getSexo());
		
	}

	@Test
	public void parserVOListToEntityListTest() {
		List<Cliente> outputList = DozerConverter.parseListObjects(inputObject.mockVOList(), Cliente.class);
		Cliente outputZero = outputList.get(0);
		
		Assertions.assertEquals(Long.valueOf(0L), outputZero.getId());
		Assertions.assertEquals("MOCK DA SILVA_0", outputZero.getNome());
		Assertions.assertEquals("BRASILEIRO", outputZero.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputZero.getSexo());
		
		Cliente outputSete = outputList.get(7);

		Assertions.assertEquals(Long.valueOf(7L), outputSete.getId());
		Assertions.assertEquals("MOCK DA SILVA_7", outputSete.getNome());
		Assertions.assertEquals("BRASILEIRO", outputSete.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputSete.getSexo());
		
		
		Cliente outputDoze = outputList.get(12);

		Assertions.assertEquals(Long.valueOf(12L), outputDoze.getId());
		Assertions.assertEquals("MOCK DA SILVA_12", outputDoze.getNome());
		Assertions.assertEquals("BRASILEIRO", outputDoze.getNacionalidade());
		Assertions.assertEquals(SexoEnum.MASCULINO, outputDoze.getSexo());
	}
}