package br.edu.ifsp.agendacompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Contact (@PrimaryKey(autoGenerate = true)
               var id: Int=0,
               var name:String,
               var phone:String,
               var email:String
)