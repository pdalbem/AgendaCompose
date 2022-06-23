package br.edu.ifsp.agendacompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contatos")
class Contato (@PrimaryKey(autoGenerate = true)
               var id: Int,
               var nome:String,
               var fone:String,
               var email:String
): Serializable