package com.example.datemate_sd

import com.google.auth.oauth2.GoogleCredentials
import okio.IOException
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

object AccessToken {

    private val firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(): String?{
        try {
            val jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"datemate-6978d\",\n" +
                    "  \"private_key_id\": \"f9e83625bc355a343ed78b0ca472c77f3512408e\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEuAIBADANBgkqhkiG9w0BAQEFAASCBKIwggSeAgEAAoIBAQCbK/OL1X39BEXa\\nZOxcZ/gdWd0QzFPN+z1FHaNW/SuMRYGzdXI8m2oqYMQe63U61NXZTMgNqecEdlLz\\nuz3Nrad38jtQ8bpS3F5FiC+6r+GiJlGGPKgVrGsIu7+raaRcHbctIoJjxCi11hUA\\nt0ytCS7N24OxAuFbOAGwldHJB2N6W0oAObRAr5NuJekNnCQhM4cebjqGdSn6wepX\\n7/yr4Fmf2zzurx+6W3XOxP/dIFQe1e4TMF+tWr2Bf6ntHv44eY6DO7tkjKjtQBQn\\nNLvl5Pufyd9SXRBy/OVWslGt6ET726wqgtYxAhk8qE232BB1b4Rw6KJJFkJlJbj0\\n12lG99K9AgMBAAECgf8+4tERGrS0lca5e61OxdQIESNGa4iLMgbiu+A2oOPFNWYb\\nFL33UgT0p8LTxRj9fsQsDLIG15SIemuj235wnLACLGldpJmeezC5fTfasO0AigcA\\nIBT+s6GtKGnFTksOCfzyjcnKJPVavChG4gJxHgeZ5hy44DH0hf4pUo5yqkvmifcm\\nYmMt298qHzTTJSJq3s/BwJq46VtNG5y9iE9QRqp/0AEa7P61nYX6KF+goFTWaRzR\\nDPPvpH3hrzv4c0SB2FJo8p4W/05wax5ozd2KlUecDd16xoiXenohCG/q6Y804Elr\\nfz47wxt44srXJuMyznaUJkvYg0Kt8rVp7+8uTPkCgYEAyHaO+qQqQnDEzJoeGR1m\\n+o6Wce4yD+5EWpudquS1o1r6h/eyXg7yQJI8TGZLXlCWMyNKU1h5JD/ESLUWybma\\ntTOKObHrD+k0/6Yek5G55W+f2gb9Ggzaqlz5WGI9mYvpAK3WCDwK5pjhvgqiph0h\\ne9e9W84eqskzcZF5JM3JuqkCgYEAxikwfjxMcGRpcvGzMbklbgWBw7jqsePdY7IY\\nNcgwUGxpBpgpim9NfcxvKTL6EuM+ALMLAxyCx3ZZAxKQj+WcCC5VhopAY4FUWCrq\\nTH0CMrSVav6JQpnrxr0+q7E0Sa3LNO9UMLQufe+qxCsv6wXIZC2OSFeo/OsB+gDt\\nI3zrF/UCgYAUqLM/fV+ljT2a4F8ts2wDcmJqx2N9ZM7Cj7sF+6AEf8HF9W76Zovs\\nv6VbMkeeJqv0BTUJtdp2M+QkohUBIRGijvc1zvIsp5oAprwL9T61OHW+4G2BG8YF\\nPKOtyiITCuouWbvvCVEORr8s+Keeeu4Sdz9akLl0XFvi3l0fv92EUQJ/WD3/Wjd/\\n0ozkaEs5gOrxwAyzvHScDptp+vtTzJ9Y5HdiTY108jCeUWa4GaCZu891zNnuDXSk\\nyC3rdXLO7UVSNlJrw33NLoJpDNoPfAAu+mVwJDU06nOgit+7CLngju7Vjr0EwKt8\\nCJixcqnIrN/mXLXspkpi9j2wo6PF9geqqQKBgBlJ1RUaOgRS+EaOxg5DsmYJ88vL\\n2XXI8SElA5lXdIpoIuw7ah0MBwjvV2l8wRaooGDdmTLy/UuX3ooffmZEgCLnseQN\\n3WuIegAiMlCoHsItHb7mr/xemCWyR9U0sCMsfUF7sNZaqbCbZSGwcqTEStjk8oSY\\ni4DUrMe3VILz3GzU\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-u57wo@datemate-6978d.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"116680094894730209434\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-u57wo%40datemate-6978d.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}"

            val stream = ByteArrayInputStream(jsonString.toByteArray(StandardCharsets.UTF_8))
            val googleCredential = GoogleCredentials.fromStream(stream)
                .createScoped(arrayListOf(firebaseMessagingScope))

            googleCredential.refresh()

            return googleCredential.accessToken.tokenValue
        }catch (e: IOException){
            return null
        }
    }
}