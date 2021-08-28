package com.felipeg.intelligentnotes.annotation.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface AnnotationService {

    @GET("notes")
    fun listUserNotes() : Deferred<Response<List<NoteOutput>>>

    data class NoteOutput(var id: Long, var title: String, var content: String, var creationDate: String)
}