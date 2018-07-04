package mobileIntegration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.tasks.Task;

import models.Registro;

/** Comunicação com o back-end firebase do aplicativo mobile */
public class FireBaseComunication {

	public static FireBaseComunication instance;

	public static FireBaseComunication getInstance() throws IOException {
		if (instance == null)
			instance = new FireBaseComunication();

		return instance;
	}

	private FileInputStream serviceAccount;
	private FirebaseOptions options;
	private DatabaseReference culturaReference;

	private ChildEventListener childEventRegistro;

	private FireBaseComunication() throws IOException  {
		this.serviceAccount = new FileInputStream(
				"C:\\Users\\Quaresma\\git\\mcpd\\target\\classes\\mcpd-b1333-firebase-adminsdk-4btsm-e3686817ae.json");
		this.options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://mcpd-b1333.firebaseio.com").build();
		//this.childEventRegistro = childEventRegistro;
	}
	
	public void save(Map<String, Registro> registros) {
		System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation()
				+ "mcpd-b1333-firebase-adminsdk-4btsm-e3686817ae.json");

		FirebaseApp.initializeApp(options);

		culturaReference = FirebaseDatabase.getInstance().getReference("/").child("Registro");
				
		culturaReference.setValueAsync(registros);
	}

		/*
		 * 
		 * 
		 *  Task task =
		 * culturaReference.push().setValue(registro);
		 */
		/*
		 * List<Registro> listaRegistros = new ArrayList<>();
		 * 
		 * childEventRegistro =
		 * culturaReference.child("dataRegistro").orderByChild("dataRegistro")
		 * .addChildEventListener(new ChildEventListener() {
		 * 
		 * @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
		 * Registro registro = dataSnapshot.getValue(Registro.class);
		 * registro.setKey(dataSnapshot.getKey()); listaRegistros.add(registro);
		 * 
		 * }
		 * 
		 * @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
		 * 
		 * }
		 * 
		 * @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
		 * 
		 * }
		 * 
		 * @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {
		 * 
		 * }
		 * 
		 * @Override public void onCancelled(DatabaseError databaseError) {
		 * 
		 * } }); culturaReference.addChildEventListener(childEventRegistro);
		 */
}
