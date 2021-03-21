package pe.com.win.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SentenciaMapper {

	@Select("exec DOLPHIN.SP_PRUEBA_LISTA_CLIENTES  #{estado}")
	public List<LinkedHashMap<?, ?>> lista(String estado);
}
