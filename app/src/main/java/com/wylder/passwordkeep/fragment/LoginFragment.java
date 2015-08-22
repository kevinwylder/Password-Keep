package com.wylder.passwordkeep.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.activity.MainActivity;
import com.wylder.passwordkeep.storage.BasePassword;

/**
 * Created by kevin on 8/21/15.
 *
 * Fragment to log the user in
 */
public class LoginFragment extends Fragment {

    BasePassword password;

    EditText passwordField;
    Toast toast = null;

    /**
     * Static factory method to give parameters to this fragment.
     * @return a new instance of this fragment
     */
    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        password = new BasePassword(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.password_enter, container, false);
        root.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        passwordField = (EditText) root.findViewById(R.id.editText);
        passwordField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP){
                    checkLogin();
                    return true;
                }
                return false;
            }
        });
        return root;
    }

    /**
     * check the password
     */
    private void checkLogin(){
        if(toast != null) {
            toast.cancel();
        }
        if(password.checkPassword(passwordField.getText().toString())) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        } else {
            passwordField.requestFocus();
            toast = Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
