package com.felipeg.intelligentnotes.annotation.data

import com.felipeg.intelligentnotes.annotation.data.model.Annotation

class AnnotationRepository(private val annotationDataSource: AnnotationDataSource) {

    suspend fun listMyNotes(token: String) : List<Annotation> {
        return annotationDataSource.listMyNotes(token)
    }

}