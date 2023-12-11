package kz.alseco.core.network.ktor

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kz.alseco.core.network.models.ErrorModel


data class KtorResponse<T>(
    private val status: Status,
    private val data: HttpResponse?,
    private val _body: T?,
    val error: ErrorModel? = null,
    private val exception: Exception?
){

    companion object {
        suspend inline fun <reified T> success(data: HttpResponse): KtorResponse<T> {
            return KtorResponse(
                status = Status.Success,
                data = data,
                _body = data.body<T>(),
                error = null,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): KtorResponse<T> {
            return KtorResponse(
                status = Status.Failure,
                data = null,
                _body = null,
                error = generateErrorModel(exception),
                exception = exception
            )
        }
        suspend inline fun <reified T> failure(data: HttpResponse): KtorResponse<T> {
            return KtorResponse(
                status = Status.Failure,
                data = null,
                _body = null,
                error = data.body<ErrorModel>(),
                exception = null
            )
        }

        private fun generateErrorModel(exception: Exception): ErrorModel {
            return createErrorModel(0, exception.toString())
        }

        private fun createErrorModel(statusCode: Int = 0, customMessage: String? = ""): ErrorModel {
            return ErrorModel(
                status = statusCode,
                message = null,
                customMessage = customMessage?: ""
            )
        }
    }

    val failed: Boolean
        get() = this.status == Status.Failure || (this.data?.status?.value
            ?: 0) < 200 || (this.data?.status?.value ?: 0) >= 300

    val isSuccessful: Boolean
        get() = !failed && ((this.data?.status?.value ?: 0) >= 200 && (this.data?.status?.value
            ?: 0) < 300)

    val body get() = _body!!

    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }
}