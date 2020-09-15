package com.example.hbd.learnabc;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumberFragment extends Fragment {

   private MediaPlayer mMediaPlayer;

   public AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener af = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(mAudioManager != null) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mMediaPlayer.start();
                    //release the memory using helper class for the audio player

                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    releaseMediaPlayer();
                }
            }
        }
    };

   private MediaPlayer.OnCompletionListener mCompletionListner= new MediaPlayer.OnCompletionListener() {
       @Override
       public void onCompletion(MediaPlayer mp) {
           releaseMediaPlayer();
       }
   };

    public NumberFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.activity_numbers, container, false);

        final ArrayList<Word> wordss = new ArrayList<Word>();

        wordss.add(new Word("0","Zero",R.drawable.zero, R.raw.a0));
        wordss.add(new Word("1", "One",R.drawable.number_one,R.raw.a1));
        wordss.add(new Word("2", "Two",R.drawable.number_two,R.raw.a2));
        wordss.add(new Word("3", "Three",R.drawable.number_three,R.raw.a3));
        wordss.add(new Word("4", "Four",R.drawable.number_four,R.raw.a4));
        wordss.add(new Word("5", "Five",R.drawable.number_five,R.raw.a5));
        wordss.add(new Word("6", "Six",R.drawable.number_six,R.raw.a6));
        wordss.add(new Word("7", "Seven",R.drawable.number_seven,R.raw.a7));
        wordss.add(new Word("8", "Eight",R.drawable.number_eight,R.raw.a8));
        wordss.add(new Word("9", "Nine",R.drawable.number_nine,R.raw.a9));
        wordss.add(new Word("10", "Ten",R.drawable.number_ten,R.raw.a10));

        AlphabetAdapter numAdapter = new AlphabetAdapter(getActivity(),wordss);
        ListView listView= (ListView) rootView.findViewById(R.id.list_numbers);
        listView.setAdapter(numAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = wordss.get(position);

                if(mAudioManager != null) {
                    int result = mAudioManager.requestAudioFocus(af, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        releaseMediaPlayer();
                        mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioID());
                        mMediaPlayer.start();
                        mMediaPlayer.setOnCompletionListener(mCompletionListner);
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer()
    {
        if(mMediaPlayer != null)
        {
            mMediaPlayer.release();
            mMediaPlayer=null;

        }
    }

}
