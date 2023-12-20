mkdir jwt
openssl genrsa -out jwt/publicKey.pem
openssl pkcs8 -topk8 -inform PEM -in jwt/publicKey.pem -out jwt/privateKey.pem -nocrypt
openssl rsa -in jwt/publicKey.pem -pubout -outform PEM -out jwt/publicKey.pem
