package dev.lucas.rightcarprices.service;

import java.util.List;

public interface IConversorDeDados {
    <T> T converterJsonParaClasse(String json, Class<T> classe);
    <T> List<T> converterJsonParaListaDeClasses(String json, Class<T> classe);
}
