package com.felipeg.intelligentnotes.annotation.data

import com.felipeg.intelligentnotes.configuration.RetrofitConfig
import com.felipeg.intelligentnotes.annotation.data.model.Annotation

class AnnotationDataSource {

    suspend fun listMyNotes(token: String) : List<Annotation> {
        val response = RetrofitConfig().annotationService(token).listUserNotes().await()
        return mapAnnotations(response.body())
    }

    private fun mapAnnotations(output: List<AnnotationService.NoteOutput>?) : List<Annotation>{
        val annotations = mutableListOf<Annotation>()
        output?.forEach {
            val (id, title, content, creationDate) = it
            val annotation = Annotation(id, title, content, creationDate)
            annotations.add(annotation)
        }
        return annotations
    }
}