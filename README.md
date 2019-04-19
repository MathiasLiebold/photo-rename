# photo-rename
# Description
Rename photos based on their date taken. Just drop all photos of your camera, phone and WhatsApp into one directory and all photos are named and sorted accordingly with one click.

# Renaming strategy
Target naming pattern: "yyyy-MM-dd HH.mm.ss (Description).jpg"

| Original name              | Date taken          | File created |  New file name              | Notes         |
| ---------------------------| ------------------- | ------------ | --------------------------- | ------------- |
| 1970-01-01 14.15.16 (Name) | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 (Name)  | Unchanged     |
| any (Name)                 | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 (Name)  | Name is kept  |
| any                        | 1970-01-01 14.15.16 | any          | 1970-01-01 14.15.16 ()      | Date taken    |
| IMG-20181221-WA0016        | any                 | any          | 2018-12-21 (WA0016)         | WhatsApp      |

# Build instructions
Build with maven by executing the following command: "mvn clean package".
The result will be an executable jar in your repository e.g. "...\photo-rename\target\photo-rename-0.0.1-SNAPSHOT.jar"

# Usage instructions
1. Copy the jar into the directory with the photos.
2. Double-click the jar.
3. Click on "Start/Run".

# Implementation details
* Java 8, [Maven](https://maven.apache.org/), [Spring Boot](https://spring.io/projects/spring-boot), [Project Lombok](https://projectlombok.org/), JavaFX, [metadata-extractor](https://github.com/drewnoakes/metadata-extractor)