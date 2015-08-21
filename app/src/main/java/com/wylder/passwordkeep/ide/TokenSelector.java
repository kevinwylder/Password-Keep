package com.wylder.passwordkeep.ide;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.wylder.passwordkeep.R;
import com.wylder.passwordkeep.algorithm.DataType;
import com.wylder.passwordkeep.algorithm.Token;
import com.wylder.passwordkeep.algorithm.functions.add;
import com.wylder.passwordkeep.algorithm.functions.capitalize;
import com.wylder.passwordkeep.algorithm.functions.constant;
import com.wylder.passwordkeep.algorithm.functions.edit;
import com.wylder.passwordkeep.algorithm.functions.eval;
import com.wylder.passwordkeep.algorithm.functions.insert;
import com.wylder.passwordkeep.algorithm.functions.len;
import com.wylder.passwordkeep.algorithm.functions.letter;
import com.wylder.passwordkeep.algorithm.functions.remove;
import com.wylder.passwordkeep.algorithm.functions.rotate;
import com.wylder.passwordkeep.algorithm.functions.select;
import com.wylder.passwordkeep.algorithm.functions.sum;

/**
 * Created by kevin on 8/19/15.
 *
 * Class to show a dialog to select a token
 */
public class TokenSelector {

    interface OnTokenSelected {
        void onSelect();
    }

    private OnTokenSelected callback;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int selected = 0;
            switch(view.getId()){
                case R.id.button1:
                    selected = 0;
                    break;
                case R.id.button2:
                    selected = 1;
                    break;
                case R.id.button3:
                    selected = 2;
                    break;
                case R.id.button4:
                    selected = 3;
                    break;
                default:
                    box.deleteSelf();
            }
            box.setToken(functions[selected]);
            callback.onSelect();
            dialog.dismiss();
        }
    };

    private TokenBox box;
    private AlertDialog dialog;
    private Token[] functions;

    public TokenSelector(Context ctx, TokenBox box){
        this.box = box;
        // define the functions to choose
        if(box.type == DataType.ACTION) {
            functions = new Token[]{
                    new add(),
                    new insert(),
                    new remove(),
                    new edit()
            };
        } else if(box.type == DataType.INT) {
            functions = new Token[]{
                    new len(),
                    new sum(),
                    new eval(),
                    new constant()
            };
        } else {
            functions = new Token[]{
                    new select(),
                    new rotate(),
                    new letter(),
                    new capitalize()
            };
        }

        // inflate the layout
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = layoutInflater.inflate(R.layout.ide_select_function, null);

        // find the views
        Button button1 = (Button) root.findViewById(R.id.button1);
        Button button2 = (Button) root.findViewById(R.id.button2);
        Button button3 = (Button) root.findViewById(R.id.button3);
        Button button4 = (Button) root.findViewById(R.id.button4);
        Button delete = (Button) root.findViewById(R.id.button5);

        // setup the views
        button1.setText(functions[0].getOperatorName());
        button2.setText(functions[1].getOperatorName());
        button3.setText(functions[2].getOperatorName());
        button4.setText(functions[3].getOperatorName());
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        delete.setOnClickListener(listener);

        // create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Select Function");
        builder.setView(root);
        dialog = builder.create();
        dialog.show();
    }

    public void setOnTokenSelected(OnTokenSelected callback){
        this.callback = callback;
    }

}
