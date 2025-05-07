package com.example.tyw.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weight")
data class Weight (var vaha: Double, var date: Long?  = null ){

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "weight_state")
    var weightState: Boolean = false

    @ColumnInfo(name = "user_id")
    var userId: String? = null


}