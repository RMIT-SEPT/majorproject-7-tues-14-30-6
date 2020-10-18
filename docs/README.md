# Appointment Service Readme

The appointment service is split into two components, the frontend (built on React) and backend (built on Springboot).
To run the appointment service both the frontend and backend must be compiled and run.
By default the backend runs on port 8080 and frontend on port 80.
It would be beneficial in production to use a reverse proxy to route all /api url paths to the backend service, transparent to users.

To compile the backend simply run `mvn package -DskipTests` in `BackEnd/appointment-service-backend`, to start it simply run the compiled jar file with `java -jar target/appointmentservicebackend-0.0.1-SNAPSHOT.jar`

To compile the frontend run `npm run build`, optionally with the environment variable REACT_APP_PROD set to true so that API URL's do not include a port specification (recommended for production). This will compile the project into static sources that can be hosted with any web host.

To automate the build and hosting process, docker files have been provided for the frontend and backend, and can be automatically built and run with `docker-compose up --build` to quickly deploy the service anywhere.
