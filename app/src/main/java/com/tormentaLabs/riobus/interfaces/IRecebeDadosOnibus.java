package com.tormentaLabs.riobus.interfaces;

import com.tormentaLabs.riobus.model.Ponto;

import java.util.List;

/**
 * Created by pedro on 06/07/2014.
 */
public interface IRecebeDadosOnibus {

    public void recebeListaPontosCallback(List<Ponto> pontos, String msgErro);
}
