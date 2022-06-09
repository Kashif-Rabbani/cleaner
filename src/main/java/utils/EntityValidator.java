package utils;

import cs.MyParser;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.rio.*;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;
import org.semanticweb.yars.nx.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class EntityValidator {
    String rdfFilePath;
    HashSet<Long> invalidLineNumbers = new HashSet<>();
    
    public EntityValidator(String rdfFilePath) {
        this.rdfFilePath = rdfFilePath;
        this.rdf4jParsing();
    }
    
    public HashSet<Long> getInvalidLineNumbers() {
        return invalidLineNumbers;
    }
    
    public void rdf4jParsing() {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("Started Parsing...");
        try {
            //ModelBuilder builder = new ModelBuilder();
            //myParser.setRDFHandler(new StatementCollector(builder.build()));
            
            try (FileInputStream fileInputStream = new FileInputStream(rdfFilePath)) {
                MyParser myParser = new MyParser();
                myParser.parse(fileInputStream, "");
                this.invalidLineNumbers = myParser.getInvalidLineNumbers();
            } catch (IOException | RDFHandlerException | RDFParseException e) {
                e.printStackTrace();
            }
            /*System.out.println("About to write model to file ...");
            Model model = builder.build();
            FileOutputStream out = new FileOutputStream(Constants.CLEAN_DATASET_FILE);
            RDFWriter writer = Rio.createWriter(RDFFormat.N3, out);
            writer.startRDF();
            for (Statement st : model) {
                writer.handleStatement(st);
                writer.getWriterConfig();
            }
            writer.endRDF();
            out.close();*/
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        watch.stop();
        System.out.println("Time Elapsed rdf4jParsing: " + TimeUnit.MILLISECONDS.toSeconds(watch.getTime()) + " : " + TimeUnit.MILLISECONDS.toMinutes(watch.getTime()));
    }
    
    public void xyz() {
        ValueFactory factory = SimpleValueFactory.getInstance();
        try {
            Files.lines(Path.of(rdfFilePath))
                    .forEach(line -> {
                        try {
                            Node[] nodes = NxParser.parseNodes(line);
                            String subject = nodes[0].getLabel();
                            String predicate = nodes[1].getLabel();
                            String object = nodes[2].getLabel();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Not used yet but can be used to set the rdfhandler for the parser
 */
class StatementWriter extends AbstractRDFHandler {
    
    FileOutputStream out;
    RDFWriter writer;
    
    StatementWriter() {
        try {
            this.out = new FileOutputStream(Constants.CLEAN_DATASET_FILE);
            this.writer = Rio.createWriter(RDFFormat.N3, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void handleStatement(Statement st) {
        try {
            writer.startRDF();
            writer.handleStatement(st);
            writer.endRDF();
        } catch (RDFHandlerException e) {
            // oh no, do something!
        }
    }
}