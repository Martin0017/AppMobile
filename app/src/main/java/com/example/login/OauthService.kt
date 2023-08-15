import com.example.login.UserCredentials
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OauthService {
        @Headers("Content-Type: application/json")
        @POST("loginmobile")
        fun loginWithBody(@Body requestBody: Any): Call<Boolean>
}