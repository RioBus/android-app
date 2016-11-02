package com.tormentaLabs.riobus.common.services;

import com.tormentaLabs.riobus.common.models.Line;

import java.util.ArrayList;
import java.util.List;

public class LineService {

    private static LineService ourInstance = new LineService();

    public static LineService getInstance() {
        return ourInstance;
    }

    private LineService() {
    }

    public List<Line> getLines() {
        List<Line> items = new ArrayList<>();
        items.add(new Line("485", "FUNDAO X GENERAL OSORIO"));
        items.add(new Line("345", "BARRA DA TIJUCA X CANDELARIA"));
        items.add(new Line("664", "OUTRO X ENDERECO"));
        items.add(new Line("486", "OUTRA X LINHA"));
        items.add(new Line("130", "LINHA X ALTERNATIVA"));
        items.add(new Line("434", "CAMINHO X DESCONHECIDO"));
        items.add(new Line("343", "SEM X DESTINO"));
        items.add(new Line("281", "CORACAO X PARTIDO"));
        items.add(new Line("447", "LOREM X IPSUM"));
        items.add(new Line("696", "SIT X DOLOR"));
        items.add(new Line("677", "DESCONHECIDO"));
        items.add(new Line("669", "MUSSUM X IPSUM"));
        items.add(new Line("666", "SEM X ENDERECO"));
        items.add(new Line("420", "NILOPOLIS X BARRA"));
        items.add(new Line("415", "CAXIAS X BARRA"));
        items.add(new Line("230", "PEDRA DE GUARATIBA X PINDAMONHANGABA"));
        items.add(new Line("660", "CAIXA PREGO X FIM DO MUNDO"));
        return items;
    }
}
