package com.techticz.presentation.viewmodel.voicememo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import com.techticz.domain.interactor.voicebook.GetVoiceBook
import com.techticz.domain.interactor.voicememo.CreateVoiceMemo
import com.techticz.domain.interactor.voicememo.DeleteVoiceMemo
import com.techticz.domain.interactor.voicememo.GetVoiceMemos
import com.techticz.domain.model.VoiceMemosRequest
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.presentation.data.Resource
import org.buffer.android.boilerplate.presentation.data.ResourceState

import javax.inject.Inject

class VoiceMemoViewModel @Inject internal constructor(
    private val USECASE_GET_VOICE_MEMOS: GetVoiceMemos,
    private val USECASE_CREATE_VOICE_MEMO: CreateVoiceMemo,
    private val USECASE_GET_VOICE_BOOK: GetVoiceBook,
    private val USECASE_DELETE_VOICE_MEMO: DeleteVoiceMemo
): ViewModel(){

    private val voiceMemosLiveData:MutableLiveData<Resource<List<VoiceMemo>>> = MutableLiveData()
    private val voiceMemoCreateLiveData:MutableLiveData<Resource<Long>> = MutableLiveData()
    private val voiceBookLiveData:MutableLiveData<Resource<VoiceBook>> = MutableLiveData()
    private val voiceMemoDeleteLiveData:MutableLiveData<Resource<Int>> = MutableLiveData()

    init {

    }

    override fun onCleared() {
        USECASE_GET_VOICE_MEMOS.dispose()
        USECASE_CREATE_VOICE_MEMO.dispose()
        USECASE_GET_VOICE_BOOK.dispose()
        USECASE_DELETE_VOICE_MEMO.dispose()
        super.onCleared()
    }

    fun fetchVoiceMemos(req: VoiceMemosRequest) {
        voiceMemosLiveData.postValue(Resource(ResourceState.LOADING,null,null))
        USECASE_GET_VOICE_MEMOS.execute(VoiceMemosSubscriber(), req)
    }
    fun fetchVoiceBook(req: Long) {
        voiceBookLiveData.postValue(Resource(ResourceState.LOADING,null,null))
        USECASE_GET_VOICE_BOOK.execute(VoiceBookSubscriber(), req)
    }
    fun createVoiceMemo(memo: VoiceMemo) {
        voiceMemoCreateLiveData.postValue(Resource(ResourceState.LOADING,null,"Creating voice memo"))
        USECASE_CREATE_VOICE_MEMO.execute(CreateVoiceMemoSubscriber(),memo)

    }
    fun deleteVoiceMemo(memo: VoiceMemo) {
        voiceMemoDeleteLiveData.postValue(Resource(ResourceState.LOADING,null,"Deleting voice memo"))
        USECASE_DELETE_VOICE_MEMO.execute(DeleteVoiceMemoSubscriber(),memo)
    }

    fun getVoiceMemos(): MutableLiveData<Resource<List<VoiceMemo>>> {
        return voiceMemosLiveData
    }

    fun getVoiceMemoCreatedSignal():MutableLiveData<Resource<Long>>{
        return voiceMemoCreateLiveData
    }

    fun getVoiceBookLoadedSignal():MutableLiveData<Resource<VoiceBook>>{
        return voiceBookLiveData
    }

    fun getVoiceMemoDeletedSignal(): MutableLiveData<Resource<Int>> {
        return voiceMemoDeleteLiveData
    }


    private inner class VoiceMemosSubscriber:DisposableSubscriber<List<VoiceMemo>>() {
        override fun onComplete() {}

        override fun onNext(t: List<VoiceMemo>?) {
            if(t != null) {
                voiceMemosLiveData.postValue(Resource(ResourceState.SUCCESS, t!!, null))
            } else {
                voiceMemosLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceMemosLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }

    private inner class VoiceBookSubscriber:DisposableSubscriber<VoiceBook>() {
        override fun onComplete() {}

        override fun onNext(t: VoiceBook?) {
            if(t != null) {
                voiceBookLiveData.postValue(Resource(ResourceState.SUCCESS, t!!, null))
            } else {
                voiceBookLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceBookLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }

    private inner class CreateVoiceMemoSubscriber:DisposableSubscriber<Long>() {
        override fun onComplete() {}

        override fun onNext(t: Long?) {
            if(t != null) {
                voiceMemoCreateLiveData.postValue(Resource(ResourceState.SUCCESS, t, "Voice Memo created"))
            } else {
                voiceMemoCreateLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceMemoCreateLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }

    private inner class DeleteVoiceMemoSubscriber:DisposableSubscriber<Int>() {
        override fun onComplete() {}

        override fun onNext(t: Int?) {
            if(t != null) {
                voiceMemoDeleteLiveData.postValue(Resource(ResourceState.SUCCESS, t, "Voice Memo deleted"))
            } else {
                voiceMemoDeleteLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceMemoDeleteLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }
}