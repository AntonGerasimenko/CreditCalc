package by.minsk.pipe.creditcalc.Logic;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by gerasimenko on 10.09.2015.
 */
public class SeparNum implements TextWatcher {

    public static final char SEP_CHAR = ',';

    private EditText editText;

    public SeparNum(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String oldString = s.toString();
        String string = Convert.separate(oldString);

        /*Log.d("TRIM ", oldString + " -> " + string);*/

        if (!oldString.equals(string)) {
           setText(string);
        }
    }

    private void setText(String text) {
        editText.setText(text);
        editText.setSelection(text.length());
    }
}
