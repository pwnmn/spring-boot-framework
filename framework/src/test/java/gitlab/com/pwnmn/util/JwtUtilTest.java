package gitlab.com.pwnmn.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JwtUtilTest {

    private JwtUtil testSubject;
    private String authHeader;

    @Before
    public void setup() {
        testSubject = new JwtUtil();
        authHeader =
                "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJoSEs3N21nVFZmb3VUZlU1bVJVNDVOYmQzVjdhLUJySEdHc" +
                        "l9RODEwZVJNIn0.eyJqdGkiOiIxZTBjYzY2MS04NGU0LTQzNzQtOGVmYS1mYzA4ZjZhNjlmNzkiLCJleHAiOjE1Nj" +
                        "UxNjYwMjMsIm5iZiI6MCwiaWF0IjoxNTY1MTY1OTYzLCJpc3MiOiJodHRwOi8va2V5Y2xvYWsuZG9ja2VyLmxvY2F" +
                        "saG9zdC9hdXRoL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMjAyZTFkYTItMWZkYi00OWFk" +
                        "LTliN2UtM2U2YTAxZDY2ODIyIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZnJvbnRlbmQtY2xpZW50IiwiYXV0aF90a" +
                        "W1lIjowLCJzZXNzaW9uX3N0YXRlIjoiODI3MjhlNzQtMTRhMi00YmEwLWIzMWYtZWMyODA2YmFhMmFiIiwiYWNyIj" +
                        "oiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2V" +
                        "zcyIsInJlYWxtX2FkbWluIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50" +
                        "Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiX" +
                        "X19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInVzZXJBdHRyaWJ1dGUiOi" +
                        "J0ZXN0aW5nIiwiY3VycmVudFJlYWxtIjoibWFzdGVyIiwicmVhbG0iOiJtYXN0ZXIiLCJwcmVmZXJyZWRfdXNlcm5" +
                        "hbWUiOiJ1c2VyIiwiaGFyZGNvZGVkIjoiaW1hY2xhaW0ifQ.bsXIfueb4KUB9_GAA9ytl7jY_tsxWcR1rS0z2Qq3u" +
                        "1O-ZyNtbT5NtLYs1unLw4X0zPVdM12ljOgS63f8qgNyyKRqTAtQG2AQMV6gzFGQ8kEb1kzGvk8svEIywajOT4JgLp" +
                        "0DkcGUuHVK00p5cJF0uYRkdRK9XeDWaMYCEda28RqC9487S_g0Rf5CmMu4Fcb0Xz6l16SGdHudkHwUznT9Z1nrk89" +
                        "hl7_mSBQSmtkYrPeQgIOZqqeyzFVU5Z8Vq7u1Q1Vv60V8iSAfrSZyuNI5Rbgrow3BTDeMQygwjF5ZK2HCBJdYxHJk" +
                        "SjbNdrV0TpwXC2V-W3X4CxJH2ygoflAySw";
    }

    @Test(expected = Test.None.class)
    public void extractFromAuthorization_Success_ValidAuthHeader() {
        var jwtEncoded = testSubject.extractJwtToken(authHeader);
        assertEquals(true, jwtEncoded.isPresent());
        assertEquals(authHeader.split(" ")[1], jwtEncoded.get());
    }

    @Test(expected = Test.None.class)
    public void extractFromAuthorization_Fail_InvalidFormat() {
        var jwtEncoded = testSubject.extractJwtToken(
                authHeader.replace("Bearer ", "")
        );
        assertEquals(false, jwtEncoded.isPresent());
    }

    @Test(expected = Test.None.class)
    public void extractRealmFromJwt_Success_ValidJwtToken() {
        var realm = testSubject.extractClaimFromToken(authHeader, "realm");

        assertEquals("master", realm.get());
    }

    @Test(expected = Test.None.class)
    public void extractRealmFromJwt_Fail_InvalidJwtToken() {
        var realm = testSubject.extractClaimFromToken(
                authHeader.replace("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ", "abcdefg"),
                "realm"
        );

        assertEquals(false, realm.isPresent());
    }


}
