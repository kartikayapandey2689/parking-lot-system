Setup

export GOOGLE_CLIENT_ID=xxxx
export GOOGLE_CLIENT_SECRET=yyyy
mvn spring-boot:run


API Endpoints

/api/parking/entry → Vehicle Entry

/api/parking/exit → exit vehicle

/auth/me → Check Logged-in User

Authentication

Uses Google OAuth2

Add your Gmail in Test Users before running

Postman Collection

Import postman_collection.json

Use OAuth2 tab to get token