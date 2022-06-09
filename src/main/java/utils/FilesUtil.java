package utils;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.lang3.time.StopWatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class contains various methods used as a utility in the project to interact with files
 */
public class FilesUtil {
    public static void writeToFile(String str, String fileNameAndPath) {
        try {
            FileWriter fileWriter = new FileWriter(new File(fileNameAndPath));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(str);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void writeToFileInAppendMode(String str, String fileNameAndPath) {
        try {
            FileWriter fileWriter = new FileWriter(new File(fileNameAndPath), true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(str);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static List<String[]> readCsvAllDataOnceWithPipeSeparator(String fileAddress) {
        List<String[]> allData = null;
        try {
            FileReader filereader = new FileReader(fileAddress);
            // create csvParser object with
            // custom separator pipe
            CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
            
            // create csvReader object with
            // parameter file reader and parser
            CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser).build();
            
            // Read all data at once
            allData = csvReader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }
    
    public static boolean deleteFile(String fileAddress) {
        File file = new File(fileAddress);
        return file.delete();
    }
    
    public static String readQuery(String query) {
        String q = null;
        try {
            String queriesDirectory = ConfigManager.getProperty("resources_path") + "/queries/";
            q = new String(Files.readAllBytes(Paths.get(queriesDirectory + query + ".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return q;
    }
    
    
    public static String readShaclStatsQuery(String query, String type) {
        String q = null;
        try {
            String queriesDirectory = ConfigManager.getProperty("resources_path") + "/shacl_stats_queries/" + type + "/";
            q = new String(Files.readAllBytes(Paths.get(queriesDirectory + query + ".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return q;
    }
    
    
    public static void removeLinesFromFile(String fileAddress,  HashSet<Long> set) {
        //Set<Integer> set = new HashSet<>(Arrays.asList(49055920, 1929511564));
        StopWatch watch = new StopWatch();
        watch.start();
        
        AtomicLong lineNo = new AtomicLong(1);
        try {
            FileWriter fileWriter = new FileWriter(Constants.CLEAN_DATASET_FILE, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            FileWriter fileWriter2 = new FileWriter(Constants.DIRTY_DATASET_FILE, true);
            PrintWriter printWriter2 = new PrintWriter(fileWriter2);
            Files.lines(Path.of(fileAddress))
                    .forEach(line -> {
                        if (set.contains(lineNo.get())) {
                            printWriter2.println(line);
                        } else {
                            printWriter.println(line);
                        }
                        lineNo.getAndIncrement();
                    });
            printWriter.close();
            printWriter2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        watch.stop();
        System.out.println("Time Elapsed removeLinesFromFile: " + TimeUnit.MILLISECONDS.toSeconds(watch.getTime()) + " : " + TimeUnit.MILLISECONDS.toMinutes(watch.getTime()));
    }
    
}
