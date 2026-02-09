package br.com.onetec.cross.utilities;

import java.math.BigDecimal;

public class ValorPorExtenso {
    private static final String[] UNIDADES = {
            "", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez",
            "onze", "doze", "treze", "quatorze", "quinze", "dezesseis", "dezessete", "dezoito", "dezenove"
    };

    private static final String[] DEZENAS = {
            "", "", "vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"
    };

    private static final String[] CENTENAS = {
            "", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos",
            "seiscentos", "setecentos", "oitocentos", "novecentos"
    };


    public static String valorPorExtenso(BigDecimal valor) {
        int parteInteira = valor.intValue();
        int centavos = valor.remainder(BigDecimal.ONE).movePointRight(2).intValue();

        String extenso = numeroPorExtenso(parteInteira) + " reais";

        if (centavos > 0) {
            extenso += " e " + numeroPorExtenso(centavos) + " centavos";
        }

        return extenso.substring(0, 1).toUpperCase() + extenso.substring(1);
    }

    private static String numeroPorExtenso(int numero) {
        if (numero == 0) return "zero";
        if (numero < 20) return UNIDADES[numero];

        if (numero < 100) {
            return DEZENAS[numero / 10] + (numero % 10 != 0 ? " e " + UNIDADES[numero % 10] : "");
        }

        if (numero < 1000) {
            return (numero == 100 ? "cem" : CENTENAS[numero / 100]) +
                    (numero % 100 != 0 ? " e " + numeroPorExtenso(numero % 100) : "");
        }

        if (numero < 1000000) {
            int milhares = numero / 1000;
            int resto = numero % 1000;

            String resultado = (milhares == 1 ? "mil" : numeroPorExtenso(milhares) + " mil");

            if (resto > 0) {
                if (resto < 100) {
                    resultado += " e ";
                } else {
                    resultado += " ";
                }
                resultado += numeroPorExtenso(resto);
            }

            return resultado;
        }

        return "Número muito grande";
    }
}
