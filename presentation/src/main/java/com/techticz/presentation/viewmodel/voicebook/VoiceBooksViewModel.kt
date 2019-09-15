package com.techticz.presentation.viewmodel.voicebook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techticz.data.model.VoiceMemo
import com.techticz.database.model.VoiceBook
import com.techticz.domain.interactor.voicebook.CreateVoiceBook
import com.techticz.domain.interactor.voicebook.GetVoiceBooks
import com.techticz.domain.interactor.voicememo.CreateVoiceMemo
import com.techticz.domain.interactor.voicememo.DeleteVoiceMemo
import com.techticz.domain.interactor.voicememo.RecentVoiceMemos
import com.techticz.domain.model.VoiceBooksRequest
import com.techticz.domain.model.VoiceMemosRequest
import io.reactivex.subscribers.DisposableSubscriber
import org.buffer.android.boilerplate.presentation.data.Resource
import org.buffer.android.boilerplate.presentation.data.ResourceState

import javax.inject.Inject

class VoiceBooksViewModel @Inject internal constructor(
    private val USECASE_GET_VOICE_BOOKS: GetVoiceBooks,
    private val USECASE_CREATE_VOICE_BOOK: CreateVoiceBook,
    private val USECASE_RECENT_VOICE_MEMOS: RecentVoiceMemos,
    private val USECASE_DELETE_VOICE_MEMO: DeleteVoiceMemo
): ViewModel(){

    private val voiceBooksLiveData:MutableLiveData<Resource<List<VoiceBook>>> = MutableLiveData()
    private val recentMemosLiveData:MutableLiveData<Resource<List<VoiceMemo>>> = MutableLiveData()
    private val voiceBookCreateLiveData:MutableLiveData<Resource<Long>> = MutableLiveData()
    private val voiceMemoDeleteLiveData:MutableLiveData<Resource<Int>> = MutableLiveData()

    init {

    }

    override fun onCleared() {
        USECASE_GET_VOICE_BOOKS.dispose()
        USECASE_CREATE_VOICE_BOOK.dispose()
        USECASE_RECENT_VOICE_MEMOS.dispose()
        USECASE_DELETE_VOICE_MEMO.dispose()
        super.onCleared()
    }

    fun fetchVoiceBooks(req:VoiceBooksRequest) {
        voiceBooksLiveData.postValue(Resource(ResourceState.LOADING,null,null))
        return USECASE_GET_VOICE_BOOKS.execute(VoiceBooksSubscriber(), req)
    }

    fun fetchRecentMemos(req:VoiceMemosRequest) {
        recentMemosLiveData.postValue(Resource(ResourceState.LOADING,null,null))
        return USECASE_RECENT_VOICE_MEMOS.execute(RecentVoiceMemosSubscriber(), req)
    }

    fun createVoiceBook(voiceBook:VoiceBook){
        voiceBookCreateLiveData.postValue(Resource(ResourceState.LOADING,null,"Creating voice book"))
        USECASE_CREATE_VOICE_BOOK.execute(CreateVoiceBookSubscriber(),voiceBook)
    }

    fun deleteVoiceMemo(memo: VoiceMemo) {
        voiceMemoDeleteLiveData.postValue(Resource(ResourceState.LOADING,null,"Deleting voice memo"))
        USECASE_DELETE_VOICE_MEMO.execute(DeleteVoiceMemoSubscriber(),memo)
    }

    fun getVoiceBooks(): MutableLiveData<Resource<List<VoiceBook>>> {
        return voiceBooksLiveData
    }

    fun getRecentMemosSignal(): MutableLiveData<Resource<List<VoiceMemo>>> {
        return recentMemosLiveData
    }

    fun getVoiceBookCreatedSignal(): MutableLiveData<Resource<Long>> {
        return voiceBookCreateLiveData
    }

    fun getVoiceMemoDeletedSignal(): MutableLiveData<Resource<Int>> {
        return voiceMemoDeleteLiveData
    }


    private inner class VoiceBooksSubscriber:DisposableSubscriber<List<VoiceBook>>() {
        override fun onComplete() {}

        override fun onNext(t: List<VoiceBook>?) {
            if(t != null) {
                voiceBooksLiveData.postValue(Resource(ResourceState.SUCCESS, t!!, null))
            } else {
                voiceBooksLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceBooksLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }

    private inner class RecentVoiceMemosSubscriber:DisposableSubscriber<List<VoiceMemo>>() {
        override fun onComplete() {}

        override fun onNext(t: List<VoiceMemo>?) {
            if(t != null) {
                recentMemosLiveData.postValue(Resource(ResourceState.SUCCESS, t!!, null))
            } else {
                recentMemosLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            recentMemosLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
        }

    }

    private inner class CreateVoiceBookSubscriber:DisposableSubscriber<Long>() {
        override fun onComplete() {}

        override fun onNext(t: Long?) {
            if(t != null) {
                voiceBookCreateLiveData.postValue(Resource(ResourceState.SUCCESS, t, "Voice Book created"))
            } else {
                voiceBookCreateLiveData.postValue(Resource(ResourceState.ERROR, null, "Empty Data"))
            }
        }

        override fun onError(t: Throwable?) {
            voiceBookCreateLiveData.postValue(Resource(ResourceState.ERROR, null, t?.message))
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