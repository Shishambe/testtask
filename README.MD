Feel free to complete this test task, override or change everything

For start app: 
1. Run mvn clean install
2. Run mvn spring-boot:run

### Register user
curl -X POST --data "email=test1@test.com&password=test" http://localhost:8080/user/register/ -v
curl -X POST --data "email=test2@test.com&password=test2" http://localhost:8080/user/register/ -v

### Create new password
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=booking&login=test1@test.com&password=bookingPasWord&tag=booking" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=gmail&login=test1@gmail.com&password=sfdgsdfg&tag=mail" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=facebook&login=test1@gmail.com&password=sdfsdfercv&tag=facebook" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=bookingmail&login=test1@gmail.com&password=sfdgsdfg&tag=mail&tag=booking" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=bookingmailfacebook&login=test1@gmail.com&password=sfdgsdfg&tag=mail&tag=booking&tag=facebook" http://localhost:8080/user/password/create -v

curl -X POST --data "userEmail=test2@test.com&userPassword=test2&name=booking&login=test2@test.com&password=rtyrty" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test2@test.com&userPassword=test2&name=gmail&login=test2@gmail.com&password=cvbcvbc" http://localhost:8080/user/password/create -v
curl -X POST --data "userEmail=test2@test.com&userPassword=test2&name=taggedBookinn&login=test2@gmail.com&password=cvbcvbc&tag=booking" http://localhost:8080/user/password/create -v

### Update user password
curl -X PATCH --data "userEmail=test1@test.com&userPassword=test&name=booking&login=bookingUPDATED@booking.com&password=bookingPassword" http://localhost:8080/user/password/update -v
curl -X PATCH --data "userEmail=test2@test.com&userPassword=test2&name=gmail&login=test2up@gmail.com&password=cvbcvbchj" http://localhost:8080/user/password/update -v

### Delete user password
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=booking" http://localhost:8080/user/password/delete -v
curl -X POST --data "userEmail=test1@test.com&userPassword=test&name=gmail" http://localhost:8080/user/password/delete -v

### Get passwords for user
curl -X POST --data "userEmail=test1@test.com&userPassword=test" http://localhost:8080/user/password/list -v
curl -X POST --data "userEmail=test2@test.com&userPassword=test2" http://localhost:8080/user/password/list -v

### Get all passwords
curl -X GET http://localhost:8080/password/list -v

### Serach for passwords (leave param with empty value to not include it in search)
### Search for passwords which include tags
curl -X POST --data "inctag=booking&exctag=&name=" http://localhost:8080/password/search -v
curl -X POST --data "inctag=mail&exctag=&name=" http://localhost:8080/password/search -v
curl -X POST --data "inctag=mail&inctag=booking&exctag=&name=" http://localhost:8080/password/search -v

### Search for password which dont have tags
curl -X POST --data "inctag=&exctag=booking&name=" http://localhost:8080/password/search -v
curl -X POST --data "inctag=&exctag=booking&exctag=mail&name=" http://localhost:8080/password/search -v
curl -X POST --data "inctag=facebook&exctag=booking&name=" http://localhost:8080/password/search -v
curl -X POST --data "inctag=&exctag=facebook&name=" http://localhost:8080/password/search -v

### Search for password by name 
curl -X POST --data "inctag=&exctag=&name=booking" http://localhost:8080/password/search -v
curl -X POST --data "inctag=&exctag=&name=gmail" http://localhost:8080/password/search -v
