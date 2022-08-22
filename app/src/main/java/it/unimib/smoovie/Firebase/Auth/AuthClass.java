package it.unimib.smoovie.Firebase.Auth;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import it.unimib.smoovie.Firebase.Model.User;
import it.unimib.smoovie.utils.Constants;

public class AuthClass {


    FirebaseAuth mAuth;
    Boolean result;
    public void start(){
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean createUser(String email, String username, String password){
        result = false;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(email, username, Constants.BASIC_AVATAR_URL, userId);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                result = true;
                                                System.out.println("Registrato Ziopera");
                                            }
                                            else{
                                                result = false;
                                                System.out.println("Non worka dioca");
                                            }
                                        }
                                    });

                        }
                        else {
                            result = false;
                            String message = task.getException().getMessage();
                            System.out.println(message);
                        }
                    }
                });
        return result;
    }





}


