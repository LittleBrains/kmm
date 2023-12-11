package kz.littlebrains.core


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CasesListModel(
    @SerialName("content")
    val content: List<Content>?,
    @SerialName("pageable")
    val pageable: Pageable?,
    @SerialName("last")
    val last: Boolean?,
    @SerialName("totalPages")
    val totalPages: Int?,
    @SerialName("totalElements")
    val totalElements: Int?,
    @SerialName("first")
    val first: Boolean?,
    @SerialName("size")
    val size: Int?,
    @SerialName("number")
    val number: Int?,
   //@SerialName("sort")
    //val sort: List<Any?>?,
    @SerialName("numberOfElements")
    val numberOfElements: Int?,
    @SerialName("empty")
    val empty: Boolean?
) {
    @Serializable
    data class Content(
        @SerialName("id")
        val id: Int?,
        @SerialName("caseNameRu")
        val caseNameRu: String?,
        @SerialName("caseNameKz")
        val caseNameKz: String?,
        @SerialName("files")
        val files: List<File?>?
    ) {
        @Serializable
        data class File(
            @SerialName("id")
            val id: Int?,
            @SerialName("fileId")
            val fileId: String?
        )
    }

    @Serializable
    data class Pageable(
       // @SerialName("sort")
       // val sort: List<Any?>?,
        @SerialName("pageNumber")
        val pageNumber: Int?,
        @SerialName("offset")
        val offset: Int?,
        @SerialName("pageSize")
        val pageSize: Int?,
        @SerialName("paged")
        val paged: Boolean?,
        @SerialName("unpaged")
        val unpaged: Boolean?
    )
}