package com.gssirohi.techticz.voicebook.ui.voicebook

import android.Manifest.permission.*
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.techticz.app.R
import com.techticz.app.ui.voicememo.VoiceMemoBottomSheetFragment
import com.techticz.app.ui.voicememo.VoiceMemoBottomSheetFragment.Companion.VIEW_MODE_CREATE
import com.techticz.app.ui.voicememo.VoiceMemoBottomSheetFragment.Companion.VIEW_MODE_UPDATE
import com.techticz.app.utils.AppPref
import com.techticz.app.utils.AppUtils
import com.techticz.data.model.MemoAudio
import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import com.techticz.domain.model.VoiceMemosRequest
import com.techticz.presentation.ViewModelFactory
import com.techticz.presentation.viewmodel.voicebook.VoiceBooksViewModel
import com.techticz.presentation.viewmodel.voicememo.VoiceMemoViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.voicebook_activity.*
import org.buffer.android.boilerplate.presentation.data.ResourceState
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.inject.Inject

class VoicebookActivity : AppCompatActivity(), VoiceMemoBottomSheetFragment.BottomSheetCallBack {
    private var actionRecording: Boolean = false
    private var memoToBeCreated: VoiceMemo? = null
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var voiceMemoViewModel: VoiceMemoViewModel
    private lateinit var voiceBookViewModel: VoiceBooksViewModel

    private var voiceBookId: Long = 0L
    private var voiceBookColor:Int = 0
    private var voiceBookName:String = ""
    private val PERMISSION_REQUEST_CODE: Int = 10
    private val SPEECH_RECOGNITION_CODE: Int = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.voicebook_activity)
        AndroidInjection.inject(this)

        voiceBookId = intent.getLongExtra("voiceBookId",-1L)
        voiceBookColor = intent.getIntExtra("voiceBookColor",-1)
        voiceBookName = intent.getStringExtra("voiceBookName")
        actionRecording = intent.getBooleanExtra("startRecording",false)
        fab_speak.setBackgroundTintList(ColorStateList.valueOf(voiceBookColor));

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar?.setNavigationOnClickListener { view -> onBackPressed() }
        setupVoiceBookView()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, VoicebookFragment.newInstance())
                .commitNow()
        }

        if(voiceBookId == AppPref.getDefaultBookId(this)){
            b_set_default.visibility = View.GONE
            tv_default.visibility = View.VISIBLE
        } else {
            b_set_default.visibility = View.VISIBLE
            tv_default.visibility = View.GONE
        }
        b_set_default.setOnClickListener{
            AppPref.setDefaultBookId(this,voiceBookId)
            b_set_default.visibility = View.GONE
            tv_default.visibility = View.VISIBLE
        }



        fab_speak.setOnClickListener{
            startRecording()
        }

        voiceMemoViewModel = ViewModelProviders.of(this,viewModelFactory).get(VoiceMemoViewModel::class.java)
        voiceBookViewModel = ViewModelProviders.of(this,viewModelFactory).get(VoiceBooksViewModel::class.java)

        voiceMemoViewModel.fetchVoiceMemos(VoiceMemosRequest(voiceBookId))
        voiceMemoViewModel.fetchVoiceBook(voiceBookId)

        if(actionRecording){
            actionRecording = false
            startRecording()
        }
    }

    override fun onResume() {
        super.onResume()
        getViewModel().getVoiceMemoCreatedSignal().observe(this, androidx.lifecycle.Observer {
            if (it != null) this.onCreateMemoSignal(it.status, it.data, it.message)
        })

        getViewModel().getVoiceBookLoadedSignal().observe(this, androidx.lifecycle.Observer {
            if (it != null) this.onVoiceBookLoadedSignal(it.status, it.data, it.message)
        })
        getViewModel().getVoiceMemoDeletedSignal().observe(this, androidx.lifecycle.Observer {
            if (it != null) this.onVoiceMemoDeletedSignal(it.status, it.data, it.message)
        })
    }

    private fun onVoiceMemoDeletedSignal(status: ResourceState, data: Int?, message: String?) {
        when(status){

            ResourceState.SUCCESS->{
                getViewModel().fetchVoiceBook(voiceBookId)
                getViewModel().fetchVoiceMemos(VoiceMemosRequest(voiceBookId))
            }

        }

    }
    private fun onVoiceBookLoadedSignal(status: ResourceState, data: VoiceBook?, message: String?) {
        when(status) {
            ResourceState.LOADING -> {}
            ResourceState.SUCCESS -> {
               // setupScreenForSuccess()
                tv_memo_count.text = ""+data!!.memoCount+" voice memo"
                fab_speak.setBackgroundTintList(
                    ColorStateList.valueOf(data.color));
            }
            ResourceState.ERROR -> {
                //setupScreenForError("Voice memo could not be created!!")
                // Snackbar.make(coordinatorLayout, "Voice memo could not be created!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupVoiceBookView() {
        toolbar_layout.title = voiceBookName
        toolbar?.title = voiceBookName
        supportActionBar!!.title = voiceBookName
        app_bar_content.setBackgroundColor(voiceBookColor)
    }

    fun getViewModel():VoiceMemoViewModel{
        return voiceMemoViewModel
    }

    private fun startRecording() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Speak now..."
        )

        intent.putExtra("android.speech.extra.GET_AUDIO_FORMAT", "audio/AMR")
        intent.putExtra("android.speech.extra.GET_AUDIO", true)


        try {
            startActivityForResult(intent, SPEECH_RECOGNITION_CODE)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "Sorry! Speech recognition is not supported in this device.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun onCreateMemoSignal(status: ResourceState, data: Long?, message: String?) {
        when(status){
            ResourceState.LOADING->{
             //   setupScreenForLoadingState()
            }
            ResourceState.SUCCESS->{
             //   setupScreenForSuccess()
                Snackbar.make(coordinatorLayout, "Voice memo created.", Snackbar.LENGTH_LONG).show()
                voiceMemoViewModel.fetchVoiceMemos(VoiceMemosRequest(voiceBookId))
                voiceMemoViewModel.fetchVoiceBook(voiceBookId)
            }
            ResourceState.ERROR->{
            //    setupScreenForError("Voice memo could not be created!!")
               // Snackbar.make(coordinatorLayout, "Voice memo could not be created!!", Snackbar.LENGTH_LONG).show()
            }
        }
    }
    fun setupScreenForSuccess() {
        loading_view.visibility = View.GONE
        error_view.visibility = View.GONE
        container.visibility = View.VISIBLE
    }

    fun setupScreenForError(message: String?) {
        loading_view.visibility = View.GONE
        container.visibility = View.GONE
        error_view.visibility = View.VISIBLE
    }

    fun setupScreenForLoadingState() {
        loading_view.visibility = View.VISIBLE
        container.visibility = View.GONE
        error_view.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionsGranted(coordinatorLayout)

        when(requestCode) {
            SPEECH_RECOGNITION_CODE->{
                if (resultCode == RESULT_OK && null != data) {
                    val result_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    Log.i("RESULTS_TEXT", result_text!![0])

                    // directory for audio file.
                    var directory_audioFile: String? = null

                        directory_audioFile =
                            Environment.getExternalStorageDirectory().absolutePath + File.separator + "VoiceBook"+File.separator+voiceBookName

                        val tempAudioFolder = File(directory_audioFile)

                        if (!tempAudioFolder.exists()) {
                            tempAudioFolder.mkdirs()
                            Log.i("TEMP_AUDIO_FOLDER", "TempAudio created")
                        }

                    var timeStamp = Calendar.getInstance().timeInMillis

                    // GET AUDIO FROM SPEECH-TO-TEXT SERVICES AND SAVE IN APPROPRIATE LOCATION.
                    try {

                        val audioUri = data?.data
                        val contentResolver = contentResolver

                        val audioFileName = ""+timeStamp + ".mp3"

                        val file = File(directory_audioFile, audioFileName)
                        val filestream = contentResolver.openInputStream(audioUri!!)
                        val out = FileOutputStream(file)


                        try {

                            val buffer = ByteArray(4 * 1024) // or other buffer size
                            var read: Int

                            while (true) {
                                read = filestream!!.read(buffer)
                                if(read == -1)break
                                out.write(buffer, 0, read)
                            }
                            out.flush()
                            out.close()

                        } finally {
                            out.close()
                            filestream!!.close()
                        }

                        var transcript = result_text[0]
                        var title = when(transcript.length > 15){
                                            true->transcript.substring(0,15)
                            else-> transcript.substring(0,transcript.length-1)
                        }
                        var memoAudio = MemoAudio(audioFileName,directory_audioFile,"",title)
                        memoToBeCreated = VoiceMemo(0,voiceBookId,title,transcript,memoAudio,timeStamp)
                        var color = AppUtils.darkerLighterColor(voiceBookColor,0.7f)
                        VoiceMemoBottomSheetFragment.newInstance(VIEW_MODE_CREATE,color).show(supportFragmentManager, "bottomSheet")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }


    private fun permissionsGranted(vi: View) {

        if (checkPermission()) {

        } else {
            Snackbar.make(vi, "Please allow permissions.", Snackbar.LENGTH_LONG).show()
        }

        if (!checkPermission()) {

            requestPermission()

        }
    }

    // handles permissions for API 23 and up
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, RECORD_AUDIO)
        val result1 = ContextCompat.checkSelfPermission(applicationContext, WRITE_EXTERNAL_STORAGE)

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {

        ActivityCompat.requestPermissions(
            this,
            arrayOf<String>(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0) {

                val audioAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED


                if (audioAccepted && storageAccepted) {
                    //Snackbar.make(view, "Permissions Granted", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(this@VoicebookActivity, "Permissions Granted", Toast.LENGTH_LONG)
                        .show()
                } else {

                    Snackbar.make(
                        coordinatorLayout,
                        "Permissions Denied. Cannot access microphone and storage",
                        Snackbar.LENGTH_LONG
                    ).show()

                    //Toast.makeText(MainActivity.this, "Permissions Denied. Cannot access microphone and storage.", Toast.LENGTH_LONG).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
                            showMessageOKCancel("All permissions are needed for Audio Memo to function properly",
                                DialogInterface.OnClickListener { dialog, which ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(
                                            arrayOf(
                                                RECORD_AUDIO,
                                                WRITE_EXTERNAL_STORAGE
                                            ),
                                            PERMISSION_REQUEST_CODE
                                        )
                                    }
                                })
                            return
                        }
                    }

                }
            }
        }
    }


    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@VoicebookActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onVoiceMemoRequested(): VoiceMemo {
        return memoToBeCreated!!
    }

    override fun onVoiceMemoSubmit(memo: VoiceMemo, viewMode: Int) {
        if(viewMode == VIEW_MODE_CREATE) {
            memo.voiceBookId = voiceBookId
            memoToBeCreated = memo
            voiceMemoViewModel.createVoiceMemo(memoToBeCreated!!)
        }
    }

}
