package mobileIntegration;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/** Comunicação com o back-end firebase do aplicativo mobile */
public class FireBaseComunication {

	FileInputStream serviceAccount;
	FirebaseOptions options;

	public FireBaseComunication() throws IOException {
		
		this.serviceAccount = new FileInputStream(".json");
		
		this.options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("url do projeto MCPD")
				.build();;
		FirebaseApp.initializeApp(options);
																								
		DatabaseReference ref = FirebaseDatabase.getInstance().getReference("restricted_access/secret_document<= WTF!?");
		
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Object document = dataSnapshot.getValue();
				System.out.println(document);
			}
			
			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		});
	}

}
