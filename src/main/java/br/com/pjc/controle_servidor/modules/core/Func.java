package br.com.pjc.controle_servidor.modules.core;

public class Func {
    public static boolean isNullOrEmpty(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean isNotNullOrEmpty(String texto) {
        return !isNullOrEmpty(texto);
    }
    public static String formatarQuery(String query){
        return "%".concat(query.toLowerCase()).concat("%");
    }
}
