package com.kavak.brastlewark.data.dao

import androidx.paging.DataSource
import androidx.room.*
import com.kavak.brastlewark.data.entities.Citizen

@Dao
interface CitizenDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(itm: Citizen)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Citizen>)

    @Update
    fun update(itm: Citizen)

    @Delete
    fun delete(item: Citizen)

    @Delete
    fun delete(items: List<Citizen>)

    @Query("SELECT * FROM citizens ORDER BY name ASC")
    fun getAll(): List<Citizen>

    @Query("SELECT * FROM citizens WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun getCitizensByName(query: String): List<Citizen>

    @Query("SELECT age FROM citizens  ORDER BY age ASC LIMIT 1")
    fun getMinAge(): Int

    @Query("SELECT age FROM citizens  ORDER BY age DESC LIMIT 1")
    fun getMaxAge(): Int

    @Query("SELECT height FROM citizens  ORDER BY height ASC LIMIT 1")
    fun getMinHeight(): Float


    @Query("SELECT height FROM citizens  ORDER BY height DESC LIMIT 1")
    fun getMaxHeight(): Float


    @Query("SELECT weight FROM citizens  ORDER BY weight ASC LIMIT 1")
    fun getMinWeight(): Float


    @Query("SELECT weight FROM citizens  ORDER BY weight DESC LIMIT 1")
    fun getMaxWeight(): Float


    @Query("SELECT * FROM citizens WHERE age>=:filterAge AND height>=:filterHeight AND weight>=:filterWeight ORDER BY name ASC")
    fun filter(filterAge: Int, filterHeight: Int, filterWeight: Int): List<Citizen>


}