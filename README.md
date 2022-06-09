# Cleaner Project

If you are given a RDF dataset file which contains invalid IRIs, for example, when you try loading it in GraphDB, its RDF4J parser will result into invalid IRIs errors and you won’t be able to load it in GraphDB. It is frustrating. You might have to use other Endpoints like Virtuoso etc, but that’s also time consuming and nothing is equal to GraphDB.

In order to fix this issue, I have proposed the following steps:

1. If the file is not in NT format, use the docker-based script to convert TTL into NT format such as:

    ```java
    docker run --name shexer_wikidata_cleaning --volume ${PWD}/wikidata:/rdf stain/jena riot --output=ntriples wikidata-20150518-all-BETA.ttl &> wikidata/wikidata-20150518-all-BETA-clean.nt
    ```

2. Now we need to parse the file line by line to find out triples having invalid IRIs. The fast approach would be to just find out the line numbers of triples having invalid IRIs. This way, we can remove these lines in a faster way. Otherwise, file writing and reading while parsing will become very expensive for very large files.
3. Once all the invalid triples lines are identified, use these to remove from the file and create two files, one without the marked triples and one with invalid triples so that you can verify them manually as well.


## To run locally
    git pull
    gradle clean
    gradle build
    gradle shadowjar
    java -jar build/libs/cleaner-1.0-SNAPSHOT-all.jar config.properties  
    
Make sure to specify the correct paths in config.properties file.


## To run using Docker

There is a docker file 'Dockerfile' in the main directory. To specify the paths for the Docker, use the commented config paths in the config.properties file.
Then update the 'run.sh' file and run it.