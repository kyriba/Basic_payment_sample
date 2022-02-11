package sample.model;

import com.google.gson.annotations.SerializedName;

public class GetTokenResponseModel {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("scope")
    private String scope;

    @SerializedName("kapp_username")
    private String kappUsername;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getKappUsername() {
        return kappUsername;
    }

    public void setKappUsername(String kappUsername) {
        this.kappUsername = kappUsername;
    }

    @Override
    public String toString() {
        return "{\n" +
                "   accessToken: \"" + accessToken + '\"' +
                ",\n   tokenType: \"" + tokenType + '\"' +
                ",\n   expiresIn: " + expiresIn +
                ",\n   scope: \"" + scope + '\"' +
                ",\n   kappUsername: \"" + kappUsername + '\"' +
                "\n}";
    }
}
