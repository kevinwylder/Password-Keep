package com.wylder.passwordkeep.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.storage.BasePassword;

/**
 * Created by kevin on 8/21/15.
 *
 * User interface to set the password
 */
public class PasswordFragment extends Fragment {

    private BasePassword passwordManager;

    private EditText password1;
    private EditText password2;
    private CheckBox showTextCheckBox;
    private Button submitButton;

    private Toast toast;

    private OnPasswordSelect callback = null;

    /**
     * Static factory method to give parameters to this fragment.
     * @return a new instance of this fragment
     */
    public static PasswordFragment newInstance(){
        PasswordFragment fragment = new PasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle sis){
        super.onCreate(sis);
        passwordManager = new BasePassword(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.password_set, container, false);
        if (passwordManager.passwordNotSet()) {
            TextView title = (TextView) root.findViewById(R.id.textView);
            title.setText("Set New Base Password");
        }
        // find the views
        password1 = (EditText) root.findViewById(R.id.editText);
        password2 = (EditText) root.findViewById(R.id.editText2);
        showTextCheckBox = (CheckBox) root.findViewById(R.id.checkBox);
        submitButton = (Button) root.findViewById(R.id.button6);
        // set listeners
        showTextCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // switch the password fields to text so they're visible
                if (b) {
                    password1.setInputType(InputType.TYPE_CLASS_TEXT);
                    password2.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    password1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                password1.setSelection(password1.getText().length());
                password2.setSelection(password2.getText().length());
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null) {
                    toast.cancel();
                }
                // set the base password
                String pass1 = password1.getText().toString();
                String pass2 = password2.getText().toString();
                if(pass1.length() == 0 || pass2.length() == 0){
                    toast = Toast.makeText(getActivity(), "Enter a Password into both fields", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (pass1.equals(pass2)) {
                    // passwords match, finalize it
                    passwordManager.setPassword(pass1);
                    passwordManager.checkPassword(pass1);   // must be checked for MainActivity
                    // send callback if one was set
                    if(callback != null){
                        callback.passwordSelected();
                    }
                    // hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                } else {
                    // user made a typo
                    toast = Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return root;
    }

    public interface OnPasswordSelect {
        /**
         * called when the user sucessfully sets their password
         */
        void passwordSelected();
    }

    /**
     * set a callback for when the user choose their password
     * @param callback an instance of OnPasswordSelect
     */
    public void setOnPasswordSelect(OnPasswordSelect callback){
        this.callback = callback;
    }

}
