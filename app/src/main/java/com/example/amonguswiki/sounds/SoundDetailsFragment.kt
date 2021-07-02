package com.example.amonguswiki.sounds

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.amonguswiki.R
import com.example.amonguswiki.navigator.Navigator
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso

import java.io.IOException

@Suppress("DEPRECATION")
class SoundDetailsFragment : Fragment() {


    private  var mMediaPlayer: MediaPlayer?=null
    private lateinit var timetext: TextView
    private lateinit var curTime: TextView
    private lateinit var mSeekBar: SeekBar
    private lateinit var mPlay: ImageView
    private lateinit var imgSound: ImageView
    private lateinit var soundName: TextView
    lateinit var tb_sound_details: MaterialToolbar




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sound_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        (activity as AppCompatActivity?)!!.setSupportActionBar(tb_sound_details)
        soundName.text="Cuando un Tripulante es asesinado por el Impostor"

        tb_sound_details.setNavigationOnClickListener {
            Navigator.goBack()
        }
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/among-us-wiki.appspot.com/o/adminmirahg.png?alt=media&token=2b13b50e-28f6-4ff9-877e-37d57cf03370")
        .into((imgSound))


        mPlay.setOnClickListener {
            if (mMediaPlayer?.isPlaying == true){
                mMediaPlayer?.pause()
                mMediaPlayer = null
                mMediaPlayer?.release()
                mPlay.setImageResource(R.drawable.play)
            } else {
                val songResourceUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/among-us-8a1d9.appspot.com/o/audio%2Fkill.wav?alt=media&token=26308bf1-913d-4c8b-aecf-311dcba0b5ba")
                playContentUri(songResourceUri)
            }
        }
    }

    private fun setup(){

        imgSound=requireView().findViewById(R.id.iv_sounds)
        timetext= requireView().findViewById(R.id.totalTime)
        curTime=requireView().findViewById(R.id.currTime)
        mSeekBar=requireView().findViewById(R.id.mSeekbar)
        mPlay=requireView().findViewById(R.id.playbtn)
        soundName=requireView().findViewById(R.id.tv_sounds)
        tb_sound_details=requireView().findViewById(R.id.tb_sound_details)
    }

    @SuppressLint("HandlerLeak")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val current_position = msg.what
            mSeekBar.progress = current_position
            val cTime = createTimeLabel(current_position)
            curTime.text = cTime
        }
    }

    private fun createTimeLabel(duration: Int): String? {
        var timeLabel: String? = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60
        timeLabel += "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec
        return timeLabel
    }

    fun pauseSound() {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }


    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    fun playContentUri(uri: Uri) {
        mMediaPlayer  = null
        try {
            mMediaPlayer = MediaPlayer().apply {
                activity?.let { setDataSource(it, uri) }
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                prepare()
            }
            mMediaPlayer?.setOnPreparedListener(MediaPlayer.OnPreparedListener {
                val totalTime: String? = mMediaPlayer?.duration?.let { it1 -> createTimeLabel(it1) }
                timetext.text = totalTime
                mSeekBar.max = mMediaPlayer?.duration!!
                mMediaPlayer?.start()
                mPlay.setImageResource(R.drawable.pause)
            })

            mSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mMediaPlayer?.seekTo(progress)
                        mSeekBar.progress = progress
                    }
                    if (mSeekBar.progress==mMediaPlayer?.duration!!)  {
                        mPlay.setImageResource(R.drawable.play)
                        mMediaPlayer = null
                        mMediaPlayer?.release()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
            Thread {
                while (true) {
                    try {

                        if (mMediaPlayer?.isPlaying == true && mMediaPlayer!=null) {
                            val msg = Message()
                            msg.what = mMediaPlayer!!.currentPosition
                            handler.sendMessage(msg)
                            Thread.sleep(
                                50
                            )
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }

            }.start()
        } catch (e: IOException) {
            mMediaPlayer = null
            mMediaPlayer?.release()
        }
        mPlay.setImageResource(R.drawable.play)
    }

}