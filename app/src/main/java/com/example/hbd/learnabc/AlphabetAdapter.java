package com.example.hbd.learnabc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlphabetAdapter extends ArrayAdapter<Word> {


    public AlphabetAdapter(Context context, ArrayList<Word> pWords) {
        super(context,0, pWords);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);

        TextView l = (TextView) listItemView.findViewById(R.id.letter);
        l.setText(currentWord.getAphabet());
        TextView w = (TextView) listItemView.findViewById(R.id.word);
        w.setText(currentWord.getSymbol());
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);
        iconView.setImageResource(currentWord.getImageR());

        return  listItemView;
    }
}
