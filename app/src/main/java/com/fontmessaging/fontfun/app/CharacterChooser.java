package com.fontmessaging.fontfun.app;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by charles on 4/28/14.
 */
public class CharacterChooser extends Fragment implements View.OnClickListener{
    ArrayList<Button> buttons;
    int currentButton;
    private final int selectedColor = 0xFF2FBBF7;
    private final int deselectedColor = 0xFFFFFFFF;

    private static final int[] BUTTON_IDS = {
            R.id.button65,
            R.id.button66,
            R.id.button67,
            R.id.button68,
            R.id.button69,
            R.id.button70,
            R.id.button71,
            R.id.button72,
            R.id.button73,
            R.id.button74,
            R.id.button75,
            R.id.button76,
            R.id.button77,
            R.id.button78,
            R.id.button79,
            R.id.button80,
            R.id.button81,
            R.id.button82,
            R.id.button83,
            R.id.button84,
            R.id.button85,
            R.id.button86,
            R.id.button87,
            R.id.button88,
            R.id.button89,
            R.id.button90,
            R.id.button97,
            R.id.button98,
            R.id.button99,
            R.id.button100,
            R.id.button101,
            R.id.button102,
            R.id.button103,
            R.id.button104,
            R.id.button105,
            R.id.button106,
            R.id.button107,
            R.id.button108,
            R.id.button109,
            R.id.button110,
            R.id.button111,
            R.id.button112,
            R.id.button113,
            R.id.button114,
            R.id.button115,
            R.id.button116,
            R.id.button117,
            R.id.button118,
            R.id.button119,
            R.id.button120,
            R.id.button121,
            R.id.button122,
            R.id.button49,
            R.id.button50,
            R.id.button51,
            R.id.button52,
            R.id.button53,
            R.id.button54,
            R.id.button55,
            R.id.button56,
            R.id.button57,
            R.id.button48
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttons = new ArrayList<Button>();
        currentButton = 1;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //    protected class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            DrawingActivity cur = (DrawingActivity)getActivity();
            buttons.get(currentButton).setTextColor(deselectedColor);
            Button b = (Button) view;
            Log.d("button clicked", (String) b.getText());
            b.getText();
            cur.changeChar(b.getText().charAt(0), true);
            b.setTextColor(selectedColor);
            currentButton = buttons.indexOf(b);

        }
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_character_chooser, container, false);
        for(int id: BUTTON_IDS){
            buttons.add((Button)view.findViewById(id));
        }
        for(Button b: buttons){
//            Log.d("button text", (String) b.getText());
            b.setOnClickListener(this);
        }
        return view;
    }
}
