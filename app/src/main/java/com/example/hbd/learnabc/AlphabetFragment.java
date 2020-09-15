package com.example.hbd.learnabc;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class AlphabetFragment extends Fragment {

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

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public AlphabetFragment() {
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
        //this.mAudioManager = (AudioManager)getLayoutInflater().getContext().getSystemService(Context.AUDIO_SERVICE);

        View rootView = inflater.inflate(R.layout.activity_alphabet, container, false);
        //Log.v(, "print the value"+ getActivity() );

        final ArrayList<Word> words = new ArrayList<Word>();
        // making the arraylist as final. Now MediaPlayer can acess the ArrayList.Because MediaPlayer is static method



         //as soon as onCreateView invokes MAudioManager will clean the resource already allocated

        words.add(new Word("A", "Apple", R.drawable.apple, R.raw.a));
        words.add(new Word("B", "BaseBall", R.drawable.baseball, R.raw.b));
        words.add(new Word("C", "Clock", R.drawable.ck, R.raw.c));
        words.add(new Word("D", "Donkey", R.drawable.donkey, R.raw.d));
        words.add(new Word("E", "Elephant", R.drawable.ele, R.raw.e));
        words.add(new Word("F", "Father", R.drawable.family_father, R.raw.f));
        words.add(new Word("G", "Grandfather", R.drawable.family_grandfather, R.raw.g));
        words.add(new Word("H", "Hungry", R.drawable.hung, R.raw.h));
        words.add(new Word("I", "Internet", R.drawable.internet, R.raw.i));
        words.add(new Word("J", "Justice", R.drawable.justice, R.raw.j));
        words.add(new Word("K", "Kangaroo", R.drawable.kangaroo, R.raw.k));
        words.add(new Word("L", "London", R.drawable.london, R.raw.l));
        words.add(new Word("M", "Monkey", R.drawable.monk, R.raw.m));
        words.add(new Word("N", "Norway", R.drawable.norwayimages, R.raw.n));
        words.add(new Word("O", "Overtime", R.drawable.over, R.raw.o));
        words.add(new Word("P", "Pillow", R.drawable.pillow, R.raw.p));
        words.add(new Word("Q", "Question", R.drawable.question, R.raw.q));
        words.add(new Word("R", "Rabbit", R.drawable.rabbit, R.raw.r));
        words.add(new Word("S", "Superman", R.drawable.superman, R.raw.s));
        words.add(new Word("T", "Telephone", R.drawable.teleph, R.raw.t));
        words.add(new Word("U", "Underwear", R.drawable.under, R.raw.u));
        words.add(new Word("V", "Vaccinate", R.drawable.vaccination, R.raw.v));
        words.add(new Word("W", "World Wide Web", R.drawable.world, R.raw.w));
        words.add(new Word("X", "Xylophone", R.drawable.xylophone, R.raw.x));
        words.add(new Word("Y", "Yogurt", R.drawable.yogurt, R.raw.y));
        words.add(new Word("Z", "Zebra", R.drawable.zebra, R.raw.z));

        AlphabetAdapter AlphaAdapter = new AlphabetAdapter(getActivity(), words);
        ListView listView = (ListView) rootView.findViewById(R.id.list_word);
        listView.setAdapter(AlphaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                //release the media player if already allocatedto someone

                //we are using the concept of audioFocus
                if(mAudioManager != null) {
                    int result = mAudioManager.requestAudioFocus(af, AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        releaseMediaPlayer();
                        //Log.v("NumbersActivity", "Current word: " + word);
                        mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioID());
                        mMediaPlayer.start();

                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            }
        });
        return rootView;
    }
    //when the activity is stopped,release the media Player resources because we wouldn't be playing any more sound
    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private  void releaseMediaPlayer()
    {
        if(mMediaPlayer!= null)
        {
            mMediaPlayer.release();
            mMediaPlayer= null;
            mAudioManager.abandonAudioFocus(af);
        }
    }
}
