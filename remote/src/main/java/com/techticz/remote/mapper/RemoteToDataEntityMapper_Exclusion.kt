package com.techticz.remote.mapper


import com.techticz.data.model.Exclusion
import com.techticz.data.model.ExclusiveItem
import com.techticz.data.model.Facility
import com.techticz.data.model.Option
import com.techticz.remote.model.Remote_Exclusion
import com.techticz.remote.model.Remote_ExclusiveItem
import com.techticz.remote.model.Remote_Facility

import javax.inject.Inject

open class RemoteToDataEntityMapper_Exclusion @Inject constructor():RemoteToDataEntityMapper<List<Remote_ExclusiveItem>, Exclusion> {
    override fun mapFromRemote(remoteEntity: List<Remote_ExclusiveItem>): Exclusion {
        var exclusionItemList = mutableListOf<ExclusiveItem>()
        remoteEntity.forEach {
            exclusionItemList.add(ExclusiveItem(it.facility_id,it.options_id))
        }
        var dataEntity = Exclusion(exclusionItemList)

        return dataEntity
    }
}