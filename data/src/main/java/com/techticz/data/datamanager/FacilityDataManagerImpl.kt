package com.techticz.data.datamanager


import com.techticz.data.model.FacilityData
import com.techticz.data.repository.FacilityDatabaseRepository
import com.techticz.data.repository.FacilityRemoteRepository

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import sun.rmi.runtime.Log
import javax.inject.Inject

class FacilityDataManagerImpl @Inject constructor(val remoteRepo: FacilityRemoteRepository, val dbRepo: FacilityDatabaseRepository): FacilityDataManager {

    override fun getFacilityData(latest:Boolean): Flowable<FacilityData> {
        print("DATA: getting facilities latest:"+latest)
        if(latest) {
            var data = remoteRepo.getFacilityData()
            data
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    print("DATA: inserting facilities")
                       dbRepo.insertFacilityData(it).toFlowable()
                           .observeOn(Schedulers.io())
                           .subscribeOn(Schedulers.io())
                           .doOnNext {
                                print("DATA: facilities inserted")
                            }
                           .subscribe()
                }
                .doOnError { }
                .subscribe()
            return data
        } else {
            var data =  dbRepo.getFacilityData().toFlowable()
            return data
        }

    }

}