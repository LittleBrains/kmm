package kz.littlebrains.core

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kz.alseco.core.network.ktor.BaseApiClient
import kz.alseco.core.network.ktor.KtorResponse

class PiRepository(private val httpClient: HttpClient):BaseApiClient() {

    suspend fun fetchShows():KtorResponse<CasesListModel> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                httpClient.get("https://zanpro-dev.alseco.kz/api/zanpro-api/api/v1/cases/search/list")
            }
        }
    }
}