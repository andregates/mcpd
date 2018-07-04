package util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mobileIntegration.FireBaseComunication;
import models.Registro;

public class FireBaseStart {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Map<String, Registro> registros = new HashMap<>();
		Registro registro = new Registro();
		registro.setRegistroId(87578);
		registro.setEscala(955);
		registro.setObs("Massa hehe EAE");
		registros.put("rere", registro);

		FireBaseComunication baseComunication = FireBaseComunication.getInstance();
		baseComunication.save(registros);

	}

}
