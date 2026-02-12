## Register Users
Endpoint : POST /api/users

Request Body: 
```json
{
  "username": "Rauliqbal",
  "fullname": "Muhamad Raul",
  "password": "rauliqbal1324",
  "email" : "raul.iqbal@vensys.co.id",
  "role": "Admin"
}  
```

Response Body (success):

```json
{
  "success": true,
  "message": "Register berhasil!" 
}
```

Response Body (Failed):

```json
{ 
  "success" : false,
  "errors": "Username wajib diisi!" 
}
```

## Login Users
Endpoing: POST /api/login

Request Body:
```json
{
  "username": "Rauliqbal",
  "fullname": "Muhamad Raul",
  "password": "rauliqbal1324",
  "email" : "raul.iqbal@vensys.co.id",
  "role": "Admin"
}  
```

Response Body (success):

```json
{
  "success": true,
  "message": "Register berhasil!" 
}
```
Response Body (success):

```json
{
  "success": true,
  "errors": "Register berhasil!" 
}
```