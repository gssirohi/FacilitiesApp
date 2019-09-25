package com.techticz.remote.mapper


import com.techticz.data.model.Facility
import com.techticz.data.model.Option
import com.techticz.remote.model.Remote_Facility

import javax.inject.Inject

open class RemoteToDataEntityMapper_Facility @Inject constructor():RemoteToDataEntityMapper<Remote_Facility, Facility> {
    override fun mapFromRemote(remoteEntity: Remote_Facility): Facility {
        var dataEntity = Facility(
            remoteEntity.facility_id,
            remoteEntity.name,
            remoteEntity.options.map { r-> Option(r.name,r.icon,r.id) }
        )

        return dataEntity
    }
}