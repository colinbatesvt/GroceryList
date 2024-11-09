to authenticate -> POST /user/authenticate with username/pass as request body params

then make a request such as GET /api/groceryLists/10006

save secret properties in the root directory as secrets/secrets.properties

set a path to the secret properties as a build argument, eg. --spring.config.additional-location=./secrets/secrets.properties

to build docker image: docker build -t grocery-list .
to run docker image: docker run -t -p 8080:8080 --env-file ./secrets/env.list grocerylist